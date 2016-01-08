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

import com.yinghua.translation.model.MemberOrderUse;
import com.yinghua.translation.model.Product;

@Stateless
public class MemberOrderUseBean extends AbstractFacade<MemberOrderUse>
{
	private static final Logger logger = Logger.getLogger(MemberOrderUse.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public MemberOrderUseBean()
	{
		super(MemberOrderUse.class);
	}
	
	public Product findById(Long id)
	{
		return em.find(Product.class, id);
	}
	
	public List<MemberOrderUse> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberOrderUse> criteria = cb.createQuery(MemberOrderUse.class);
		Root<MemberOrderUse> product = criteria.from(MemberOrderUse.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}
	
	public MemberOrderUse findByProductNo(String orn)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberOrderUse> criteria = cb.createQuery(MemberOrderUse.class);
		Root<MemberOrderUse> product = criteria.from(MemberOrderUse.class);
		criteria.select(product).where(cb.equal(product.get("productNo"), orn));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (MemberOrderUse) query.getSingleResult();
		}
	}

}
