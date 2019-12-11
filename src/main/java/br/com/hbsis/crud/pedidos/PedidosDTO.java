package br.com.hbsis.crud.pedidos;

import java.util.Date;
import java.util.List;

public class PedidosDTO {
    private int idFuncionario;
    private int qtdCompra;
    private List<Integer> produtos;
    private List<Integer> qtdComprada;
    private Date dataCricao;
    private int Fornecedor;

    public PedidosDTO() {
    }

    public PedidosDTO(int idFuncionario, int qtdCompra, List<Integer> produtos) {
        this.idFuncionario = idFuncionario;
        this.qtdCompra = qtdCompra;
        this.produtos = produtos;
    }

    public PedidosDTO(int idFuncionario, int qtdCompra, List<Integer> produtos, List<Integer> qtdComprada, Date dataCricao, int fornecedor) {
        this.idFuncionario = idFuncionario;
        this.qtdCompra = qtdCompra;
        this.produtos = produtos;
        this.qtdComprada = qtdComprada;
        this.dataCricao = dataCricao;
        Fornecedor = fornecedor;
    }

    public int getFornecedor() {
        return Fornecedor;
    }

    public void setFornecedor(int fornecedor) {
        Fornecedor = fornecedor;
    }

    public Date getDataCricao() {
        return dataCricao;
    }

    public void setDataCricao(Date dataCricao) {
        this.dataCricao = dataCricao;
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

}
