package br.com.hbsis.crud.csv;

import br.com.hbsis.crud.categoriaProduto.Categoria;
import br.com.hbsis.crud.categoriaProduto.CategoriaDTO;
import br.com.hbsis.crud.categoriaProduto.CategoriaService;
import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.fornecedor.FornecedorService;
import br.com.hbsis.crud.linhaProduto.Linha;
import br.com.hbsis.crud.linhaProduto.LinhaService;
import br.com.hbsis.crud.produto.Produto;
import br.com.hbsis.crud.produto.ProdutoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ImportProdutoFornecedor {

    private final ProdutoService produtoService;
    private final LinhaService linhaService;
    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;

    public ImportProdutoFornecedor(ProdutoService produtoService, LinhaService linhaService, CategoriaService categoriaService, FornecedorService fornecedorService) {
        this.produtoService = produtoService;
        this.linhaService = linhaService;
        this.categoriaService = categoriaService;
        this.fornecedorService = fornecedorService;
    }

    public void importar(MultipartFile multipartFile, int idFornecedor) throws IOException, ParseException {
        File file = SaveFile.save(multipartFile);
        List<String[]> infos = CSVReader.read(file);

        for (int i = 0; i < infos.size(); i++) {
            Fornecedor fornecedor = this.fornecedorService.findEntityById(idFornecedor);
            Linha linha = new Linha();
            Categoria categoria = new Categoria();
            if(!this.categoriaService.verificarExisteCode(infos.get(i)[8])){
                //criar nova categoria
                categoria.setFornecedor(fornecedor);
                categoria.setCodigoCategoria(infos.get(i)[8]);
                categoria.setNomeCategoria(infos.get(i)[9]);

                categoria = this.categoriaService.save(categoria);

            }else{
                categoria = this.categoriaService.entidadeCategoriaByCodigo(infos.get(i)[8]);
            }
            if (!this.linhaService.linhaExiste(infos.get(i)[6])) {//verificar se a linha de categoria informada existe
                //cadastrar linha

                linha.setCategoria(categoria);
                linha.setNomeLinha(infos.get(i)[7]);
                linha.setCodigoLinha(infos.get(i)[6]);

                linha = this.linhaService.save(linha);

            }else{
                linha = this.linhaService.findLinhaByCodigo(infos.get(i)[6]);

                linha.setCategoria(categoria);
                linha.setNomeLinha(infos.get(i)[7]);
                linha.setCodigoLinha(infos.get(i)[6]);

                linha = this.linhaService.save(linha);
            }
            if (this.produtoService.produtoByCodigo(infos.get(i)[0]) == null) { // verifica se o produto existe, caso retorne null ele n existe
                Produto produto = new Produto();
                produto.setCodigoProduto(this.produtoService.codigoProdto(infos.get(i)[0]));
                produto.setMedidaPeso(infos.get(i)[4].replaceAll("[^A-Z]", ""));
                produto.setLinha(this.linhaService.findLinhaByCodigo(infos.get(i)[6]));

                //pegar date
                SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd");
                Date date = format.parse(infos.get(i)[5]);

                produto.setValidade(date);
                produto.setUnidadeCaixa(Integer.parseInt(infos.get(i)[3]));
                produto.setNomeProduto(infos.get(i)[1]);
                produto.setPesoQtd(Double.parseDouble(StringUtils.getDigits(infos.get(i)[4])));
                String precoString = infos.get(i)[2].substring(2, infos.get(i)[2].length());
                produto.setPrecoProduto(Double.parseDouble(precoString));

                this.produtoService.save(produto);
            }else if(this.produtoService.produtoExiste(this.produtoService.produtoByCodigo(infos.get(i)[0]).getIdProduto())){
                Produto produto = this.produtoService.produtoByCodigo(infos.get(i)[0]);
                produto.setCodigoProduto(this.produtoService.codigoProdto(infos.get(i)[0]));
                produto.setMedidaPeso(infos.get(i)[4].replaceAll("[^A-Z]", ""));
                produto.setLinha(this.linhaService.findLinhaByCodigo(infos.get(i)[6]));

                //pegar date
                SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd");
                Date date = format.parse(infos.get(i)[5]);

                produto.setValidade(date);
                produto.setUnidadeCaixa(Integer.parseInt(infos.get(i)[3]));
                produto.setNomeProduto(infos.get(i)[1]);
                produto.setPesoQtd(Double.parseDouble(StringUtils.getDigits(infos.get(i)[4])));
                String precoString = infos.get(i)[2].substring(2, infos.get(i)[2].length());
                produto.setPrecoProduto(Double.parseDouble(precoString));

                this.produtoService.save(produto);
            }
        }
    }

}
