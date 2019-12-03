package br.com.hbsis.crud.categoriaProduto;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaRest {
    private final CategoriaService categoriaService;

    public CategoriaRest(CategoriaService categoriaService){this.categoriaService = categoriaService;}

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO){
        return this.categoriaService.save(categoriaDTO);
    }

    //importar csv
    @PostMapping("/import")
    public List<CategoriaDTO> importCSV(@RequestParam("file") MultipartFile file) throws IOException {
        return this.categoriaService.importCSV(file);
    }

    //export

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        this.categoriaService.exportCSV(response);
    }

    @GetMapping("/{id}")
    public CategoriaDTO findById(@PathVariable("id") int id){
        return this.categoriaService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO update(@PathVariable("id") int id, @RequestBody CategoriaDTO categoriaDTO){
        return this.categoriaService.update(categoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        this.categoriaService.delete(id);
    }
}
