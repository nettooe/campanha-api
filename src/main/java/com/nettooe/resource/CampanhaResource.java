package com.nettooe.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nettooe.RecursoCriadoEvent;
import com.nettooe.entity.Campanha;
import com.nettooe.repository.CampanhaRepository;
import com.nettooe.repository.exception.ResourcesExceptionHandler.Erro;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/campanhas")
@Api(description = "Serviço de Gerenciamento de Campanhas")
public class CampanhaResource {

	@Autowired
	private CampanhaRepository campanhaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@ApiOperation(value = "Listar todas as campanhas vigentes", notes = "Lista todas as campanhas vigentes cadastradas")
	@ApiResponses(value = {
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public List<Campanha> listar() {
		return this.campanhaRepository.findAllVigentes();
	}
	
	@PostMapping
	@ApiOperation(value = "Grava uma campanha", notes = "Grava uma nova campanha com os dados informados. Alterará a data de fim de outras campanhas caso a data de fim seja igual.", response = Campanha.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successo ao criar a Campanha", response = Campanha.class),
			@ApiResponse(code = 400, message = "Excessão de negócio.", response = Erro.class),
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public ResponseEntity<Campanha> criar(@Valid @RequestBody Campanha campanha,
			HttpServletResponse response) {
		campanha.setId(null);
		Campanha categoriaSalva = this.campanhaRepository.gravaComPrioridade(campanha);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Consultar campanha", notes = "Retorna os dados de uma campanha através do ID informado.", response = Campanha.class)
	@ApiResponses(value = {
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public ResponseEntity<Campanha> buscarPeloCodigo(@PathVariable Long id) {
		return Optional.ofNullable(this.campanhaRepository.findOne(id))
				.map(campanha -> ResponseEntity.ok().body(campanha)).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Excluir campanha", notes = "Exclui uma campanha através do ID informado.")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Successo. Sem conteúdo para retornar."),
			@ApiResponse(code = 400, message = "Excessão de negócio.", response = Erro.class),
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public void remover(@PathVariable Long id) {
		this.campanhaRepository.delete(id);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar campanha", notes = "Atualiza os dados de uma campanha através do ID informado.", response = Campanha.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successo.", response = Campanha.class),
			@ApiResponse(code = 400, message = "Excessão de negócio.", response = Erro.class),
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public ResponseEntity<Campanha> atualizar(@PathVariable Long id, @Valid @RequestBody Campanha campanhaNova) {
		try {
			return Optional.ofNullable(this.campanhaRepository.findOne(id))
					.map(campanha -> this.atualizar(campanhaNova, campanha))
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/time/{id}")
	@ApiOperation(value = "Listar todas as campanhas vigentes para o time informado", notes = "Lista todas as campanhas vigentes cadastradas para o time informado.")
	@ApiResponses(value = {
			@ApiResponse(code = 401, message = "Não autorizado"),
			@ApiResponse(code = 403, message = "Acesso proibido"),
			@ApiResponse(code = 404, message = "Recurso não encontrado") })
	public List<Campanha> listarTodasByTime(@PathVariable Long id) {
		return this.campanhaRepository.findByIdTimeDoCoracao(id);
	}

	private ResponseEntity<Campanha> atualizar(Campanha campanhaNova, Campanha campanha) {
		BeanUtils.copyProperties(campanhaNova, campanha, "id");
		this.campanhaRepository.gravaComPrioridade(campanha);
		return ResponseEntity.ok().body(campanha);
	}

}
