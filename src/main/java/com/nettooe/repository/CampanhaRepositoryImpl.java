package com.nettooe.repository;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nettooe.entity.Campanha;
import com.nettooe.repository.exception.DataInicioMaiorQueDataFimVigenciaException;

public class CampanhaRepositoryImpl implements CampanhaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	CampanhaRepository campanhaRepository;

	@Override
	@Transactional
	public Campanha gravaComPrioridade(Campanha campanha) {

		if (campanha.getInicioVigencia().isAfter(campanha.getFimVigencia())) {
			throw new DataInicioMaiorQueDataFimVigenciaException();
		}

		List<Campanha> campanhasMesmofimVigencia = campanhaRepository.findMesmoPeriodo(campanha.getInicioVigencia(), campanha.getFimVigencia());
		for (Iterator<Campanha> iterator = campanhasMesmofimVigencia.iterator(); iterator.hasNext();) {
			Campanha c = (Campanha) iterator.next();
			c.setFimVigencia(c.getFimVigencia().plusDays(1));
			this.encaixar(c);
		}

		manager.persist(campanha);
		this.encaixar(campanha);

		return campanha;

	}

	private void encaixar(Campanha c) {
		List<Campanha> campanhasPostergar = campanhaRepository.findByfimVigenciaAndIdNot(c.getFimVigencia(), c.getId());
		if(!campanhasPostergar.isEmpty()) {
			Campanha postergar = campanhasPostergar.get(0);
			postergar.setFimVigencia(postergar.getFimVigencia().plusDays(1));
			manager.merge(postergar);
			this.encaixar(postergar);
		}
		
	}

}
