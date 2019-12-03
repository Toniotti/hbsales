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

    public String getNomeLinha() {
        return nomeLinha;
    }

    public int getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(int idLinha) {
        this.idLinha = idLinha;
    }

    public void setNomeLinha(String nomeProduto) {
        this.nomeLinha = nomeProduto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
