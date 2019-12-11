package br.com.hbsis.crud.fornecedor;

import javax.validation.constraints.*;

public class FornecedorDTO {
    @NotBlank(message = "A razão social não pode ser vazia.")
    @Size(max = 100, message = "A razao social pode conter no maximo 100 caracteres.")
    private String razaoSocial;
    @Pattern(regexp = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}", message = "Este não é o padrão de CNPJ correto")
    @NotBlank(message = "O CNPJ não pode estar vazio.")
    private String cnpj;
    @Size(max = 100, message = "O nome fantasia pode conter no maximo 100 caracteres.")
    @NotBlank(message = "O nome fantasia não pode ser vazio.")
    private String nomeFantasia;
    @NotBlank(message = "O telefone não pode ser vazio.")
    @Pattern(regexp = "[0-9]{14}", message = "O padrão do telefone está errado.")
    private String telefone;
    @NotBlank(message = "O email não pode ser vazio.")
    @Email(message = "O email deve ser valido.")
    private String email;
    @NotBlank(message = "O endereço não pode ser vazio.")
    @Size(max = 100)
    private String endereco;

    public void FornecedorDTO(){

    }

    public FornecedorDTO(String razaoSocial, String cnpj, String nomeFantasia, String telefone, String email) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.telefone = telefone;
        this.email = email;
    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getRazaoSocial(),
                fornecedor.getCnpj(),
                fornecedor.getNomeFantasia(),
                fornecedor.getTelefone(),
                fornecedor.getEmail());
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
