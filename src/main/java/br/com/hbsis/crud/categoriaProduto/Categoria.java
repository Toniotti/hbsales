package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.fornecedor.Fornecedor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private int idCategoria;
    @NotNull(message = "O nome da categoria não pode ser nulo.")
    @NotBlank(message = "O nome da categoria não pode estar vazio.")
    @Column(name = "nome_categoria", unique = true, nullable = false, length = 120)
    private String nomeCategoria;
    @OneToOne
    @JoinColumn(name = "fk_fornecedor")
    private Fornecedor fornecedor;
    @Column(name = "codigo_categoria")
    private String codigoCategoria;

    public String getCodigoCategoria() {
        return codigoCategoria;
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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }
}
