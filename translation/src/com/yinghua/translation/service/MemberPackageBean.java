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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.enumeration.Language;
import com.yinghua.translation.util.ClassLoaderUtil;

@Stateless
public class MemberPackageBean extends AbstractFacade<MemberPackage>
{
	private static final Logger logger = Logger.getLogger(MemberPackageBean.class
			.getCanonicalName());
	private Properties prop = ClassLoaderUtil.getProperties("key.properties");

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}
	
	public MemberPackageBean()
	{
		// TODO Auto-generated constructor stub
		super(MemberPackage.class);
	}
	
	public MemberPackage findById(Long id)
	{
		return em.find(MemberPackage.class, id);
	}
	
	public void updateMemberPackage(MemberPackage mp)
	{
		em.merge(mp);
	}
	
	public List<MemberPackage> finByMemberId(String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberPackage> criteria = cb.createQuery(MemberPackage.class);
		Root<MemberPackage> member = criteria.from(MemberPackage.class);
		criteria.select(member).where(
				cb.equal(member.get("memberNumber"), uno));
		return em.createQuery(criteria).getResultList();
	}
	
	public MemberPackage findMemberPackage(String uno,String lanuage)
	{
//		Query query = em.createQuery("select p from MemberPackage p where p.memberNumber=:uno and p.languages=:lan");
//		query.setParameter("uno", uno);
//		query.setParameter("lan", lanuage);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberPackage> criteria = cb.createQuery(MemberPackage.class);
		Root<MemberPackage> page = criteria.from(MemberPackage.class);
//		criteria.select(page).where(cb.equal(page.get("memberNumber"), uno).);
//		cb.and(cb.equal(page.get("language"), lanuage));
		Predicate p = cb.and(cb.equal(page.get("memberNumber"), uno),cb.equal(page.get("languages"), Language.getLanguageName(Language.getLanguageIndex(lanuage))));
		Query query = em.createQuery(criteria.where(p));
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (MemberPackage) query.getSingleResult();
		}
	}

}
