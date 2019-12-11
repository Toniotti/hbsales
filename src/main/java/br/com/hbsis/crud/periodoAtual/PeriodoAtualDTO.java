package br.com.hbsis.crud.periodoAtual;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class PeriodoAtualDTO {
    private int idPeriodoAtual;
    @NotNull(message = "O campo da data de inicio não pode ser nulo")
    private Date dataInicio;
    @NotNull(message = "O campo da data de fim não pode ser nulo")
    private Date dataFinal;
    @NotNull(message = "O campo do fornecedor não pode ser nulo")
    private int idFornecedor;
    @NotNull(message = "O campo da data de retirada não pode ser nulo")
    private Date dataRetirada;
    @NotNull(message = "O campo da quantidade de produto vendido não pode ser nulo")
    private int qtdProdutoVendido;
    @NotNull(message = "O campo da descrição não pode ser nulo")
    @Size(max = 50, message = "A descrição não pode conter mais de 50 caracteres.")
    private String descricao;

    public PeriodoAtualDTO(){

    }

    public PeriodoAtualDTO(int idPeriodoAtual, Date dataInicio, Date dataFinal, int idFornecedor, Date dataRetirada, int qtdProdutoVendido, String descricao){
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.idFornecedor = idFornecedor;
        this.idPeriodoAtual = idPeriodoAtual;
        this.dataRetirada = dataRetirada;
        this.qtdProdutoVendido = qtdProdutoVendido;
        this.descricao = descricao;
    }

    public PeriodoAtualDTO(Date dataInicio, Date dataFinal, int idFornecedor, int qtdProdutoVendido, String descricao) {
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.idFornecedor = idFornecedor;
        this.qtdProdutoVendido = qtdProdutoVendido;
        this.descricao = descricao;
    }

    public static PeriodoAtualDTO of(PeriodoAtual periodoAtual){
        return new PeriodoAtualDTO(
                periodoAtual.getId(),
                periodoAtual.getDataInicio(),
                periodoAtual.getDataFinal(),
                periodoAtual.getFkFornecedor().getIdFornecedor(),
                periodoAtual.getDataRetirada(),
                periodoAtual.getQtdProdutoVendido(),
                periodoAtual.getDescricao());
    }

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

    public int getIdPeriodoAtual() {
        return idPeriodoAtual;
    }

    public void setIdPeriodoAtual(int idPeriodoAtual) {
        this.idPeriodoAtual = idPeriodoAtual;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
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

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
