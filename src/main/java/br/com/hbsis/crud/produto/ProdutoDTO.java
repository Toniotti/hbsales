package br.com.hbsis.crud.produto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class ProdutoDTO {
    private int idProduto;
    @NotNull(message = "O campo da linha não pode ser nulo.")
    private int idLinha;
    @Size(max = 200)
    @NotBlank(message = "O campo do nome não pode ficar em branco.")
    @NotNull(message = "O campo do nome não pode ser nulo.")
    private String nomeProduto;
    @NotNull(message = "O campo do preço não pode ser nulo.")
    private double precoProduto;
    @NotNull(message = "O campo da unidade por caixa não pode ser nulo.")
    private int unidadeCaixa;
    @NotNull(message = "O campo do peso não pode ser nulo.")
    private double pesoQtd;
    @NotNull(message = "O campo da validade não pode ser nulo.")
//    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
    private Date validade;
    @NotNull(message = "O cmapo do codigo não pode ser nulo.")
    @NotBlank(message = "O campo do código não pode estar em branco.")
    @Pattern(regexp = "[A-Za-z0-9]{0,10}", message = "O código não atende o padrão esperado.")
    @Size(max = 10)
    private String codigoProduto;
    @NotNull(message = "O campo da medida do peso não pode ser nulo.")
    @NotBlank(message = "O campo da medidad de peso não pode ficar em branco.")
    @Size(max = 2, message = "A medida de peso não está no padrão esperado.")
    private String medidaPeso;

    public ProdutoDTO() {

    }

    public ProdutoDTO(int idLinha, String nomeProduto, double precoProduto, int unidadeCaixa, double pesoQtd, Date validade, String codigoProduto, @NotNull(message = "O campo da medida do peso não pode ser nulo.") @NotBlank(message = "O campo da medidad de peso não pode ficar em branco.") @Pattern(regexp = "([Kg]{1}|[g]{1}|[mg]{1})") String medidaPeso) {
        this.idLinha = idLinha;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.unidadeCaixa = unidadeCaixa;
        this.pesoQtd = pesoQtd;
        this.validade = validade;
        this.codigoProduto = codigoProduto;
        this.medidaPeso = medidaPeso;
    }

    public ProdutoDTO(int idProduto, int idLinha, String nomeProduto, double precoProduto, int unidadeCaixa, double pesoQtd, Date validade, String codigoProduto, @NotNull(message = "O campo da medida do peso não pode ser nulo.") @NotBlank(message = "O campo da medidad de peso não pode ficar em branco.") @Pattern(regexp = "([Kg]{1}|[g]{1}|[mg]{1})") String medidaPeso) {
        this.idLinha = idLinha;
        this.nomeProduto = nomeProduto;
        this.pesoQtd = pesoQtd;
        this.precoProduto = precoProduto;
        this.unidadeCaixa = unidadeCaixa;
        this.validade = validade;
        this.idProduto = idProduto;
        this.codigoProduto = codigoProduto;
        this.medidaPeso = medidaPeso;
    }

    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getIdProduto(),
                produto.getLinha().getIdLinha(),
                produto.getNomeProduto(),
                produto.getPrecoProduto(),
                produto.getUnidadeCaixa(),
                produto.getPesoQtd(),
                produto.getValidade(),
                produto.getCodigoProduto(),
                produto.getMedidaPeso()
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
