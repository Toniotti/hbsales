package br.com.hbsis.crud.csv;

import br.com.hbsis.crud.categoriaProduto.Categoria;
import br.com.hbsis.crud.fornecedor.MascaraCNPJ;
import br.com.hbsis.crud.linhaProduto.Linha;
import br.com.hbsis.crud.produto.Produto;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class ExportCSV {

    //exportar categoria
    public void exportCategoria(HttpServletResponse response, List<Categoria> categoriaList) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "filename=linha.csv");

        CSVWriter csvWriter = new CSVWriter(response.getWriter(),
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] header = new String[]{"ID Categoria", "Nome Categoria", "CNPJ Fornecedor", "Razão Social Fornecedor", "Codigo Categoria"};//setar o cabeçalho

        csvWriter.writeNext(header);//printa a primeira linha, o cabeçalho

        for (int i = 0; i < categoriaList.size(); i++) {
            //montar header
            //criar variveis com as informações
            String idCategoria = Integer.toString(categoriaList.get(i).getIdCategoria());
            String nomeCategoria = categoriaList.get(i).getNomeCategoria();
            String cnpjFornecedor = categoriaList.get(i).getFornecedor().getCnpj();
            cnpjFornecedor = MascaraCNPJ.mascarar(cnpjFornecedor);
            String codigoCategoria = categoriaList.get(i).getCodigoCategoria();
            String razaoSocial = categoriaList.get(i).getFornecedor().getRazaoSocial();

            header = new String[]{idCategoria, nomeCategoria, cnpjFornecedor, razaoSocial, codigoCategoria};//setar o header utilizando as variveis

            csvWriter.writeNext(header);//escreve o header na proxima linha
        }

    }

    //linha de categoria

    public void exportLinha(HttpServletResponse response, List<Linha> linhaList) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "filename=linha.csv");

        CSVWriter csvWriter = new CSVWriter(response.getWriter(),
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] headers = new String[]{"Código Linha", "Nome Linha", "Código Categoria", "Nome Categoria"};
        csvWriter.writeNext(headers);

        for (int i = 0; i < linhaList.size(); i++) {
            String codigoLinha = linhaList.get(i).getCodigoLinha();
            String nomeLinha = linhaList.get(i).getNomeLinha();
            String codigoCategoria = linhaList.get(i).getCategoria().getCodigoCategoria();
            String nomeCategoria = linhaList.get(i).getCategoria().getNomeCategoria();


            headers = new String[]{codigoLinha, nomeLinha, codigoCategoria, nomeCategoria};
            csvWriter.writeNext(headers);
        }

        response.getWriter().close();
    }

    //produtos

    public void exportProdutos(HttpServletResponse response, List<Produto> produtoList) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "filename=produtos.csv");

        CSVWriter csvWriter = new CSVWriter(response.getWriter(),
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] headers = new String[]{"Código Produto", "Nome Produto", "Preço Produto", "Qtd Unidade Caixa", "Peso Unidade", "Validade Produto", "Código Linha", "Nome Linha",
                "Código Categoria", "Nome Categoria", "CNPJ Fornecedor", "Razão Social Fornecedor"};
        csvWriter.writeNext(headers);

        for (int i = 0; i < produtoList.size(); i++) {
            Produto produto = produtoList.get(i);
            String codigoProduto = produto.getCodigoProduto();
            String nomeProduto = produto.getNomeProduto();
            String precoProduto = "R$" + Double.toString(produto.getPrecoProduto());
            String qtdUnidade = Integer.toString(produto.getUnidadeCaixa());
            String pesoUnidade = Double.toString(produto.getPesoQtd()) + produto.getMedidaPeso().toUpperCase();
            String validade = produto.getValidade().toString();
            String codigoLinha = produto.getLinha().getCodigoLinha();
            String nomeLinha = produto.getLinha().getNomeLinha();
            String codigoCategoria = produto.getLinha().getCategoria().getCodigoCategoria();
            String nomeCategoria = produto.getLinha().getCategoria().getNomeCategoria();
            String cnpj = produto.getLinha().getCategoria().getFornecedor().getCnpj();
            String rzs = produto.getLinha().getCategoria().getFornecedor().getRazaoSocial();

            headers = new String[]{codigoProduto, nomeProduto, precoProduto, qtdUnidade, pesoUnidade, validade, codigoLinha, nomeLinha, codigoCategoria, nomeCategoria, cnpj, rzs};

            csvWriter.writeNext(headers);
        }

        response.getWriter().close();
    }


}
