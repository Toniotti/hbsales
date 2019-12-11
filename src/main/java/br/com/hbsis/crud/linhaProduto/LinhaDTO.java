package br.com.hbsis.crud.linhaProduto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LinhaDTO {
    private int idLinha;
    @NotNull(message = "O campo da linha não pode ser nulo.")
    private int fkCategoria;
    @NotBlank(message = "O nome da linha não pode estar em branco.")
    @NotNull(message = "O nome da linha não pode ser nulo.")
    @Size(max = 50, message = "O nome da linha não pode conter mais de 50 caracteres.")
    private String nomeLinha;
    @NotBlank(message = "O codigo da linha não pode estar em branco")
    @NotNull(message = "O codigo da linha não pode ser nulo.")
    @Pattern(regexp = "[A-Za-z0-9]{0,10}", message = "O código não atende o padrão esperado.")
    private String codigoLinha;

    public LinhaDTO() {
    }

    public LinhaDTO(int idLinha, int fkCategoria, String nomeCategoria, String codigoCategoria) {
        this.idLinha = idLinha;
        this.fkCategoria = fkCategoria;
        this.nomeLinha = nomeCategoria;
        this.codigoLinha = codigoCategoria;
    }

    public LinhaDTO(int fkCategoria, String nomeCategoria, String codigoCategoria) {
        this.fkCategoria = fkCategoria;
        this.nomeLinha = nomeCategoria;
        this.codigoLinha = codigoCategoria;
    }

    public static LinhaDTO of(Linha linha){
        LinhaDTO linhaDTO = new LinhaDTO(
                linha.getIdLinha(),
                linha.getCategoria().getIdCategoria(),
                linha.getNomeLinha(),
                linha.getCodigoLinha()
        );

        return linhaDTO;
    }

    public int getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(int idLinha) {
        this.idLinha = idLinha;
    }

    public int getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(int fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }
}
