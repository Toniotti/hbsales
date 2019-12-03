package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.fornecedor.Fornecedor;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDTO {
    private int idCategoria;
    private String nomeCategoria;
    private List<Integer> fkFornecedor;


    public CategoriaDTO() {

    }

    public CategoriaDTO(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public CategoriaDTO(int idCategoria, String nomeCategoria, List<Integer> fkFornecedor) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fkFornecedor = fkFornecedor;
    }

    public CategoriaDTO(String nomeCategoria, List<Integer> fkFornecedor) {
        this.nomeCategoria = nomeCategoria;
        this.fkFornecedor = fkFornecedor;
    }

    public static CategoriaDTO of(Categoria categoria) {
        //pegar id's dos fornecedores da categoria
        List<Fornecedor> fornecedorList = categoria.getFkFornecedor();
        List<Integer> idFornecedores = new ArrayList<>();

        idFornecedores.forEach(integer -> {idFornecedores.add(integer);});

        return new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getNomeCategoria(),
                idFornecedores
        );
    }

    public List<Integer> getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(List<Integer> fkFornecedor) {
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
