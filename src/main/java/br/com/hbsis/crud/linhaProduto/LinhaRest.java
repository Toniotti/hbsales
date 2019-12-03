package br.com.hbsis.crud.linhaProduto;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/linhas")
public class LinhaRest {

    private final LinhaService linhaService;

    public LinhaRest(LinhaService linhaService){
        this.linhaService = linhaService;
    }

    //listar todas
    @GetMapping("/getAll")
    public List<Linha> listAll(){return this.linhaService.listAll();}

    //salvar nova linha

    @PostMapping
    public LinhaDTO save(@RequestBody LinhaDTO linhaDTO){
        return this.linhaService.save(linhaDTO);
    }

    //listar linha

    @GetMapping("/{id}")
    public LinhaDTO findById(@PathVariable("id") int id){
        return this.linhaService.findById(id);
    }

    //alterar linha

    @PutMapping("/{id}")
    public LinhaDTO update(@RequestBody LinhaDTO linhaDTO, @PathVariable("id") int id){
        return this.linhaService.update(linhaDTO, id);
    }

    //deletar

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        this.linhaService.delete(id);
    }

    //exportar csv
    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException {this.linhaService.exportarCsv(response);}


}
