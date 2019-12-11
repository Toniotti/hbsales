package br.com.hbsis.crud.produtoPedido;

import br.com.hbsis.crud.pedidos.Pedido;
import br.com.hbsis.crud.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Integer> {
    @Query("select fkProduto from ProdutoPedido where fkPedido = ?1")
    List<Produto> findProdutosByIdPedido(int id);

    //find produto pedio by fkPedido
    @Query("select idProdutoPedido from ProdutoPedido where fkPedido = ?1")
    List<Integer> findProdutoPedidoByPedido(Pedido pedido);

    //selecionar soma quantidade comprada, de um produto, de varios pedidos
    @Query("select new br.com.hbsis.crud.produtoPedido.ProdutoQuantidade(fkProduto.nomeProduto, sum(QuantidadeComprada)) from ProdutoPedido pedido where fkPedido IN ?1 group by fkProduto.nomeProduto")
    List<ProdutoQuantidade> pegarValorProdutos(List<Pedido> pedidos);

}
