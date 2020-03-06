package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoComprasController {

	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private CarrinhoCompras carrinho;

	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipo) {
		Produto produto = produtoDao.find(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipo);
		return carrinhoItem;
	}

	@RequestMapping("/add") // faz a listagem para /carinho/add
	public ModelAndView add(Integer produtoId, TipoPreco tipo) {
		// Método que irá adicionar os produtos no carrinho de compras, recebe o ID do
		// produto(pois somente o ID e não o produto em si será enviando pelo form, e o
		// seu tipo.
		ModelAndView modelAndView = new ModelAndView("redirect:/produtos");
		// Ao escolher um produto, o user será redirecionado para a listagem de
		// produtos.
		CarrinhoItem carrinhoItem = criaItem(produtoId, tipo);
		carrinho.add(carrinhoItem);

		return modelAndView;
	}

}
