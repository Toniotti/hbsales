package br.com.hbsis.crud.fornecedor;

import javax.persistence.*;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fornecedor")
    private int idFornecedor;
    @Column(name = "razao_social", unique = true, nullable = false, length = 80)
    private String razaoSocial;
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;
    @Column(name = "nome_fantasia", unique = false, nullable = false, length = 80)
    private String nomeFantasia;
    @Column(name = "telefone", unique = true, nullable = false, length = 14)
    private String telefone;
    @Column(name = "email", unique = false, nullable = false, length = 80)
    private String email;

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int id) {
        this.idFornecedor = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
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
