package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired // Injeta o produtodao aqui
	private ProdutoDAO produtoDao;
	
	@Autowired
	private FileSaver fileSaver;

	@InitBinder // para o spring reconhecer que ESTE método fara o binder entre as duas classes.
	public void initBinder(WebDataBinder binder) {
		// Binder será responsável por conectar duas coisas, no caso, a validação com o controller.
		binder.addValidators(new ProdutoValidation());
		// Adicionamos aqui a classe de validação ao binder, PORÉM essa classe deve implementar a interface VALIDATOR.
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		TipoPreco.values();
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {

		
		
		if (result.hasErrors()) {
			return form(produto);
			//Caso tenhas erros de validação, irá "retornar ao form" no caso, irá continuar no form. 
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);// colocando dentro do produto o path.

		produtoDao.gravar(produto);

		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		// O Flash Scoped é um escopo onde os objetos que adicionamos nele através do
		// método addFlashAttribute ficam vivos de um request para outro, enquanto o
		// navegador executa um redirect (usando o código de status 302).

		return new ModelAndView("redirect:produtos");
		// Redirecionar o usuário para o "/produtos", pois o POST guarda as info
		// e a cada att de pag, irá salvar no banco de novo. Este retorno, evita isso.
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDao.listar();// retorna uma lista de produtos.
		ModelAndView modelAndView = new ModelAndView("/produtos/lista");
		// Manda a lista para a página pelo mav. passando a página que vamos buscar a
		// lista.
		modelAndView.addObject("produtos", produtos);
		// adiciona os produtos da lista no model and view o 1º parâmetro será um alias.
		return modelAndView;

	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		Produto produto = produtoDao.find(id);
		modelAndView.addObject("produto", produto);
		
		return modelAndView;
		
	}
}
