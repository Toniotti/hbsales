package br.com.hbsis.crud.produto;

import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.csv.ImportProduto;
import br.com.hbsis.crud.csv.ImportProdutoFornecedor;
import br.com.hbsis.crud.linhaProduto.Linha;
import br.com.hbsis.crud.linhaProduto.LinhaService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final LinhaService linhaService;
    private final ExportCSV exportCSV;

    public ProdutoService(ProdutoRepository produtoRepository, LinhaService linhaService, ExportCSV exportCSV) {
        this.produtoRepository = produtoRepository;
        this.linhaService = linhaService;
        this.exportCSV = exportCSV;
    }

    //salvar produto

    public Produto save(ProdutoDTO produtoDTO) {

        if (produtoDTO.getMedidaPeso().toLowerCase().equals("g") || produtoDTO.getMedidaPeso().toLowerCase().equals("kg")
                || produtoDTO.getMedidaPeso().toLowerCase().equals("mg")) {
            if(this.verificarFloat(produtoDTO.getPrecoProduto(), produtoDTO.getPesoQtd())){
                    Produto produto = new Produto();
                    produto.setNomeProduto(produtoDTO.getNomeProduto());
                    produto.setPrecoProduto(produtoDTO.getPrecoProduto());
                    produto.setUnidadeCaixa(produtoDTO.getUnidadeCaixa());
                    produto.setPesoQtd(produtoDTO.getPesoQtd());
                    produto.setValidade(produtoDTO.getValidade());
                    produto.setCodigoProduto(this.codigoProdto(produtoDTO.getCodigoProduto()));
                    produto.setMedidaPeso(produtoDTO.getMedidaPeso());
                    Linha entityById = this.linhaService.findEntityById(produtoDTO.getIdLinha());
                    produto.setLinha(entityById);

                    produto = this.produtoRepository.save(produto);

                    return produto;
            }
        }
        throw new IllegalArgumentException(String.format("A medida de peso informada(%s) não atende os padrões requisitados.", produtoDTO.getMedidaPeso()));
    }

    //salvar entidade do import
    public void save(Produto produto){
        this.produtoRepository.save(produto);
    }
    //verificar/gerar código do produto.
    public String codigoProdto(String codigo) {
        while (codigo.length() < 10) {
            codigo = "0" + codigo;
        }

        return codigo.toUpperCase();
    }

    //verificar float
    public boolean verificarFloat(Double preco, Double peso){
        String pontoPreco = Double.toString(preco).substring(Double.toString(preco).indexOf(".")+1, Double.toString(preco).length());
        String pontoPeso = Double.toString(peso).substring(Double.toString(peso).indexOf(".")+1, Double.toString(peso).length());
        System.out.println(pontoPeso+"\n"+pontoPeso.length());
        if(pontoPeso.length() >3 && pontoPreco.length() > 2){
            throw new IllegalArgumentException(String.format("O peso(%s) e o preço(%s) informados, possuem muitos decimais.", peso, preco));
        }
        if(pontoPeso.length() >3){
            throw new IllegalArgumentException(String.format("O peso informado(%s) possue mais de 3 casas decimais.", peso));
        }
        if (pontoPreco.length() > 2){
            throw new IllegalArgumentException(String.format("O preço informado(%s) possue mais de 2 casas decimais.", preco));
        }

        return true;
    }

    //listar todos

    public List<ProdutoDTO> findAll() {
        List<Produto> produtoList = this.produtoRepository.findAll();

        List<ProdutoDTO> produtoDTOS = new ArrayList<>();

        produtoList.forEach(produto -> produtoDTOS.add(ProdutoDTO.of(produto)));

        return produtoDTOS;
    }

    //listar produto
    public Produto findByID(int id) {
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
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

    public Produto update(ProdutoDTO produtoDTO, int id) {
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            Produto produtoExistente = produtoOptional.get();

            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
            produtoExistente.setPrecoProduto(produtoDTO.getPrecoProduto());
            produtoExistente.setUnidadeCaixa(produtoDTO.getUnidadeCaixa());
            produtoExistente.setPesoQtd(produtoDTO.getPesoQtd());
            produtoExistente.setValidade(produtoDTO.getValidade());
            produtoExistente.setMedidaPeso(produtoDTO.getMedidaPeso());
            produtoExistente.setCodigoProduto(this.codigoProdto(produtoDTO.getCodigoProduto()));
            Linha entityById = this.linhaService.findEntityById(produtoDTO.getIdLinha());
            produtoExistente.setLinha(entityById);

            produtoExistente = this.produtoRepository.save(produtoExistente);

            return produtoExistente;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //deletar produto
    public void delete(int id) {
        this.produtoRepository.deleteById(id);
    }

    //exportar
    public void exportarCsv(HttpServletResponse response) throws IOException {

        this.exportCSV.exportProdutos(response, this.produtoRepository.findAll());
    }

    //importar
//    public void importarCsv(MultipartFile multipartFile) throws IOException, ParseException {
//        SaveFile saveFile = new SaveFile();
//        File file = saveFile.save(multipartFile);
//
//        List<ProdutoDTO> produtoList = this.importProduto.importProduto(file);
//
//
//        for (int i = 0; i < produtoList.size(); i++) {
//            Produto produto = new Produto();
//            Linha entidadeLinha = this.linhaService.findEntityById(produtoList.get(i).getIdLinha());
//            produto.setLinha(entidadeLinha);
//            produto.setValidade(produtoList.get(i).getValidade());
//            produto.setPesoQtd(produtoList.get(i).getPesoQtd());
//            produto.setUnidadeCaixa(produtoList.get(i).getUnidadeCaixa());
//            produto.setPrecoProduto(produtoList.get(i).getPrecoProduto());
//            produto.setNomeProduto(produtoList.get(i).getNomeProduto());
//
//            produto = this.produtoRepository.save(produto);
//        }
//    }

    //importar produto por fornecedeor
//    public void importarProdutoFornecedor(MultipartFile multipartFile, int idFornecedor) throws IOException, ParseException {
//        SaveFile saveFile = new SaveFile();
//        File file = saveFile.save(multipartFile);
//
//        List<ProdutoDTO> produtoDTOS = this.importProdutoFornecedor.importarProdutoFornecedor(file, idFornecedor);
//
//        for (int i = 0; i < produtoDTOS.size(); i++) {
//            Optional<Produto> produtoOptional = this.produtoRepository.findById(produtoDTOS.get(i).getIdProduto());
//
//            Linha entidadeLinha = this.linhaService.findEntityById(produtoDTOS.get(i).getIdLinha());
//
//            if (produtoOptional.isPresent()) {
//                Produto produtoExistente = produtoOptional.get();
//
//                produtoExistente.setLinha(entidadeLinha);
//                produtoExistente.setValidade(produtoDTOS.get(i).getValidade());
//                produtoExistente.setPesoQtd(produtoDTOS.get(i).getPesoQtd());
//                produtoExistente.setUnidadeCaixa(produtoDTOS.get(i).getUnidadeCaixa());
//                produtoExistente.setPrecoProduto(produtoDTOS.get(i).getPrecoProduto());
//                produtoExistente.setNomeProduto(produtoDTOS.get(i).getNomeProduto());
//
//                produtoExistente = this.produtoRepository.save(produtoExistente);
//            }else{
//                Produto produto = new Produto();
//                produto.setNomeProduto(produtoDTOS.get(i).getNomeProduto());
//                produto.setPrecoProduto(produtoDTOS.get(i).getPrecoProduto());
//                produto.setUnidadeCaixa(produtoDTOS.get(i).getUnidadeCaixa());
//                produto.setPesoQtd(produtoDTOS.get(i).getPesoQtd());
//                produto.setValidade(produtoDTOS.get(i).getValidade());
//                produto.setLinha(entidadeLinha);
//
//                produto = this.produtoRepository.save(produto);
//            }
//
//        }
//    }

    //verificar se o produto existe
    public boolean produtoExiste(int id) {
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return true;
        }
        return false;
    }

    //pegar produto pelo codigo
    public Produto produtoByCodigo(String codigoProuto){
        Optional<Produto> produtoOptional = Optional.ofNullable(this.produtoRepository.findByCodigoProduto(codigoProuto));

        if(produtoOptional.isPresent()){
            return produtoOptional.get();
        }
        return null;
    }
}
