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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yinghua.translation.model.Member;
import com.yinghua.translation.model.MemberPackage;
import com.yinghua.translation.model.enumeration.Language;

@Stateless
public class MemberBean extends AbstractFacade<Member>
{

	private static final Logger logger = Logger.getLogger(MemberBean.class
			.getCanonicalName());

	@Inject
	private Event<Member> memberEventSrc;

	@PersistenceContext(unitName = "translationPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager()
	{
		return em;
	}

	public MemberBean()
	{
		super(Member.class);
	}

	public Long register(Member member)
	{

		// check if user exists
		if (findByMobile(member.getMobilePhone()) == null)
		{
			super.create(member);
			memberEventSrc.fire(member);
			return member.getId();
		}
		else
		{
			return 0L;
		}
	}
	
	public void updateMember(Member member)
	{
		em.merge(member);
		memberEventSrc.fire(member);
	}

	public Member findById(Long id)
	{
		return em.find(Member.class, id);
	}

	public Member findByMobile(String mobile)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		criteria.select(member).where(
				cb.equal(member.get("mobilePhone"), mobile));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Member) query.getSingleResult();
		}
	}

	public Member findByEmail(String email)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).where(cb.equal(member.get(Member_.name),
		// email));
		criteria.select(member).where(cb.equal(member.get("email"), email));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Member) query.getSingleResult();
		}
	}
	
	public Member findByMemberNo(String mno)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		criteria.select(member).where(cb.equal(member.get("memberNumber"), mno));
		Query query = em.createQuery(criteria);
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Member) query.getSingleResult();
		}
	}

	public List<Member> findAllOrderedByName()
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		return em.createQuery(criteria).getResultList();
	}
	
	
	public Member findMember(String phone,String voip)
	{
//		Query query = em.createQuery("select p from MemberPackage p where p.memberNumber=:uno and p.languages=:lan");
//		query.setParameter("uno", uno);
//		query.setParameter("lan", lanuage);
		if(StringUtils.isEmpty(phone))//如果VOIP的格式为xxxxx_xxxxx,需要把下划线左侧的内容去掉,剩下的是手机号
		{
			if(StringUtils.contains(voip,"_"))
				phone = voip.substring(voip.indexOf("_")+1);
            else
			    phone = voip;
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
//		criteria.select(page).where(cb.equal(page.get("memberNumber"), uno).);
//		cb.and(cb.equal(page.get("language"), lanuage));
		Predicate p = cb.or(cb.equal(member.get("mobilePhone"), phone),cb.equal(member.get("voip"), voip));
		Query query = em.createQuery(criteria.where(p));
		if (query.getResultList().size() == 0)
		{
			return null;
		}
		else
		{
			return (Member) query.getSingleResult();
		}
	}
	
}
