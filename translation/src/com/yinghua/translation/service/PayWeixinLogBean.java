package com.yinghua.translation.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.yinghua.translation.model.PayWeixinLog;

@Stateless
public class PayWeixinLogBean extends AbstractFacade<PayWeixinLog>
{
	private static final Logger logger = Logger.getLogger(PayWeixinLogBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public PayWeixinLogBean()
	{
		super(PayWeixinLog.class);
	}

	public PayWeixinLog findById(Long id)
	{
		return em.find(PayWeixinLog.class, id);
	}

	public Long createPayWeixinLog(PayWeixinLog order)
	{
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void updatePayWeixinLog(PayWeixinLog order)
	{
		em.merge(order);
	}

	public List<PayWeixinLog> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PayWeixinLog> criteria = cb.createQuery(PayWeixinLog.class);
		Root<PayWeixinLog> product = criteria.from(PayWeixinLog.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}

	public PayWeixinLog findByPayWeixinLogNo(String orderNo)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PayWeixinLog> criteria = cb.createQuery(PayWeixinLog.class);
		Root<PayWeixinLog> order = criteria.from(PayWeixinLog.class);
		criteria.select(order).where(cb.equal(order.get("outTradeNo"), orderNo));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (PayWeixinLog) query.getSingleResult();
		}
	}

}
