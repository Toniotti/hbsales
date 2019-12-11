package br.com.hbsis.crud.linhaProduto;

import br.com.hbsis.crud.csv.ImportLinha;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/linhas")
public class LinhaRest {

    private final LinhaService linhaService;
    private final ImportLinha importLinha;

    public LinhaRest(LinhaService linhaService, ImportLinha importLinha){
        this.linhaService = linhaService;
        this.importLinha = importLinha;
    }

    //listar todas
    @GetMapping("/getAll")
    public List<Linha> listAll(){return this.linhaService.listAll();}

    //salvar nova linha

    @PostMapping
    public Linha save(@Valid @RequestBody LinhaDTO linhaDTO){
        return this.linhaService.save(linhaDTO);
    }

    //listar linha

    @GetMapping("/{id}")
    public LinhaDTO findById(@PathVariable("id") int id){
        return this.linhaService.findById(id);
    }

    //alterar linha

    @PutMapping("/{id}")
    public Linha update(@Valid @RequestBody LinhaDTO linhaDTO, @PathVariable("id") int id){
        return this.linhaService.update(linhaDTO, id);
    }

    //deletar

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        this.linhaService.delete(id);
    }

//    exportar csv
    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException {this.linhaService.exportarCsv(response);}

    //import
    @PostMapping("/import")
    public List<Linha> importLinha(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        return this.importLinha.importLinha(multipartFile);
    }

}
