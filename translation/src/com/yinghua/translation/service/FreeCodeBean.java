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

import com.yinghua.translation.model.FreeCode;
import com.yinghua.translation.model.MemberOrder;

@Stateless
public class FreeCodeBean extends AbstractFacade<FreeCode>
{
	private static final Logger logger = Logger.getLogger(FreeCode.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public FreeCodeBean()
	{
		super(FreeCode.class);
	}
	
	public FreeCode findById(Long id)
	{
		return em.find(FreeCode.class, id);
	}
	
	public Long createFreeCode(FreeCode order)
	{
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void updateFreeCode(FreeCode order)
	{
		em.merge(order);
	}
	
	public List<FreeCode> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FreeCode> criteria = cb.createQuery(FreeCode.class);
		Root<FreeCode> packageProduct = criteria.from(FreeCode.class);
		criteria.select(packageProduct);
		return em.createQuery(criteria).getResultList();
	}
	
	
	public FreeCode findByCode(String code)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FreeCode> criteria = cb.createQuery(FreeCode.class);
		Root<FreeCode> packageEm = criteria.from(FreeCode.class);
		criteria.select(packageEm).where(cb.equal(packageEm.get("code"), code));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (FreeCode) query.getSingleResult();
		}
	}

}
