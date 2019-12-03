package br.com.hbsis.crud.produto;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProdutoDTO {
    private int idProduto;
    private int idLinha;
    private String nomeProduto;
    private float precoProduto;
    private int unidadeCaixa;
    private float pesoQtd;
    private Date validade;

    public ProdutoDTO(){

    }

    public ProdutoDTO(int idLinha, String nomeProduto, float precoProduto, int unidadeCaixa, float pesoQtd, Date validade) {
        this.idLinha = idLinha;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCaixa = unidadeCaixa;
        this.pesoQtd = pesoQtd;
        this.validade = validade;
    }

    public ProdutoDTO(int idProduto, int idLinha, String nomeProduto, float precoProduto, int unidadeCaixa, float pesoQtd, Date validade) {
        this.idLinha = idLinha;
        this.nomeProduto = nomeProduto;
        this.pesoQtd = pesoQtd;
        this.precoProduto = precoProduto;
        this.unidadeCaixa = unidadeCaixa;
        this.validade = validade;
        this.idProduto = idProduto;
    }

    public static ProdutoDTO of(Produto produto){
        return new ProdutoDTO(
                produto.getIdProduto(),
                produto.getLinha().getIdLinha(),
                produto.getNomeProduto(),
                produto.getPrecoProduto(),
                produto.getUnidadeCaixa(),
                produto.getPesoQtd(),
                produto.getValidade()
                );
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(int idLinha) {
        this.idLinha = idLinha;
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
