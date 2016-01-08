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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.yinghua.translation.model.BaseProduct;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.PackageProduct;
import com.yinghua.translation.model.PackageProductContent;
import com.yinghua.translation.model.Product;
import com.yinghua.translation.util.ClassLoaderUtil;

@Stateless
public class PackageProductContentBean extends AbstractFacade<PackageProductContent>
{
	private static final Logger logger = Logger.getLogger(PackageProductContent.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public PackageProductContentBean()
	{
		super(PackageProductContent.class);
	}
	
	public Product findById(Long id)
	{
		return em.find(Product.class, id);
	}
	
	public List<PackageProductContent> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PackageProductContent> criteria = cb.createQuery(PackageProductContent.class);
		Root<PackageProductContent> product = criteria.from(PackageProductContent.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}
	
	public PackageProductContent findByProductNo(String orn)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PackageProductContent> criteria = cb.createQuery(PackageProductContent.class);
		Root<PackageProductContent> product = criteria.from(PackageProductContent.class);
		criteria.select(product).where(cb.equal(product.get("productNo"), orn));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (PackageProductContent) query.getSingleResult();
		}
	}

	public List<PackageProductContent> findByPackageNo(String packageNo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PackageProductContent> criteria = cb.createQuery(PackageProductContent.class);
		Root<PackageProductContent> packageProduct = criteria.from(PackageProductContent.class);
		Join<PackageProductContent,BaseProduct> bpJoin = packageProduct.join("packageNo", JoinType.LEFT);
		criteria.select(packageProduct).where(cb.equal(packageProduct.get("packageNo"), packageNo));
		return em.createQuery(criteria).getResultList();
	}

}
