package br.com.hbsis.crud.funcionario;

public class FuncionarioDTO {
    private String nomeFuncionario;
    private String emailFuncionario;

    public FuncionarioDTO(){

    }

    public FuncionarioDTO(String nomeFuncionario, String emailFuncionario){
        this.nomeFuncionario = nomeFuncionario;
        this.emailFuncionario = emailFuncionario;
    }

    public static FuncionarioDTO of(Funcionario funcionario){
        return new FuncionarioDTO(
                funcionario.getNomeFuncionario(),
                funcionario.getEmailFuncionario()
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
