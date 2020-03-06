package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Produto;

// Validação do produto, para não colocar valores inválido ou deixar o campo vazio.
public class ProdutoValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// O método supports indica a qual classe a validação dará suporte.
		//O que esse código faz é verificar se o objeto recebido para a validação tem uma assinatura da classe Produto. 
		//Com isso o Spring pode verificar se o objeto é uma instância daquela classe ou não.
		return Produto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//ValidationUtils - Classe do Spring que valida dados.
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		
		// VU.rejeiteSeForVazio(Objeto que contém erros de validação, nome do campo a ser validado, errorCode conhecido pelo Spring)
		// Não é necessário retornar nada, pois o Objeto errors já contêm as informações se falhou ou não.

		Produto produto = (Produto) target;
		// Não temos mais o objeto Produtom temos um target do Tipo object e para resolver isso, fizemos um cast do target para Produto.
		
		if (produto.getPaginas() <= 0) { // Validamos a paginas com if, pois é um número.
			errors.rejectValue("paginas", "field.required");
			// Usamos o errors para rejeitar o valor(campo a ser validado, errorCode).
		}

	}
}
