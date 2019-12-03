package br.com.hbsis.crud.funcionario;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    //salvar funcionario

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO){
        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
        funcionario.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());

        funcionario = this.funcionarioRepository.save(funcionario);

        return FuncionarioDTO.of(funcionario);
    }

    //achar entidade

    public Funcionario findEntityById(int id){
        return this.funcionarioRepository.findById(id).get();
    }

    //listar funcionario

    public FuncionarioDTO findById(int id){
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if(funcionarioOptional.isPresent()){
            return FuncionarioDTO.of(funcionarioOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s do funcionario não existe", id));
    }

    //atualizar informações do funcionario

    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, int id){
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if(funcionarioOptional.isPresent()){
            Funcionario funcionarioExistente = funcionarioOptional.get();

            funcionarioExistente.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());
            funcionarioExistente.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());

            funcionarioExistente = this.funcionarioRepository.save(funcionarioExistente);

            return FuncionarioDTO.of(funcionarioExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s do funcionario não existe", id));
    }

    //deletar funcionario

    public String delete(int id){
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if(funcionarioOptional.isPresent()){
            this.funcionarioRepository.deleteById(id);
            return String.format("O funcionario informador(id %s) foi deletado com sucesso", id);
        }
        throw new IllegalArgumentException(String.format("ID %s do funcionario não existe", id));
    }
}
