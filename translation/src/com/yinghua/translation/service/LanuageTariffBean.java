package com.yinghua.translation.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.yinghua.translation.model.LanuageTariff;
import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.TimedTask;
import com.yinghua.translation.model.TranslationRecord;
import com.yinghua.translation.model.enumeration.Language;

@Stateless
public class LanuageTariffBean extends AbstractFacade<LanuageTariff>
{

	private static final Logger logger = Logger.getLogger(LanuageTariffBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public LanuageTariffBean()
	{
		super(LanuageTariff.class);
	}

	public void register(LanuageTariff lanuageTariff)
	{
		super.create(lanuageTariff);
	}
	
	public void updateMember(LanuageTariff lanuageTariff)
	{
		em.merge(lanuageTariff);
	}

	public LanuageTariff findById(Long id)
	{
		return em.find(LanuageTariff.class, id);
	}

	public LanuageTariff findByLanguage(String language)
	{
//		Query query = em.createQuery("select p from MemberPackage p where p.memberNumber=:uno and p.languages=:lan");
//		query.setParameter("uno", uno);
//		query.setParameter("lan", lanuage);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<LanuageTariff> criteria = cb.createQuery(LanuageTariff.class);
		Root<LanuageTariff> lan = criteria.from(LanuageTariff.class);
//		criteria.select(page).where(cb.equal(page.get("memberNumber"), uno).);
//		cb.and(cb.equal(page.get("language"), lanuage));
		criteria.select(lan).where(cb.equal(lan.get("languages"), Language.getLanguageName(Language.getLanguageIndex(language))));
		//Predicate p = cb.and(cb.equal(page.get("memberNumber"), uno),cb.equal(page.get("languages"), Language.getLanguageName(Language.getLanguageIndex(lanuage))));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (LanuageTariff) query.getSingleResult();
		}
	}
}
