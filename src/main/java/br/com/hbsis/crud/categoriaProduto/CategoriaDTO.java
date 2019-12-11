package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.fornecedor.Fornecedor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDTO {
    private int idCategoria;
    @NotBlank(message = "O nome da categoria não pode estar vazio.")
    @NotNull(message = "O nome da categoria não pode ser nulo.")
    private String nomeCategoria;
    @Pattern(regexp = "[\\d]{1,3}", message = "O codigo deve conter somente números e deve conter de 1-3 digitos.")
    @NotBlank(message = "O codigo não pode estar vazio.")
    @NotNull(message = "O codigo não pode ser nulo.")
    private String codigoCategoria;
    @NotNull(message = "O fornecedor precisa ser informado.")
    private int fkFornecedor;


    public CategoriaDTO() {

    }

    public CategoriaDTO(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public CategoriaDTO(int idCategoria, String nomeCategoria, int fkFornecedor) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fkFornecedor = fkFornecedor;
    }

    public CategoriaDTO(String nomeCategoria, int fkFornecedor, String codigoCategoria) {
        this.nomeCategoria = nomeCategoria;
        this.fkFornecedor = fkFornecedor;
        this.codigoCategoria = codigoCategoria;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getNomeCategoria(),
                categoria.getFornecedor().getIdFornecedor()
        );
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public int getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(int fkFornecedor) {
        this.fkFornecedor = fkFornecedor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
