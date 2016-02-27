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
import com.yinghua.translation.model.BizCallRecord;
import com.yinghua.translation.model.Member;

@Stateless
public class BizCallRecordBean extends AbstractFacade<BizCallRecord>
{

	private static final Logger logger = Logger.getLogger(BizCallRecordBean.class
			.getCanonicalName());

	@Inject
	private Event<BizCallRecord> memberEventSrc;

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public BizCallRecordBean()
	{
		super(BizCallRecord.class);
	}

	public Long register(BizCallRecord bizCallRecord)
	{

		super.create(bizCallRecord);
		memberEventSrc.fire(bizCallRecord);
		return bizCallRecord.getId();
	}
	
	public void updateAccount(BizCallRecord bizCallRecord)
	{
		em.merge(bizCallRecord);
		memberEventSrc.fire(bizCallRecord);
	}

	public BizCallRecord findById(Long id)
	{
		return em.find(BizCallRecord.class, id);
	}

	public Account findByMemberNo(String memberNo)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> criteria = cb.createQuery(Account.class);
		Root<Account> member = criteria.from(Account.class);
		criteria.select(member).where(
				cb.equal(member.get("memberNumber"), memberNo));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Account) query.getSingleResult();
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
