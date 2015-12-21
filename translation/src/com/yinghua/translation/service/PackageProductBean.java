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

import com.yinghua.translation.model.PackageProduct;
import com.yinghua.translation.model.Product;

@Stateless
public class PackageProductBean extends AbstractFacade<PackageProduct>
{
	private static final Logger logger = Logger.getLogger(PackageProduct.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public PackageProductBean()
	{
		super(PackageProduct.class);
	}
	
	public PackageProduct findById(Long id)
	{
		return em.find(PackageProduct.class, id);
	}
	
	public List<PackageProduct> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PackageProduct> criteria = cb.createQuery(PackageProduct.class);
		Root<PackageProduct> packageProduct = criteria.from(PackageProduct.class);
		criteria.select(packageProduct);
		return em.createQuery(criteria).getResultList();
	}
	
	public List<PackageProduct> findByPackageType(String orn)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PackageProduct> criteria = cb.createQuery(PackageProduct.class);
		Root<PackageProduct> packageProduct = criteria.from(PackageProduct.class);
		criteria.select(packageProduct).where(cb.equal(packageProduct.get("type"), orn));
		return em.createQuery(criteria).getResultList();
	}
	
	public PackageProduct findByPackageNo(String orn)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PackageProduct> criteria = cb.createQuery(PackageProduct.class);
		Root<PackageProduct> packageEm = criteria.from(PackageProduct.class);
		criteria.select(packageEm).where(cb.equal(packageEm.get("packageNo"), orn));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (PackageProduct) query.getSingleResult();
		}
	}

}
