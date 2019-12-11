package br.com.hbsis.crud.produtoPedido;


import br.com.hbsis.crud.pedidos.Pedido;
import br.com.hbsis.crud.produto.Produto;

import javax.persistence.*;

@Entity
@Table(name = "produto_pedido")
public class ProdutoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idProdutoPedido;
    @ManyToOne
    @JoinColumn(name = "fk_produto")
    private Produto fkProduto;
    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private Pedido fkPedido;
    @Column(name = "quantidade_comprada")
    private int QuantidadeComprada;

    public int getIdProdutoPedido() {
        return idProdutoPedido;
    }

    public void setIdProdutoPedido(int idProdutoPedido) {
        this.idProdutoPedido = idProdutoPedido;
    }

    public Produto getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(Produto fkProduto) {
        this.fkProduto = fkProduto;
    }

    public int getQuantidadeComprada() {
        return QuantidadeComprada;
    }

    public void setQuantidadeComprada(int quantidadeComprada) {
        QuantidadeComprada = quantidadeComprada;
    }

    public Pedido getFkPedido() {
        return fkPedido;
    }

    public void setFkPedido(Pedido fkPedido) {
        this.fkPedido = fkPedido;
    }
}
