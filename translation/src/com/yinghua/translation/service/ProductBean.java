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
import com.yinghua.translation.model.Product;
import com.yinghua.translation.util.ClassLoaderUtil;

@Stateless
public class ProductBean extends AbstractFacade<Product>
{
	private static final Logger logger = Logger.getLogger(Product.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public ProductBean()
	{
		// TODO Auto-generated constructor stub
		super(Product.class);
	}
	
	public Product findById(Long id)
	{
		return em.find(Product.class, id);
	}
	
	public List<Product> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> product = criteria.from(Product.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}
	
	public Product findByProductNo(String orn)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> product = criteria.from(Product.class);
		criteria.select(product).where(cb.equal(product.get("productNo"), orn));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Product) query.getSingleResult();
		}
	}

}
