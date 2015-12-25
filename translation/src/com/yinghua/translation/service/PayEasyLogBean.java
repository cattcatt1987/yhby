package com.yinghua.translation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.PayEasyLog;
import com.yinghua.translation.model.Product;
import com.yinghua.translation.util.ClassLoaderUtil;

@Stateless
public class PayEasyLogBean extends AbstractFacade<PayEasyLog>
{
	private static final Logger logger = Logger.getLogger(PayEasyLogBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public PayEasyLogBean()
	{
		super(PayEasyLog.class);
	}

	public PayEasyLog findById(Long id)
	{
		return em.find(PayEasyLog.class, id);
	}

	public Long createPayEasyLog(PayEasyLog order)
	{
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void updatePayEasyLog(PayEasyLog order)
	{
		em.merge(order);
	}

	public List<PayEasyLog> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PayEasyLog> criteria = cb.createQuery(PayEasyLog.class);
		Root<PayEasyLog> product = criteria.from(PayEasyLog.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}

	public PayEasyLog findByPayEasyLogNo(String orderNo)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PayEasyLog> criteria = cb.createQuery(PayEasyLog.class);
		Root<PayEasyLog> order = criteria.from(PayEasyLog.class);
		criteria.select(order).where(cb.equal(order.get("oid"), orderNo));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (PayEasyLog) query.getSingleResult();
		}
	}

}
