package br.com.hbsis.crud.csv;

import br.com.hbsis.crud.categoriaProduto.CategoriaService;
import br.com.hbsis.crud.linhaProduto.Linha;
import br.com.hbsis.crud.linhaProduto.LinhaDTO;
import br.com.hbsis.crud.linhaProduto.LinhaService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImportLinha {

    private final CategoriaService categoriaService;
    private final LinhaService linhaService;

    public ImportLinha(CategoriaService categoriaService, LinhaService linhaService) {
        this.categoriaService = categoriaService;
        this.linhaService = linhaService;
    }

    public List<Linha> importLinha(MultipartFile multipartFile) throws IOException {
        CSVReader csvReader = new CSVReader();

        File file = SaveFile.save(multipartFile);

        List<String[]> infos = CSVReader.read(file);
        List<Linha> linhaList = new ArrayList<>();


        for (int i = 0; i < infos.size(); i++){
            String codigoLinha = this.linhaService.codigoLinha(infos.get(i)[0]);
            if (!(this.linhaService.linhaExiste(codigoLinha)) && this.categoriaService.verificarExisteCode(infos.get(i)[2])){
                Linha linha = new Linha();
                linha.setCodigoLinha(codigoLinha);
                linha.setNomeLinha(infos.get(i)[1]);
                linha.setCategoria(this.categoriaService.entidadeCategoriaByCodigo(infos.get(i)[2]));

                linha = this.linhaService.save(linha);

                linhaList.add(linha);

            }
        }

        return linhaList;
    }
}
