package br.com.hbsis.crud.fornecedor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    //cadastro
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setEmail(fornecedorDTO.getEmail());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());

        fornecedor = this.fornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    //listagem
    public FornecedorDTO findById(int id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor findEntityById(int id){
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if(fornecedorOptional.isPresent()){
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

            fornecedorExistente = this.fornecedorRepository.save(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //delete
    public void delete(int id){
        this.fornecedorRepository.deleteById(id);
    }

    //verificar se o fornecedor existe
    public boolean fornecedorExiste(int id){
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if(fornecedorOptional.isPresent()){
            return true;
        }
        return false;
    }
}
