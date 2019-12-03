package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.fornecedor.Fornecedor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private int idCategoria;
    @Column(name = "nome_categoria", unique = true, nullable = false, length = 120)
    private String nomeCategoria;
    @ManyToMany
    @JoinTable(name = "categoria_fornecedor", joinColumns = {@JoinColumn(name = "fk_categoria", referencedColumnName = "id_categoria")},
    inverseJoinColumns = {@JoinColumn(name = "fk_fornecedor", referencedColumnName = "id_fornecedor")})
    private List<Fornecedor> fkFornecedor;

    public List<Fornecedor> getFkFornecedor() {
        return fkFornecedor;
    }

    public void setFkFornecedor(List<Fornecedor> fkFornecedor) {
        this.fkFornecedor = fkFornecedor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int id) {
        this.idCategoria = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

}
