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

import com.yinghua.translation.model.Account;
import com.yinghua.translation.model.BizServiceData;
import com.yinghua.translation.model.Member;

@Stateless
public class BizServiceDataBean extends AbstractFacade<BizServiceData>
{

	private static final Logger logger = Logger.getLogger(BizServiceDataBean.class
			.getCanonicalName());

	@Inject
	private Event<BizServiceData> memberEventSrc;

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public BizServiceDataBean()
	{
		super(BizServiceData.class);
	}

	public Long register(BizServiceData bizServiceData)
	{

		super.create(bizServiceData);
		memberEventSrc.fire(bizServiceData);
		return bizServiceData.getId();
	}
	
	public void updateBizServiceData(BizServiceData bizServiceData)
	{
		em.merge(bizServiceData);
		memberEventSrc.fire(bizServiceData);
	}

	public BizServiceData findById(Long id)
	{
		return em.find(BizServiceData.class, id);
	}

	public BizServiceData findByPhone(String phone)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BizServiceData> criteria = cb.createQuery(BizServiceData.class);
		Root<BizServiceData> bizServiceData = criteria.from(BizServiceData.class);
		criteria.select(bizServiceData).where(
				cb.equal(bizServiceData.get("phone"), phone));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (BizServiceData) query.getSingleResult();
		}
	}

	public Member findByEmail(String email)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).where(cb.equal(member.get(Member_.name),
		// email));
		criteria.select(member).where(cb.equal(member.get("email"), email));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Member) query.getSingleResult();
		}
	}

	public List<Member> findAllOrderedByName()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		return em.createQuery(criteria).getResultList();
	}
	
	
}
