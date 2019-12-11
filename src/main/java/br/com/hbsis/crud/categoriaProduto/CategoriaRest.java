package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.csv.ImportCategoria;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaRest {
    private final CategoriaService categoriaService;
    private final ImportCategoria importCategoria;

    public CategoriaRest(CategoriaService categoriaService, ImportCategoria importCategoria){this.categoriaService = categoriaService;
        this.importCategoria = importCategoria;
    }

    @PostMapping
    public Categoria save(@Valid @RequestBody CategoriaDTO categoriaDTO){
        return this.categoriaService.save(categoriaDTO);
    }

    //importar csv
    @PostMapping("/csv/import")
    public List<Categoria> importCSV(@RequestParam("file") MultipartFile file) throws IOException {
        return this.importCategoria.importCategoria(file);
    }


    @GetMapping("/csv/export")
    public void exportCSV(HttpServletResponse response) throws IOException {
        this.categoriaService.exportarCategorias(response);
    }

    @GetMapping("/listar/{id}")
    public Categoria findById(@PathVariable("id") int id){
        return this.categoriaService.findById(id);
    }

    @PutMapping("/update/{id}")
    public Categoria update(@PathVariable("id") int id, @Valid @RequestBody CategoriaDTO categoriaDTO){
        return this.categoriaService.update(categoriaDTO, id);
    }

    @DeleteMapping("/deletar/{id}")
    public String delete(@PathVariable("id") int id){
        return this.categoriaService.delete(id);
    }

}
