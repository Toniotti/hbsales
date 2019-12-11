package br.com.hbsis.crud.funcionario;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    //salvar funcionario

    public Funcionario save(FuncionarioDTO funcionarioDTO) throws IOException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
        funcionario.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());
        funcionario.setUuid(this.hbemployee(funcionarioDTO.getNomeFuncionario()));

        funcionario = this.funcionarioRepository.save(funcionario);

        return funcionario;
    }

    //validar funcionar api HBEMPLOYEE
    public String hbemployee(String nomeFuncionario) throws IOException {
        String uuid = null;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "f59fe1c4-1b67-11ea-978f-2e728ce88125");

        HbemployeesParam hbemployeesParam = new HbemployeesParam();
        hbemployeesParam.setNome(nomeFuncionario);

        HttpEntity<HbemployeesParam> entity = new HttpEntity<HbemployeesParam>(hbemployeesParam, headers);

        ResponseEntity<HbemployeesParam> result = restTemplate.exchange("http://nt-04053:9999/api/employees", HttpMethod.POST, entity, HbemployeesParam.class);

        return result.getBody().getEmployeeUuid();
    }

    //achar entidade

    public Funcionario findEntityById(int id) {
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if(funcionarioOptional.isPresent()){
            return funcionarioOptional.get();
        }
        throw new IllegalArgumentException(String.format("O funcionario informado(%s) não existe.", id));
    }

    //listar funcionario

    public FuncionarioDTO findById(int id) {
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            return FuncionarioDTO.of(funcionarioOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s do funcionario não existe", id));
    }

    //atualizar informações do funcionario

    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, int id) {
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioOptional.get();

            funcionarioExistente.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());
            funcionarioExistente.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());

            funcionarioExistente = this.funcionarioRepository.save(funcionarioExistente);

            return FuncionarioDTO.of(funcionarioExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s do funcionario não existe", id));
    }

    //deletar funcionario

    public String delete(int id) {
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            this.funcionarioRepository.deleteById(id);
            return String.format("O funcionario informador(id %s) foi deletado com sucesso", id);
        }
        throw new IllegalArgumentException(String.format("ID %s do funcionario não existe", id));
    }
}
