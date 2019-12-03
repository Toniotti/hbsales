package br.com.hbsis.crud.produtoPedido;

public class ProdutoPedidoDTO {
    private int fkProduto;
    private int fkPedido;

    public ProdutoPedidoDTO(int fkProduto, int fkPedido) {
        this.fkProduto = fkProduto;
        this.fkPedido = fkPedido;
    }

    public ProdutoPedidoDTO of(ProdutoPedido produtoPedido){
        return new ProdutoPedidoDTO(
                produtoPedido.getFkProduto().getIdProduto(),
                produtoPedido.getFkPedido().getIdPedido()
        );
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
