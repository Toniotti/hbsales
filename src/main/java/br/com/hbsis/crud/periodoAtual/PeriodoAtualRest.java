package br.com.hbsis.crud.periodoAtual;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perido-atual")
public class PeriodoAtualRest {

    private final PeriodoAtualServices periodoAtualServices;


    public PeriodoAtualRest(PeriodoAtualServices periodoAtualServices){
        this.periodoAtualServices = periodoAtualServices;
    }

    //salvar
    @PostMapping
    public PeriodoAtualDTO save(@RequestBody PeriodoAtualDTO periodoAtualDTO){
        return this.periodoAtualServices.save(periodoAtualDTO);
    }

    //listar por fornecedor
    @GetMapping("/{id}")
    public List<PeriodoAtual> listByFornecedores(@PathVariable("id") int id){
        return this.periodoAtualServices.findAllByFornecedor(id);
    }

    //listar por id

    @GetMapping("/listById/{id}")
    public PeriodoAtualDTO listById(@PathVariable("id") int id){
        return this.periodoAtualServices.listById(id);
    }

    //atualizar

    @PutMapping("/{id}")
    public PeriodoAtualDTO update(@RequestBody PeriodoAtualDTO periodoAtualDTO, @PathVariable int id){
        return this.periodoAtualServices.update(periodoAtualDTO, id);
    }

    //deletar por id

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        return this.periodoAtualServices.delete(id);
    }

    //exportar
    @GetMapping("/export/{id}")
    public void export(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        this.periodoAtualServices.exportPeriodoFornecedor(response, id);
    }
}
