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
import com.yinghua.translation.model.Product;
import com.yinghua.translation.util.ClassLoaderUtil;

@Stateless
public class OrderBean extends AbstractFacade<Order>
{
	private static final Logger logger = Logger.getLogger(OrderBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public OrderBean()
	{
		// TODO Auto-generated constructor stub
		super(Order.class);
	}

	public Order findById(Long id)
	{
		return em.find(Order.class, id);
	}

	public Long createOrder(Order order)
	{
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void updateOrder(Order order)
	{
		em.merge(order);
	}

	public List<Order> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
		Root<Order> product = criteria.from(Order.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}

	public Order findByOrderNo(String orderNo)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
		Root<Order> order = criteria.from(Order.class);
		criteria.select(order).where(cb.equal(order.get("orderNo"), orderNo));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Order) query.getSingleResult();
		}
	}

	public List<Order> findByUid(String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
		Root<Order> order = criteria.from(Order.class);
		criteria.select(order).where(cb.equal(order.get("memberNumber"), uno));
		return em.createQuery(criteria).getResultList();
	}

}
