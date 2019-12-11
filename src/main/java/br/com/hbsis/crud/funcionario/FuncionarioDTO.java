package br.com.hbsis.crud.funcionario;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FuncionarioDTO {
    @Size(max = 50, message = "O nome do funcionario pode ter somente 50 caracteres.")
    @NotNull(message = "O nome do funcionario não pode ser nulo.")
    private String nomeFuncionario;
    @Size(max = 50, message = "O email do funcionario pode ter somente 50 caracteres.")
    @NotNull(message = "O email do funcionario não pode ser nulo.")
    private String emailFuncionario;
    private String uuid;

    public FuncionarioDTO(){

    }

    public FuncionarioDTO(String nomeFuncionario, String emailFuncionario){
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
    }

    public FuncionarioDTO(String nomeFuncionario, String emailFuncionario, String uuid) {
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
        this.uuid = uuid;
    }

    public static FuncionarioDTO of(Funcionario funcionario){
        return new FuncionarioDTO(
                funcionario.getNomeFuncionario(),
                funcionario.getEmailFuncionario(),
                funcionario.getUuid()
        );
    }


    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }
}
