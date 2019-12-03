package br.com.hbsis.crud.periodoAtual;

import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.fornecedor.FornecedorService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoAtualServices {

    private final PeriodoAtualRepository periodoAtualRepository;
    private final FornecedorService fornecedorService;
    private final ExportCSV exportCSV;

    public PeriodoAtualServices(PeriodoAtualRepository periodoAtualRepository, FornecedorService fornecedorService, ExportCSV exportCSV){
        this.periodoAtualRepository = periodoAtualRepository;
        this.fornecedorService = fornecedorService;
        this.exportCSV = exportCSV;
    }

    //salvar
    public PeriodoAtualDTO save(PeriodoAtualDTO periodoAtualDTO){
        PeriodoAtual periodoAtual = new PeriodoAtual();
        periodoAtual.setDataInicio(periodoAtualDTO.getDataInicio());
        periodoAtual.setDataFinal(periodoAtualDTO.getDataFinal());
        periodoAtual.setFkFornecedor(this.fornecedorService.findEntityById(periodoAtualDTO.getIdFornecedor()));
        periodoAtual.setDataRetirada(periodoAtualDTO.getDataRetirada());
        periodoAtual.setQtdProdutoVendido(periodoAtualDTO.getQtdProdutoVendido());

        periodoAtual = this.periodoAtualRepository.save(periodoAtual);

        return periodoAtualDTO.of(periodoAtual);
    }

    //achar por id

    public PeriodoAtualDTO findById(int id){
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if(periodoAtualOptional.isPresent()){
            return PeriodoAtualDTO.of(periodoAtualOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s nao existe", id));
    }

    //achar entidade por id

    public PeriodoAtual findEntityById(int id){
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if(periodoAtualOptional.isPresent()){
            return periodoAtualOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s nao existe", id));
    }

    //find periodo por entidade fornecedor
    public PeriodoAtual findByFornecedor(Fornecedor fornecedor){
        return this.findEntityById(this.periodoAtualRepository.findPeriodoByFornecedor(fornecedor));
    }

    //listar por id

    public PeriodoAtualDTO listById(int id){
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if(periodoAtualOptional.isPresent()){
            return PeriodoAtualDTO.of(periodoAtualOptional.get());
        }
        throw new IllegalArgumentException(String.format("O id fornecido(%s) do periodo não existe.", id));
    }


    //alterar

    public PeriodoAtualDTO update(PeriodoAtualDTO periodoAtualDTO, int id){
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if(periodoAtualOptional.isPresent()){
            PeriodoAtual periodoAtualExistente = periodoAtualOptional.get();
            Fornecedor entityById = this.fornecedorService.findEntityById(periodoAtualDTO.getIdFornecedor());
            periodoAtualExistente.setFkFornecedor(entityById);
            periodoAtualExistente.setDataFinal(periodoAtualDTO.getDataFinal());
            periodoAtualExistente.setDataInicio(periodoAtualDTO.getDataInicio());
            periodoAtualExistente.setDataRetirada(periodoAtualDTO.getDataRetirada());
            periodoAtualExistente.setQtdProdutoVendido(periodoAtualDTO.getQtdProdutoVendido());

            periodoAtualExistente = this.periodoAtualRepository.save(periodoAtualExistente);

            return PeriodoAtualDTO.of(periodoAtualExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //deletar

    public String delete(int id){
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if(periodoAtualOptional.isPresent()){
            this.periodoAtualRepository.deleteById(id);
            return String.format("Sucesso ao deletar o id %s.", id);
        }
        return String.format("ID %s não existe.", id);
    }

    //verificar se fornecedor está com periodo ativo
    public boolean verificarPeriodo(int id){
        List<Fornecedor> fornecedorList = this.periodoAtualRepository.findFornecedorByIdFornecedor(this.fornecedorService.findEntityById(id));

        if(!fornecedorList.isEmpty()){
            return true;
        }
        return false;
    }

    public void exportPeriodoFornecedor(HttpServletResponse response, int idFornecedor) throws IOException {
        Fornecedor fornecedor = this.fornecedorService.findEntityById(idFornecedor);
        this.exportCSV.exportPeriodoFornecedor(response, this.findAllByFornecedor(fornecedor.getIdFornecedor()));
    }

    //find all by fornecedor
    public List<PeriodoAtual> findAllByFornecedor(int idFornecedor){
        List<PeriodoAtual> periodoAtualList = this.periodoAtualRepository.findAll();
        List<PeriodoAtual> byFornecedor = new ArrayList<>();

        for (int i = 0; i < periodoAtualList.size(); i++){
            if(periodoAtualList.get(i).getFkFornecedor().getIdFornecedor() == idFornecedor){
                byFornecedor.add(periodoAtualList.get(i));
            }
        }

        return byFornecedor;
    }

    //verificar se o pedido atual existe
    public boolean periodoAtualExiste(PeriodoAtual periodoAtual){
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(periodoAtual.getId());

        if(periodoAtualOptional.isPresent()){
            return true;
        }
        return false;
    }
}
