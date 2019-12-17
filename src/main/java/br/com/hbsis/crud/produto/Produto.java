package br.com.hbsis.crud.produto;

import br.com.hbsis.crud.linhaProduto.Linha;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private double precoProduto;
    @Column(name = "unidade_caixa")
    private int unidadeCaixa;
    @Column(name = "peso_qtd")
    private double pesoQtd;
    @Column(name = "validade")
    private Date validade;
    @ManyToOne
    @JoinColumn(name = "fk_linha")
    @JsonIgnoreProperties
    private Linha fkLinha;
    @Column(name = "codigo_produto")
    private String codigoProduto;
    @Column(name = "medida_peso")
    private String medidaPeso;

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

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public int getUnidadeCaixa() {
        return unidadeCaixa;
    }

    public void setUnidadeCaixa(int unidadeCaixa) {
        this.unidadeCaixa = unidadeCaixa;
    }

    public double getPesoQtd() {
        return pesoQtd;
    }

    public void setPesoQtd(double pesoQtd) {
        this.pesoQtd = pesoQtd;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getMedidaPeso() {
        return medidaPeso;
    }

    public void setMedidaPeso(String medidaPeso) {
        this.medidaPeso = medidaPeso;
    }
}
