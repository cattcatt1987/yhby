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

import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.Order;
import com.yinghua.translation.model.TimedTask;
import com.yinghua.translation.model.TranslationRecord;
import com.yinghua.translation.model.enumeration.Language;

@Stateless
public class TimedTaskBean extends AbstractFacade<TimedTask>
{

	private static final Logger logger = Logger.getLogger(TimedTaskBean.class
			.getCanonicalName());

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public TimedTaskBean()
	{
		super(TimedTask.class);
	}

	public void register(TimedTask timedTask)
	{
		super.create(timedTask);
	}
	
	public void updateMember(TimedTask timedTask)
	{
		em.merge(timedTask);
	}

	public TimedTask findById(Long id)
	{
		return em.find(TimedTask.class, id);
	}

	
	public List<TimedTask> findAllOrderedByTime()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimedTask> criteria = cb.createQuery(TimedTask.class);
		Root<TimedTask> timedTask = criteria.from(TimedTask.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(timedTask).orderBy(cb.asc(timedTask.get("createTime")));
		return em.createQuery(criteria).getResultList();
	}
	
	public TimedTask findTimedTask(String uno,String callId)
	{
//		Query query = em.createQuery("select p from MemberPackage p where p.memberNumber=:uno and p.languages=:lan");
//		query.setParameter("uno", uno);
//		query.setParameter("lan", lanuage);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimedTask> criteria = cb.createQuery(TimedTask.class);
		Root<TimedTask> task = criteria.from(TimedTask.class);
//		criteria.select(page).where(cb.equal(page.get("memberNumber"), uno).);
//		cb.and(cb.equal(page.get("language"), lanuage));
		Predicate p = cb.and(cb.equal(task.get("memberNumber"), uno),cb.equal(task.get("callId"), callId));
		Query query = em.createQuery(criteria.where(p));
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (TimedTask) query.getSingleResult();
		}
	}
	
}
