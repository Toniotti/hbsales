package br.com.hbsis.crud.produto;

import br.com.hbsis.crud.csv.ImportProduto;
import br.com.hbsis.crud.csv.ImportProdutoFornecedor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/produtos")
public class ProdutoRest {

    private final ProdutoService produtoService;
    private final ImportProduto importProduto;
    private final ImportProdutoFornecedor importProdutoFornecedor;

    public ProdutoRest(ProdutoService produtoService, ImportProduto importProduto, ImportProdutoFornecedor importProdutoFornecedor){
        this.produtoService = produtoService;
        this.importProduto = importProduto;
        this.importProdutoFornecedor = importProdutoFornecedor;
    }

    //salvar produto
    @PostMapping
    public Produto save(@Valid @RequestBody ProdutoDTO produtoDTO) {
        return this.produtoService.save(produtoDTO);
    }

    //listar produto
    @GetMapping("/{id}")
    public Produto findById(@PathVariable int id) {
        return this.produtoService.findByID(id);
    }

    //alterar
    @PutMapping("/{id}")
    public Produto update(@RequestBody ProdutoDTO produtoDTO, @PathVariable int id){
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
    public void importCsv(@RequestParam("file") MultipartFile multipartFile) throws IOException, ParseException {importProduto.importar(multipartFile);}

    //importar por fornecedor
    @PostMapping("/importFornecedor/{id}")
    public void importProdutoFornecedor(@RequestParam("file") MultipartFile multipartFile, @PathVariable("id") int idFornecedor) throws IOException, ParseException {
        this.importProdutoFornecedor.importar(multipartFile, idFornecedor);
    }
}
