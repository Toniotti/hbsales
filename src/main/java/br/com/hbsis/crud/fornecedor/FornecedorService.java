package br.com.hbsis.crud.fornecedor;

import br.com.hbsis.crud.categoriaProduto.AlterarCodigoComponent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;
    private final AlterarCodigoComponent alterarCodigoComponent;

    public FornecedorService(FornecedorRepository fornecedorRepository, AlterarCodigoComponent alterarCodigoComponent) {
        this.fornecedorRepository = fornecedorRepository;
        this.alterarCodigoComponent = alterarCodigoComponent;
    }

    //cadastro
    @Transactional
    public Fornecedor save(FornecedorDTO fornecedorDTO) {

        Optional<Fornecedor> fornecedorOptional = Optional.ofNullable(this.fornecedorRepository.findByCnpj(fornecedorDTO.getCnpj()));

        if(fornecedorOptional.isPresent()){
            throw new IllegalArgumentException("O CNPJ informado já foi cadastrado.");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setEmail(fornecedorDTO.getEmail());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());

        fornecedor = this.fornecedorRepository.save(fornecedor);


        return fornecedor;
    }

    //listagem
    public FornecedorDTO findById(int id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor findEntityById(int id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //update
    public FornecedorDTO update(FornecedorDTO fornecedorDTO, int id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorOptional.get();

            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setRazaoSocial(fornecedorDTO.getRazaoSocial());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());
            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());

            fornecedorExistente = this.fornecedorRepository.save(fornecedorExistente);

            this.alterarCodigoComponent.alterarCodigo(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //delete
    public void delete(int id) {
        this.fornecedorRepository.deleteById(id);
    }

    //find fornecedor by cnpj
    public Fornecedor fornecedorByCnpj(@Pattern(regexp = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}") String cnpj){
        return this.fornecedorRepository.findByCnpj(cnpj);
    }

    //verificar se o fornecedor existe
    public boolean fornecedorExiste(int id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return true;
        }
        return false;
    }
}
