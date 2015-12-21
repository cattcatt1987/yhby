package com.yinghua.translation.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.ThirdMember;

@Stateless
public class ThirdMemberBean extends AbstractFacade<ThirdMember>
{

	private static final Logger logger = Logger.getLogger(ThirdMemberBean.class
			.getCanonicalName());

	@Inject
	private Event<ThirdMember> memberEventSrc;

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public ThirdMemberBean()
	{
		super(ThirdMember.class);
	}

	public boolean register(ThirdMember member)
	{

		// check if user exists
		if (findByOpenId(member.getOpenId()) == null)
		{
			super.create(member);
			memberEventSrc.fire(member);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void updateMember(ThirdMember member)
	{
		em.merge(member);
		memberEventSrc.fire(member);
	}

	public ThirdMember findById(Long id)
	{
		return em.find(ThirdMember.class, id);
	}

	public ThirdMember findByOpenId(String openId)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ThirdMember> criteria = cb.createQuery(ThirdMember.class);
		Root<ThirdMember> member = criteria.from(ThirdMember.class);
		criteria.select(member).where(
				cb.equal(member.get("openId"), openId));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (ThirdMember) query.getSingleResult();
		}
	}

	public List<ThirdMember> findAllOrderedByName()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ThirdMember> criteria = cb.createQuery(ThirdMember.class);
		Root<ThirdMember> member = criteria.from(ThirdMember.class);
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		return em.createQuery(criteria).getResultList();
	}
	
	
}
