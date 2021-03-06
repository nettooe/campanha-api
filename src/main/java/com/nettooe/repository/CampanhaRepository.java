package com.nettooe.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nettooe.entity.Campanha;

public interface CampanhaRepository extends JpaRepository<Campanha, Long>, CampanhaRepositoryQuery {

	@Query("select c from Campanha c where c.inicioVigencia <= :inicio AND c.fimVigencia >= :fim")
	List<Campanha> findMesmoPeriodo(@Param("inicio") LocalDate inicioVigencia, @Param("fim") LocalDate fimVigencia);

	List<Campanha> findByfimVigenciaAndIdNot(LocalDate fimVigencia, Long id);

	@Query("select c from Campanha c where c.inicioVigencia <= trunc(current_date()) AND c.fimVigencia >= trunc(current_date()) AND c.idTimeDoCoracao = :time")
	List<Campanha> findByIdTimeDoCoracao(@Param("time") Long id);

	@Query("select c from Campanha c where c.inicioVigencia <= trunc(current_date()) AND c.fimVigencia >= trunc(current_date())")
	List<Campanha> findAllVigentes();

}
