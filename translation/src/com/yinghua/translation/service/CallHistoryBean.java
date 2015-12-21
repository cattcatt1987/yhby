package com.yinghua.translation.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.yinghua.translation.model.CallRecord;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.TranslationRecord;
import com.yinghua.translation.model.enumeration.Language;

@Stateless
public class CallHistoryBean extends AbstractFacade<CallRecord>
{

	private static final Logger logger = Logger.getLogger(CallHistoryBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public CallHistoryBean()
	{
		super(CallRecord.class);
	}

	public void register(CallRecord callRecord)
	{
		super.create(callRecord);
	}
	
	public void updateCallRecord(CallRecord callRecord)
	{
		em.merge(callRecord);
	}

	public CallRecord findById(Long id)
	{
		return em.find(CallRecord.class, id);
	}

	public List<CallRecord> findAllRecordByUid(Integer pageNo,Integer pageSize,String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CallRecord> criteria = cb.createQuery(CallRecord.class);
		Root<CallRecord> trans = criteria.from(CallRecord.class);
		criteria.select(trans).where(cb.equal(trans.get("memberNumber"), uno));
		int[] range = {pageNo,pageSize};
		return super.findRange(range, criteria);
	}
	
	public List<CallRecord> findByUid(String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CallRecord> criteria = cb.createQuery(CallRecord.class);
		Root<CallRecord> order = criteria.from(CallRecord.class);
		criteria.select(order).where(cb.equal(order.get("userNumber"), uno)).orderBy(cb.desc(order.get("startTime")));
		return em.createQuery(criteria).getResultList();
	}
	
//	public CallRecord findCallRecord(String uno,Date start,Date end)
//	{
////		Query query = em.createQuery("select p from MemberPackage p where p.memberNumber=:uno and p.languages=:lan");
////		query.setParameter("uno", uno);
////		query.setParameter("lan", lanuage);
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<CallRecord> criteria = cb.createQuery(CallRecord.class);
//		Root<CallRecord> page = criteria.from(CallRecord.class);
////		criteria.select(page).where(cb.equal(page.get("memberNumber"), uno).);
////		cb.and(cb.equal(page.get("language"), lanuage));
//		Predicate p = cb.and(cb.equal(page.get("memberNumber"), uno),cb.between("", arg1, arg2)));
//		Query query = em.createQuery(criteria.where(p));
//		if (query.getResultList().size() == 0)
//		{
//			return null;
//		}
//		else
//		{
//			return (MemberPackage) query.getSingleResult();
//		}
//	}
//	
	
}
