package com.yinghua.translation.service;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import com.yinghua.translation.model.CommonData;
import com.yinghua.translation.model.MemberOrder;
import com.yinghua.translation.model.enumeration.CommonType;
import com.yinghua.translation.model.enumeration.OrderUseStatus;

@Stateless
public class CommonDataBean extends AbstractFacade<CommonData>
{
	private static final Logger logger = Logger.getLogger(CommonDataBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public CommonDataBean()
	{
		super(CommonData.class);
	}

	public CommonData findById(Long id)
	{
		return em.find(CommonData.class, id);
	}

	public Long createCommonData(CommonData order)
	{
		// check if user exists
		super.create(order);
		return order.getId();
	}

	public void update(CommonData order)
	{
		em.merge(order);
	}

	public List<CommonData> findAll()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CommonData> criteria = cb.createQuery(CommonData.class);
		Root<CommonData> product = criteria.from(CommonData.class);
		criteria.select(product);
		return em.createQuery(criteria).getResultList();
	}

	public CommonData findByOrderNo(String orderNo)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CommonData> criteria = cb.createQuery(CommonData.class);
		Root<CommonData> order = criteria.from(CommonData.class);
		criteria.select(order).where(cb.equal(order.get("orderNo"), orderNo));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
			
		{
			return (CommonData) query.getSingleResult();
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

	public List<MemberOrder> findUsingOrderByUno(String uno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemberOrder> criteria = cb.createQuery(MemberOrder.class);
		Root<MemberOrder> order = criteria.from(MemberOrder.class);
		criteria.select(order).where(cb.equal(order.get("memberNumber"), uno),cb.equal(order.get("useState"), OrderUseStatus.USING));
		return em.createQuery(criteria).getResultList();
	}

	public List<CommonData> findByCommonType(String commonType) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CommonData> criteria = cb.createQuery(CommonData.class);
		Root<CommonData> comData = criteria.from(CommonData.class);
		criteria.select(comData).where(cb.equal(comData.get("commonType"), CommonType.getCommonType(CommonType.getCommonIndex(commonType))))
		.orderBy(cb.asc(comData.get("belongType")),cb.asc(comData.get("level")));
		return em.createQuery(criteria).getResultList();
	}
	
}
