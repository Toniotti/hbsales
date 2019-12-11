package br.com.hbsis.crud.periodoAtual;

import br.com.hbsis.crud.fornecedor.Fornecedor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "periodo_atual")
public class PeriodoAtual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodo_atual")
    private int idPeriodoAtual;
    @Column(name = "data_inicio", unique = false, nullable = false)
    private Date dataInicio;
    @Column(name = "data_final", unique = false, nullable = false)
    private Date dataFinal;
    @Column(name = "data_retirada", unique = false, nullable = false)
    private Date dataRetirada;
    @Column(name = "qtd_produto_vendido", unique = false, nullable = false)
    private int qtdProdutoVendido;
    @ManyToOne
    @JoinColumn(name = "fk_fornecedor", unique = true, nullable = false)
    private Fornecedor fkFornecedor;
    @Column(name = "descricao")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtdProdutoVendido() {
        return qtdProdutoVendido;
    }

    public void setQtdProdutoVendido(int qtdProdutoVendido) {
        this.qtdProdutoVendido = qtdProdutoVendido;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public int getId() {
        return idPeriodoAtual;
    }

    public void setId(int id) {
        this.idPeriodoAtual = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Fornecedor getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(Fornecedor fkFornecedor) {
        this.fkFornecedor = fkFornecedor;
    }
}
