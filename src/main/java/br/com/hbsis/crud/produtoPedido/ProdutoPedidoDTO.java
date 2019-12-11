package br.com.hbsis.crud.produtoPedido;

public class ProdutoPedidoDTO {
    private int fkProduto;
    private int fkPedido;
    private int qtdComprada;

    public ProdutoPedidoDTO(int fkProduto, int fkPedido, int qtdComprada) {
        this.fkProduto = fkProduto;
        this.fkPedido = fkPedido;
        this.qtdComprada = qtdComprada;
    }

    public ProdutoPedidoDTO of(ProdutoPedido produtoPedido){
        return new ProdutoPedidoDTO(
                produtoPedido.getFkProduto().getIdProduto(),
                produtoPedido.getFkPedido().getIdPedido(),
                produtoPedido.getQuantidadeComprada());
    }

    public int getQtdComprada() {
        return qtdComprada;
    }

    public void setQtdComprada(int qtdComprada) {
        this.qtdComprada = qtdComprada;
    }

    public int getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(int fkProduto) {
        this.fkProduto = fkProduto;
    }

    public int getFkPedido() {
        return fkPedido;
    }

    public void setFkPedido(int fkPedido) {
        this.fkPedido = fkPedido;
    }
}
