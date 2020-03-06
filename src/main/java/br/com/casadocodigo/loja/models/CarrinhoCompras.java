package br.com.casadocodigo.loja.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/* Por padrão uma classe anotada apenas com COMPONENT será do tipo SINGLETON(scope.application)
 *  que significa que terá uma sessão global. EX: dois usuários em máquinas separas, alimentam o mesmo carrinho por conta do escopo.
 * Para resolvermos isso, colocamos a tag scope e atribuimos um contexto de scopo de sessão
 * assim será criado para cada sessão uma instância da aplicação. */

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CarrinhoCompras {
	// Aqui será o próprio carrinho de compras em si.

	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
	// Lista para associar o item a sua quantidade(quando o cliente quer mais de 1
	// produto(do mesmo produto))

	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);// adiciona um item no carrinho,
		// Caso exista o produto no carrinho, apenas é adicionado +1
		// Caso não exista, adiciona o produto selecionado.
	}

	private int getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		return itens.get(item);
		// Caso o item não exista na lista, colocamos o item e retornamos o valor 0, que
		// será incrementado pelo metódo add. Mas caso o item já exista, retornamos
		// apenas o valor que representa a quantidade de vezes que o produto foi
		// adicionado na lista.
	}

	public int getQuantidade() {
		return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador));
	}

}

/*
 * O primeiro escopo apresentado é o escopo de aplicação, isto é, desde que o
 * servidor é iniciado, apenas um objeto na memória é manipulado, o que causa
 * conflito quando temos mais de um usuário usando a nossa aplicação. O segundo
 * escopo é o de sessão, no qual o objeto é criado para cada usuário que se
 * conecta à aplicação, ou seja, usuários diferentes usam objetos diferentes, o
 * que é ideal para um carrinho de compras, uma vez que cada usuário possui o
 * seu próprio carrinho. O último escopo apresentado é o escopo de request, no
 * qual cada vez que acessamos a página, um objeto é criado.
 */
