package com.meli.meteorologiagalactica.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.meli.meteorologiagalactica.model.ClimaticCondition;

/**
 * DAO para operaciones sobre la tabla ClimaticCondition
 * 
 * @author lposca
 *
 */
@Repository
public class ClimaticConditionRepository {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Retorna todos los registros de la tabla ClimaticCondition
	 * 
	 * @return
	 */
	public List<ClimaticCondition> getAll() {
		List resultList = em.createQuery("from ClimaticCondition").getResultList();
		return resultList;
	}

	/**
	 * Borra todos los registros de la tabla ClimaticCondition
	 */
	@Transactional
	public void deleteAll() {

		em.createQuery("delete from ClimaticCondition").executeUpdate();
	}

	/**
	 * Guarda un registro de la tabla ClimaticCondition
	 * 
	 * @param cc
	 */
	@Transactional
	public void save(ClimaticCondition cc) {
		em.persist(cc);

	}
}
