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
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.TranslationRecord;

@Stateless
public class TranslationHistoryBean extends AbstractFacade<TranslationRecord>
{

	private static final Logger logger = Logger.getLogger(TranslationHistoryBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public TranslationHistoryBean()
	{
		super(TranslationRecord.class);
	}

	public void register(TranslationRecord translationRecord)
	{
		super.create(translationRecord);
	}
	
	public void updateMember(TranslationRecord translationRecord)
	{
		em.merge(translationRecord);
	}

	public TranslationRecord findById(Long id)
	{
		return em.find(TranslationRecord.class, id);
	}

	public List<TranslationRecord> findAllRecordByUid(Integer pageNo,Integer pageSize,String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TranslationRecord> criteria = cb.createQuery(TranslationRecord.class);
		Root<TranslationRecord> trans = criteria.from(TranslationRecord.class);
		criteria.select(trans).where(cb.equal(trans.get("memberNumber"), uno));
		int[] range = {pageNo,pageSize};
		return super.findRange(range, criteria);
	}
	
	public List<TranslationRecord> findByUid(String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TranslationRecord> criteria = cb.createQuery(TranslationRecord.class);
		Root<TranslationRecord> order = criteria.from(TranslationRecord.class);
		criteria.select(order).where(cb.equal(order.get("memberNumber"), uno));
		return em.createQuery(criteria).getResultList();
	}
	
	
}
