package br.com.hbsis.crud.periodoAtual;

import javax.xml.crypto.Data;
import java.util.Date;

public class PeriodoAtualDTO {
    private int idPeriodoAtual;
    private Date dataInicio;
    private Date dataFinal;
    private int idFornecedor;
    private Date dataRetirada;
    private int qtdProdutoVendido;

    public PeriodoAtualDTO(){

    }

    public PeriodoAtualDTO(int idPeriodoAtual, Date dataInicio, Date dataFinal, int idFornecedor, Date dataRetirada, int qtdProdutoVendido){
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.idFornecedor = idFornecedor;
        this.idPeriodoAtual = idPeriodoAtual;
        this.dataRetirada = dataRetirada;
        this.qtdProdutoVendido = qtdProdutoVendido;
    }

    public PeriodoAtualDTO(Date dataInicio, Date dataFinal, int idFornecedor, int qtdProdutoVendido) {
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.idFornecedor = idFornecedor;
        this.qtdProdutoVendido = qtdProdutoVendido;
    }

    public static PeriodoAtualDTO of(PeriodoAtual periodoAtual){
        return new PeriodoAtualDTO(
                periodoAtual.getId(),
                periodoAtual.getDataInicio(),
                periodoAtual.getDataFinal(),
                periodoAtual.getFkFornecedor().getIdFornecedor(),
                periodoAtual.getDataRetirada(),
                periodoAtual.getQtdProdutoVendido()
        );
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
