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

import com.yinghua.translation.model.MemberOrder;

@Stateless
public class MemberOrderBean extends AbstractFacade<MemberOrder>
{
	private static final Logger logger = Logger.getLogger(MemberOrderBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public MemberOrderBean()
	{
		// TODO Auto-generated constructor stub
		super(MemberOrder.class);
	}

	public MemberOrder findById(Long id)
	{
		return em.find(MemberOrder.class, id);
	}

	public Long createOrder(MemberOrder order)
	{
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void updateOrder(MemberOrder order)
	{
		em.merge(order);
	}

	public List<MemberOrder> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberOrder> criteria = cb.createQuery(MemberOrder.class);
		Root<MemberOrder> product = criteria.from(MemberOrder.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}

	public MemberOrder findByOrderNo(String orderNo)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberOrder> criteria = cb.createQuery(MemberOrder.class);
		Root<MemberOrder> order = criteria.from(MemberOrder.class);
		criteria.select(order).where(cb.equal(order.get("orderNo"), orderNo));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (MemberOrder) query.getSingleResult();
		}
	}

	public List<MemberOrder> findByUid(String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberOrder> criteria = cb.createQuery(MemberOrder.class);
		Root<MemberOrder> order = criteria.from(MemberOrder.class);
		criteria.select(order).where(cb.equal(order.get("memberNumber"), uno));
		return em.createQuery(criteria).getResultList();
	}

}
