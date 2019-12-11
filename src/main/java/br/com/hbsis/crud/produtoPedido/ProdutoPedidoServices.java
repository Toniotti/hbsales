package br.com.hbsis.crud.produtoPedido;

import br.com.hbsis.crud.funcionario.FuncionarioService;
import br.com.hbsis.crud.pedidos.Pedido;
import br.com.hbsis.crud.produto.Produto;
import br.com.hbsis.crud.produto.ProdutoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoPedidoServices {
    private final ProdutoPedidoRepository produtoPedidoRepository;

    public ProdutoPedidoServices(ProdutoPedidoRepository produtoPedidoRepository) {
        this.produtoPedidoRepository = produtoPedidoRepository;
    }

    //pegar produtos por pedido
    public List<Produto> findByPedido(int idPedido) {
        return this.produtoPedidoRepository.findProdutosByIdPedido(idPedido);
    }

    //delete
    public void delete(ProdutoPedido produtoPedido) {
        this.produtoPedidoRepository.delete(produtoPedido);
    }

    //save
    public void save(Pedido pedido, Produto produto, int qtdComprada) {

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setFkPedido(pedido);
        Produto entidadeProduto = produto;
        produtoPedido.setFkProduto(entidadeProduto);
        produtoPedido.setQuantidadeComprada(qtdComprada);

        produtoPedido = this.produtoPedidoRepository.save(produtoPedido);
    }

    //find element by id
    public ProdutoPedido findElementById(int id) {
        Optional<ProdutoPedido> produtoPedidoOptional = this.produtoPedidoRepository.findById(id);

        if (produtoPedidoOptional.isPresent()) {
            return produtoPedidoOptional.get();
        }
        throw new IllegalArgumentException(String.format("O id(%s) do produto pedido informado não existe.", id));
    }

    //find produto pedido by pedido
    public List<ProdutoPedido> findProdutoPedidoByPedido(Pedido pedido) {
        List<Integer> produtoPedidoByPedido = this.produtoPedidoRepository.findProdutoPedidoByPedido(pedido);
        List<ProdutoPedido> produtoPedidos = new ArrayList<>();

        produtoPedidoByPedido.forEach(
                integer -> {
                    produtoPedidos.add(this.findElementById(integer));
                }
        );

        return produtoPedidos;
    }

    //listar produto pedido por funcionario
    public List<List<ProdutoPedido>> listByFuncionario(List<Pedido> pedidos) {

        List<ProdutoPedido> produtoPedidoArray;

        List<List<ProdutoPedido>> produtosPedidos = new ArrayList<>();

        for (int i = 0; i < pedidos.size(); i++) {
            produtoPedidoArray = new ArrayList<>();
            List<Integer> idProdutoPedidos = this.produtoPedidoRepository.findProdutoPedidoByPedido(pedidos.get(i));

            idProdutoPedidos.forEach(integer -> System.out.println(integer));

            for (int j = 0; j < idProdutoPedidos.size(); j++) {
                produtoPedidoArray.add(this.findElementById(idProdutoPedidos.get(j)));
            }
            produtosPedidos.add(produtoPedidoArray);
        }

        return produtosPedidos;
    }
}
