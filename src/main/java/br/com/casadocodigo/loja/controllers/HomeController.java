package br.com.casadocodigo.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class HomeController {
	
	@RequestMapping("/") // Mapeia a requisição
	// Quando acessar alguma rota depois do "/" será redirecionado a este método
	public String index() {
		System.out.println("Entrando na Home da CDC");
		return "home";
		// Não precisa por a extensão aqui, pois já está setado no AppWebConfiguration.
	}

}
