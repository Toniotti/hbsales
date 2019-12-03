package br.com.hbsis.crud.produto;

import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.linhaProduto.Linha;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private int idProduto;
    @Column(name = "nome_produto")
    private String nomeProduto;
    @Column(name = "preco_produto")
    private float precoProduto;
    @Column(name = "unidade_caixa")
    private int unidadeCaixa;
    @Column(name = "peso_qtd")
    private float pesoQtd;
    @Column(name = "validade")
    private Date validade;
    @ManyToOne
    @JoinColumn(name = "fk_linha")
    private Linha fkLinha;

    public Linha getLinha() {
        return fkLinha;
    }

    public void setLinha(Linha linha) {
        this.fkLinha = linha;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public float getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(float precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getUnidadeCaixa() {
        return unidadeCaixa;
    }

    public void setUnidadeCaixa(int unidadeCaixa) {
        this.unidadeCaixa = unidadeCaixa;
    }

    public float getPesoQtd() {
        return pesoQtd;
    }

    public void setPesoQtd(float pesoQtd) {
        this.pesoQtd = pesoQtd;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }
}
