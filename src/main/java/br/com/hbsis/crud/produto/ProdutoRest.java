package br.com.hbsis.crud.produto;

import br.com.hbsis.crud.categoriaProduto.CategoriaDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoRest {

    private final ProdutoService produtoService;

    public ProdutoRest(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    //salvar produto
    @PostMapping("/{idFornecedor}")
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO, @PathVariable("idFornecedor") int idFornecedor) {
        return this.produtoService.save(produtoDTO, idFornecedor);
    }

    //listar produto
    @GetMapping("/{id}")
    public ProdutoDTO findById(@PathVariable int id) {
        return this.produtoService.findByID(id);
    }

    //alterar
    @PutMapping("/{id}")
    public ProdutoDTO update(@RequestBody ProdutoDTO produtoDTO, @PathVariable int id){
        return this.produtoService.update(produtoDTO, id);
    }

    //deletar
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        this.produtoService.delete(id);
    }

    //exportar
    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException {this.produtoService.exportarCsv(response);}

    //importar produtos

    @PostMapping("/importar")
    public void importCsv(@RequestParam("file") MultipartFile multipartFile) throws IOException, ParseException {this.produtoService.importarCsv(multipartFile);}

    //importar por fornecedor
    @PostMapping("/importFornecedor/{id}")
    public void importProdutoFornecedor(@RequestParam("file") MultipartFile multipartFile, @PathVariable("id") int idFornecedor) throws IOException, ParseException {
        this.produtoService.importarProdutoFornecedor(multipartFile, idFornecedor);
    }
}
