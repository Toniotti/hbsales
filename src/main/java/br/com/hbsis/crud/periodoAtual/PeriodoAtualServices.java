package br.com.hbsis.crud.periodoAtual;

import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.fornecedor.FornecedorService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoAtualServices {

    private final PeriodoAtualRepository periodoAtualRepository;
    private final FornecedorService fornecedorService;

    public PeriodoAtualServices(PeriodoAtualRepository periodoAtualRepository, FornecedorService fornecedorService) {
        this.periodoAtualRepository = periodoAtualRepository;
        this.fornecedorService = fornecedorService;
    }

    //salvar
    public PeriodoAtual save(PeriodoAtualDTO periodoAtualDTO) {
        //verificar se o periodo a ser cadastrado se encaixa em algum periodo ja cadastrado
        List<PeriodoAtual> periodoAtualList = this.periodoAtualRepository.findByFkFornecedor(this.fornecedorService.findEntityById(periodoAtualDTO.getIdFornecedor()));
        boolean dentroDoPeriodo = true;


        if(!periodoAtualList.isEmpty()){
            dentroDoPeriodo = false;
            for (int j = 0; j < periodoAtualList.size(); j++){
                Date dataIncio = periodoAtualList.get(j).getDataInicio();
                Date dataFinal = periodoAtualList.get(j).getDataFinal();
                //pegar data atual
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

                if(dataIncio.compareTo(periodoAtualDTO.getDataInicio()) < 0 && dataFinal.compareTo(periodoAtualDTO.getDataFinal()) > 0){
                    dentroDoPeriodo = true;
                    break;
                }
            }
        }

        PeriodoAtual periodoAtual = new PeriodoAtual();

        if(dentroDoPeriodo == true){
            periodoAtual.setDataInicio(periodoAtualDTO.getDataInicio());
            periodoAtual.setDataFinal(periodoAtualDTO.getDataFinal());
            periodoAtual.setFkFornecedor(this.fornecedorService.findEntityById(periodoAtualDTO.getIdFornecedor()));
            periodoAtual.setDataRetirada(periodoAtualDTO.getDataRetirada());
            periodoAtual.setQtdProdutoVendido(periodoAtualDTO.getQtdProdutoVendido());
            periodoAtual.setDescricao(periodoAtualDTO.getDescricao());

            periodoAtual = this.periodoAtualRepository.save(periodoAtual);
        }else{
            throw new IllegalArgumentException("O periodo informado não se encaixa dentro de nenhum outro periodo já cadastrado.");
        }

        return periodoAtual;
    }

    //achar por id

    public PeriodoAtualDTO findById(int id) {
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if (periodoAtualOptional.isPresent()) {
            return PeriodoAtualDTO.of(periodoAtualOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s nao existe", id));
    }

    //achar entidade por id

    public PeriodoAtual findEntityById(int id) {
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if (periodoAtualOptional.isPresent()) {
            return periodoAtualOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s nao existe", id));
    }

    //find periodo por entidade fornecedor
    public PeriodoAtual findByFornecedor(Fornecedor fornecedor) {
        return this.findEntityById(this.periodoAtualRepository.findPeriodoByFornecedor(fornecedor));
    }

    //listar por id

    public PeriodoAtualDTO listById(int id) {
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if (periodoAtualOptional.isPresent()) {
            return PeriodoAtualDTO.of(periodoAtualOptional.get());
        }
        throw new IllegalArgumentException(String.format("O id fornecido(%s) do periodo não existe.", id));
    }


    //alterar

    public PeriodoAtual update(PeriodoAtualDTO periodoAtualDTO, int id) {
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if (periodoAtualOptional.isPresent()) {
            PeriodoAtual periodoAtualExistente = periodoAtualOptional.get();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date(System.currentTimeMillis());

            if(periodoAtualExistente.getDataFinal().compareTo(now) < 0){
                System.out.println("eeeeeee");
                throw new IllegalArgumentException("A da informada já acabou.");
            }

            Fornecedor entityById = this.fornecedorService.findEntityById(periodoAtualDTO.getIdFornecedor());
            periodoAtualExistente.setFkFornecedor(entityById);
            periodoAtualExistente.setDataFinal(periodoAtualDTO.getDataFinal());
            periodoAtualExistente.setDataInicio(periodoAtualDTO.getDataInicio());
            periodoAtualExistente.setDataRetirada(periodoAtualDTO.getDataRetirada());
            periodoAtualExistente.setQtdProdutoVendido(periodoAtualDTO.getQtdProdutoVendido());
            periodoAtualExistente.setDescricao(periodoAtualDTO.getDescricao());

            periodoAtualExistente = this.periodoAtualRepository.save(periodoAtualExistente);

            return periodoAtualExistente;
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //deletar

    public String delete(int id) {
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(id);

        if (periodoAtualOptional.isPresent()) {
            this.periodoAtualRepository.deleteById(id);
            return String.format("Sucesso ao deletar o id %s.", id);
        }
        return String.format("ID %s não existe.", id);
    }

    //verificar se fornecedor está com periodo ativo
    public boolean verificarPeriodo(int id) {
        List<Fornecedor> fornecedorList = this.periodoAtualRepository.findFornecedorByIdFornecedor(this.fornecedorService.findEntityById(id));

        if (!fornecedorList.isEmpty()) {
            return true;
        }
        return false;
    }

    //find all by fornecedor
    public List<PeriodoAtual> findAllByFornecedor(int idFornecedor) {
        List<PeriodoAtual> periodoAtualList = this.periodoAtualRepository.findAll();
        List<PeriodoAtual> byFornecedor = new ArrayList<>();

        for (int i = 0; i < periodoAtualList.size(); i++) {
            if (periodoAtualList.get(i).getFkFornecedor().getIdFornecedor() == idFornecedor) {
                byFornecedor.add(periodoAtualList.get(i));
            }
        }

        return byFornecedor;
    }

    //verificar se o pedido atual existe
    public boolean periodoAtualExiste(PeriodoAtual periodoAtual) {
        Optional<PeriodoAtual> periodoAtualOptional = this.periodoAtualRepository.findById(periodoAtual.getId());

        if (periodoAtualOptional.isPresent()) {
            return true;
        }
        return false;
    }
}
