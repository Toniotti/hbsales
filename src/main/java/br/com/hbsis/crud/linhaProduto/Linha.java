package br.com.hbsis.crud.linhaProduto;


import br.com.hbsis.crud.categoriaProduto.Categoria;

import javax.persistence.*;

@Entity
@Table(name = "linha_produto")
public class Linha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linha")
    private int idLinha;
    @Column(name = "nome_linha")
    private String nomeLinha;
    @ManyToOne
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;
    @Column(name = "codigo_linha")
    private String codigoLinha;

    public int getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(int idLinha) {
        this.idLinha = idLinha;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }
}
