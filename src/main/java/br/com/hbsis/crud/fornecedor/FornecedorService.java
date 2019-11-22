package br.com.hbsis.crud.fornecedor;

import org.springframework.stereotype.Service;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        this.validate(fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setEmail(fornecedorDTO.getEmail());
        fornecedor.setNome_fantasia(fornecedorDTO.getNome_fantasia());
        fornecedor.setRazao_social(fornecedorDTO.getRazao_social());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());

        fornecedor = this.fornecedorRepository.save(fornecedor); //

        return FornecedorDTO.of(fornecedor);
    }

    private void validate(FornecedorDTO fornecedorDTO){
        if(fornecedorDTO == null){
            throw new IllegalArgumentException("FuncionarioDTO não deve ser nulo");
        }
        if(fornecedorDTO.getCnpj().equals("")){
            throw new IllegalArgumentException("CNPJ não deve ser vazio");
        }
        if(fornecedorDTO.getEmail().equals("")){
            throw new IllegalArgumentException("Email não deve ser vazio");
        }
        if(fornecedorDTO.getNome_fantasia().equals("")){
            throw new IllegalArgumentException("Nome fantasia não deve ser vazio");
        }
        if(fornecedorDTO.getRazao_social().equals("")){
            throw new IllegalArgumentException("Razao social não deve ser vazio");
        }
        if(fornecedorDTO.getTelefone().equals("")){
            throw new IllegalArgumentException("Telefone não deve ser vazio");
        }
    }
}
