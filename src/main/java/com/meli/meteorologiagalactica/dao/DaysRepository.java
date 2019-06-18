package com.meli.meteorologiagalactica.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.meli.meteorologiagalactica.model.ClimaticCondition;
import com.meli.meteorologiagalactica.model.Days;

/**
 * DAO para operaciones sobre la tabla Days
 * 
 * @author lposca
 *
 */
@Repository
public class DaysRepository {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Retorna todos los registros de la tabla Days
	 * 
	 * @return
	 */
	public List<ClimaticCondition> getAll() {
		List resultList = em.createQuery("from Days").getResultList();
		return resultList;
	}

	/**
	 * Borra todos los registros de la tabla Days
	 */
	@Transactional
	public void deleteAll() {

		em.createQuery("delete from Days").executeUpdate();
	}
	/**
	 * Guarda un registro de la tabla Days
	 * @param day
	 */
	@Transactional
	public void save(Days day) {
		em.persist(day);

	}

	@Transactional
	public void save(Collection<Days> values) {

		for (Days d : values) {
			em.persist(d);
		}

	}

	public Days loadDay(long day) {

		Query q = em.createQuery("from Days where daynumber = :d");
		q.setParameter("d", day);
		List r = q.getResultList();
		if (r.isEmpty()) {
			return null;

		}
		return (Days) r.get(0);
	}

}
