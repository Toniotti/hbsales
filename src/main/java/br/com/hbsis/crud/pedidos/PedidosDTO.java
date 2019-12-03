package br.com.hbsis.crud.pedidos;

import java.util.List;

public class PedidosDTO {
    private int idFuncionario;
    private int qtdCompra;
    private List<Integer> produtos;
    private List<Integer> qtdComprada;

    public PedidosDTO() {
    }

    public PedidosDTO(int idFuncionario, int qtdCompra, List<Integer> produtos) {
        this.idFuncionario = idFuncionario;
        this.qtdCompra = qtdCompra;
        this.produtos = produtos;
    }

    public PedidosDTO(int idFuncionario, int qtdCompra, List<Integer> produtos, List<Integer> qtdComprada) {
        this.idFuncionario = idFuncionario;
        this.qtdCompra = qtdCompra;
        this.produtos = produtos;
        this.qtdComprada = qtdComprada;
    }

    public List<Integer> getProdutos() {
        return produtos;
    }

    public List<Integer> getQtdComprada() {
        return qtdComprada;
    }

    public void setQtdComprada(List<Integer> qtdComprada) {
        this.qtdComprada = qtdComprada;
    }

    public void setProdutos(List<Integer> produtos) {
        this.produtos = produtos;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getQtdCompra() {
        return qtdCompra;
    }

    public void setQtdCompra(int qtdCompra) {
        this.qtdCompra = qtdCompra;
    }
}
