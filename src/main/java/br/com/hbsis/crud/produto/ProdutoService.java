package br.com.hbsis.crud.produto;

import br.com.hbsis.crud.categoriaFornecedor.CategoriaFornecedor;
import br.com.hbsis.crud.categoriaFornecedor.CategoriaFornecedorService;
import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.csv.ImportProduto;
import br.com.hbsis.crud.csv.ImportProdutoFornecedor;
import br.com.hbsis.crud.csv.SaveFile;
import br.com.hbsis.crud.linhaProduto.Linha;
import br.com.hbsis.crud.linhaProduto.LinhaService;
import br.com.hbsis.crud.relacaoProduto.RelacaoProduto;
import br.com.hbsis.crud.relacaoProduto.RelacaoProdutoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final LinhaService linhaService;
    private final ImportProduto importProduto;
    private final ImportProdutoFornecedor importProdutoFornecedor;
    private final ExportCSV exportCSV;
    private final CategoriaFornecedorService categoriaFornecedorService;
    private final RelacaoProdutoService relacaoProdutoService;

    public ProdutoService(ProdutoRepository produtoRepository, LinhaService linhaService, ImportProduto importProduto, ImportProdutoFornecedor importarProdutoFornecedor,
                          ExportCSV exportCSV, CategoriaFornecedorService categoriaFornecedorService, RelacaoProdutoService relacaoProdutoService) {
        this.produtoRepository = produtoRepository;
        this.linhaService = linhaService;
        this.importProduto = importProduto;
        this.importProdutoFornecedor = importarProdutoFornecedor;
        this.exportCSV = exportCSV;
        this.categoriaFornecedorService = categoriaFornecedorService;
        this.relacaoProdutoService = relacaoProdutoService;
    }

    //salvar produto

    public ProdutoDTO save(ProdutoDTO produtoDTO, int idFornecedor) {

        Produto produto = new Produto();
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setUnidadeCaixa(produtoDTO.getUnidadeCaixa());
        produto.setPesoQtd(produtoDTO.getPesoQtd());
        produto.setValidade(produtoDTO.getValidade());
        Linha entityById = this.linhaService.findEntityById(produtoDTO.getIdLinha());
        produto.setLinha(entityById);

        produto = this.produtoRepository.save(produto);

        //achar categoria do fornecedor ligada ao fornecedor requisitado para o cadastro do produto
        CategoriaFornecedor categoriaFornecedor = this.categoriaFornecedorService.findCategoriaFornecedorByIdFornecedorIdCategoria(idFornecedor, produto.getLinha().getCategoria());

        RelacaoProduto relacaoProduto = new RelacaoProduto();
        relacaoProduto.setFkProduto(produto);
        relacaoProduto.setCategoriaFornecedor(categoriaFornecedor);

        this.relacaoProdutoService.save(relacaoProduto);

        return ProdutoDTO.of(produto);
    }

    //listar todos

    public List<ProdutoDTO> findAll() {
        List<Produto> produtoList = this.produtoRepository.findAll();

        List<ProdutoDTO> produtoDTOS = new ArrayList<>();

        produtoList.forEach(produto -> produtoDTOS.add(ProdutoDTO.of(produto)));

        return produtoDTOS;
    }

    //listar produto
    public ProdutoDTO findByID(int id) {
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não do produto existe", id));
    }

    //pegar entidade pelo id

    public Produto findEntityById(int id) {
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s do produto não existe", id));
    }

    //alterar produto

    public ProdutoDTO update(ProdutoDTO produtoDTO, int id) {
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            Produto produtoExistente = produtoOptional.get();

            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
            produtoExistente.setPrecoProduto(produtoDTO.getPrecoProduto());
            produtoExistente.setUnidadeCaixa(produtoDTO.getUnidadeCaixa());
            produtoExistente.setPesoQtd(produtoDTO.getPesoQtd());
            produtoExistente.setValidade(produtoDTO.getValidade());
            Linha entityById = this.linhaService.findEntityById(produtoDTO.getIdLinha());
            produtoExistente.setLinha(entityById);

            produtoExistente = this.produtoRepository.save(produtoExistente);

            return ProdutoDTO.of(produtoExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //deletar produto
    public void delete(int id) {
        this.produtoRepository.deleteById(id);
    }

    //exportar
    public void exportarCsv(HttpServletResponse response) throws IOException {

        this.exportCSV.exportProdutos(response, this.findAll());
    }

    //importar
    public void importarCsv(MultipartFile multipartFile) throws IOException, ParseException {
        SaveFile saveFile = new SaveFile();
        File file = saveFile.save(multipartFile);

        List<ProdutoDTO> produtoList = this.importProduto.importProduto(file);

        System.out.println(produtoList.size());

        for (int i = 0; i < produtoList.size(); i++) {
            Produto produto = new Produto();
            Linha entidadeLinha = this.linhaService.findEntityById(produtoList.get(i).getIdLinha());
            produto.setLinha(entidadeLinha);
            produto.setValidade(produtoList.get(i).getValidade());
            produto.setPesoQtd(produtoList.get(i).getPesoQtd());
            produto.setUnidadeCaixa(produtoList.get(i).getUnidadeCaixa());
            produto.setPrecoProduto(produtoList.get(i).getPrecoProduto());
            produto.setNomeProduto(produtoList.get(i).getNomeProduto());

            produto = this.produtoRepository.save(produto);
        }
    }

    //importar produto por fornecedeor
    public void importarProdutoFornecedor(MultipartFile multipartFile, int idFornecedor) throws IOException, ParseException {
        SaveFile saveFile = new SaveFile();
        File file = saveFile.save(multipartFile);

        List<ProdutoDTO> produtoDTOS = this.importProdutoFornecedor.importarProdutoFornecedor(file, idFornecedor);

        for (int i = 0; i < produtoDTOS.size(); i++) {
            Optional<Produto> produtoOptional = this.produtoRepository.findById(produtoDTOS.get(i).getIdProduto());

            if (produtoOptional.isPresent()) {
                Produto produtoExistente = produtoOptional.get();

                Linha entidadeLinha = this.linhaService.findEntityById(produtoDTOS.get(i).getIdLinha());
                produtoExistente.setLinha(entidadeLinha);
                produtoExistente.setValidade(produtoDTOS.get(i).getValidade());
                produtoExistente.setPesoQtd(produtoDTOS.get(i).getPesoQtd());
                produtoExistente.setUnidadeCaixa(produtoDTOS.get(i).getUnidadeCaixa());
                produtoExistente.setPrecoProduto(produtoDTOS.get(i).getPrecoProduto());
                produtoExistente.setNomeProduto(produtoDTOS.get(i).getNomeProduto());

                produtoExistente = this.produtoRepository.save(produtoExistente);
            }
        }
    }

    //verificar se o produto existe
    public boolean produtoExiste(int id) {
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return true;
        }
        return false;
    }
}
