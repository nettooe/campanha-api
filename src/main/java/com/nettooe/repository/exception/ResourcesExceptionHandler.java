package com.nettooe.repository.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourcesExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({ DataInicioMaiorQueDataFimVigenciaException.class })
	public ResponseEntity<Object> handleDataInicioMaiorQueDataFimVigenciaException(
			DataInicioMaiorQueDataFimVigenciaException ex) {
		String mensagemUsuario = messageSource.getMessage("campanha.invalid.datainicio-maiorque-datafim", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	// private List<Erro> criarListaDeErros(BindingResult bindingResult) {
	// List<Erro> erros = new ArrayList<>();
	//
	// for (FieldError fieldError : bindingResult.getFieldErrors()) {
	// String mensagemUsuario = messageSource.getMessage(fieldError,
	// LocaleContextHolder.getLocale());
	// String mensagemDesenvolvedor = fieldError.toString();
	// erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
	// }
	//
	// return erros;
	// }

	public static class Erro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

	}
}
