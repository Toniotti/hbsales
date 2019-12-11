package br.com.hbsis.crud.csv;

import br.com.hbsis.crud.categoriaProduto.Categoria;
import br.com.hbsis.crud.categoriaProduto.CategoriaService;
import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.fornecedor.FornecedorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImportCategoria {

    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;

    public ImportCategoria(CategoriaService categoriaService, FornecedorService fornecedorService) {
        this.categoriaService = categoriaService;
        this.fornecedorService = fornecedorService;
    }

    //importar
    public List<Categoria> importCategoria(MultipartFile multipartFile) throws IOException {

        File file = SaveFile.save(multipartFile);

        CSVReader csvReader = new CSVReader();
        List<String[]> infos = CSVReader.read(file);
        List<Categoria> categoriaList = new ArrayList<>();

        for (int i = 0; i < infos.size(); i++){
            String[] array = infos.get(i);

            if (StringUtils.isNumeric(array[0]) == false){
                Fornecedor entidadeFornecedor = this.fornecedorService.fornecedorByCnpj(array[2]);
                if(!this.categoriaService.verificarExisteCode(this.categoriaService.gerarCodigoCategoria(array[3], entidadeFornecedor))){
                    if (!(array[3].length() > 3)){
                        categoriaList.add(this.categoriaService.importCategoria(array));
                    }
                }
            }

        }
        return categoriaList;
    }

    //verificar se uma string é numerica
    public boolean isNumeric(String str){
        try {
            int n = Integer.parseInt(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
