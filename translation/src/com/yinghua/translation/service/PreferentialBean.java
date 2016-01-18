package com.yinghua.translation.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.yinghua.translation.model.Preferential;
@Stateless
public class PreferentialBean extends AbstractFacade<Preferential> {

	private static final Logger logger = Logger.getLogger(PreferentialBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public PreferentialBean() {
		super(Preferential.class);
	}

	public Preferential findById(Long id) {
		return em.find(Preferential.class, id);
	}

	public Long createPreferential(Preferential order) {
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void updatePreferential(Preferential order) {
		em.merge(order);
	}

	public List<Preferential> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Preferential> criteria = cb.createQuery(Preferential.class);
		Root<Preferential> Preferentials = criteria.from(Preferential.class);
		criteria.select(Preferentials);
		return em.createQuery(criteria).getResultList();
	}

	public List<Preferential> findByUid(String salestrategyno) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Preferential> criteria = cb.createQuery(Preferential.class);
		
		Root<Preferential> partnercode = criteria.from(Preferential.class);
		criteria.select(partnercode).where(
				cb.equal(partnercode.get("salestrategyno"), salestrategyno));
		return em.createQuery(criteria).getResultList();
	}

	

}
