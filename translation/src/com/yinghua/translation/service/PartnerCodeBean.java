package com.yinghua.translation.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.yinghua.translation.model.PartnerCode;

@Stateless
public class PartnerCodeBean extends AbstractFacade<PartnerCode> {

	private static final Logger logger = Logger.getLogger(PartnerCodeBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public PartnerCodeBean() {
		super(PartnerCode.class);
	}

	public PartnerCode findById(Long id) {
		return em.find(PartnerCode.class, id);
	}

	public Long createPartnerCode(PartnerCode order) {
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void updatePartnerCode(PartnerCode order) {
		em.merge(order);
	}

	public List<PartnerCode> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PartnerCode> criteria = cb.createQuery(PartnerCode.class);
		Root<PartnerCode> partnerCodes = criteria.from(PartnerCode.class);
		criteria.select(partnerCodes);
		return em.createQuery(criteria).getResultList();
	}

	public List<PartnerCode> findByUid(String code) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PartnerCode> criteria = cb.createQuery(PartnerCode.class);
		
		Root<PartnerCode> partnercode = criteria.from(PartnerCode.class);
		criteria.select(partnercode).where(
				cb.equal(partnercode.get("code"), code));
		return em.createQuery(criteria).getResultList();
	}

}
