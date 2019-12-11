package br.com.hbsis.crud.csv;

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
public class ImportProduto {
    private final ProdutoService produtoService;
    private final LinhaService linhaService;

    public ImportProduto(ProdutoService produtoService, LinhaService linhaService) {
        this.produtoService = produtoService;
        this.linhaService = linhaService;
    }

    public void importar(MultipartFile multipartFile) throws IOException, ParseException {
        File file = SaveFile.save(multipartFile);
        List<String[]> infos = CSVReader.read(file);

        for (int i = 0; i < infos.size(); i++) {
            if (this.produtoService.produtoByCodigo(infos.get(i)[0]) == null) { // verifica se o produto existe, caso retorne null ele n existe
                if (this.linhaService.linhaExiste(infos.get(i)[6])) {//verificar se a linha de categoria informada existe
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
                }
            }
        }
    }

}
