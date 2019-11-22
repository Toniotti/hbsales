package br.com.hbsis.crud.fornecedor;

import javax.persistence.*;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_social", unique = true, nullable = false, length = 80)
    private String razao_social;
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;
    @Column(name = "nome_fantasia", unique = false, nullable = false, length = 80)
    private String nome_fantasia;
    @Column(name = "telefone", unique = true, nullable = false, length = 14)
    private String telefone;
    @Column(name = "email", unique = false, nullable = false, length = 80)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razao_social;
    }

    public void setRazaoSocial(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nome_fantasia;
    }

    public void setNomeFantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
