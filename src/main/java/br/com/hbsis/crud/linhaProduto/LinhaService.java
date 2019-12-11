package br.com.hbsis.crud.linhaProduto;

import br.com.hbsis.crud.categoriaProduto.Categoria;
import br.com.hbsis.crud.categoriaProduto.CategoriaService;
import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.csv.SaveFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaService {
    private final LinhaRepository linhaRepository;
    private final CategoriaService categoriaService;
    private final ExportCSV exportCSV;

    @Autowired
    public LinhaService(LinhaRepository linhaRepository, CategoriaService categoriaService, ExportCSV exportCSV) {
        this.linhaRepository = linhaRepository;
        this.categoriaService = categoriaService;
        this.exportCSV = exportCSV;
    }

    //salvar

    public Linha save(LinhaDTO linhaDTO) {
        Linha linha = new Linha();

        String codigo = this.codigoLinha(linhaDTO.getCodigoLinha());
        if (codigo.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            linha.setCategoria(this.categoriaService.findEntityById(linhaDTO.getFkCategoria()));
            linha.setNomeLinha(linhaDTO.getNomeLinha());
            linha.setCodigoLinha(codigo);

            linha = this.linhaRepository.save(linha);

            return linha;
        }
        throw new IllegalArgumentException(String.format("O codigo informado (%s) não contem letras e números.", codigo));
    }

    //salvar utilizando uma entidade linha

    public Linha save(Linha linha){
        return this.linhaRepository.save(linha);
    }

    //verificar codigo da linha e se preciso, reformula-lo

    public String codigoLinha(String codigo){
        while(codigo.length() < 10){
            codigo = "0"+codigo;
        }
        return codigo.toUpperCase();
    }

    //listar

    public LinhaDTO findById(int id) {
        Optional<Linha> linhaOptional = this.linhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return LinhaDTO.of(linhaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //listar todas as linhas
    public List<Linha> listAll() {
        return this.linhaRepository.findAll();
    }

    //pegar entidade linha

    public Linha findEntityById(int id) {
        Optional<Linha> linhaOptional = this.linhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return linhaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //alterar

    public Linha update(LinhaDTO linhaDTO, int id) {
        Optional<Linha> linhaOptional = this.linhaRepository.findById(id);

        String codigo = this.codigoLinha(linhaDTO.getCodigoLinha());

        if(linhaOptional.isPresent() && codigo.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            Linha linhaExistente = linhaOptional.get();

            linhaExistente.setCodigoLinha(codigo);
            linhaExistente.setNomeLinha(linhaDTO.getNomeLinha());
            linhaExistente.setCategoria(this.categoriaService.findEntityById(linhaDTO.getFkCategoria()));

            linhaExistente = this.linhaRepository.save(linhaExistente);

            return linhaExistente;
        }
        throw new IllegalArgumentException(String.format("A linha informada (id: %s) não existe.", id));
    }

    //deletar

    public void delete(int id) {
        this.linhaRepository.deleteById(id);
    }

    //exportar csv
    public void exportarCsv(HttpServletResponse response) throws IOException {

        this.exportCSV.exportLinha(response, this.linhaRepository.findAll());
    }

    //verificar se existe
    public boolean linhaExiste(int id) {
        Optional<Linha> linhaOptional = this.linhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return true;
        }
        return false;
    }

    //verificar se a linha existe pelo codigo
    public boolean linhaExiste(String codigo){

        Optional<Linha> linhaOptional = Optional.ofNullable(this.linhaRepository.findByCodigoLinha(codigo));

        if(linhaOptional.isPresent()){
            return true;
        }
        return false;
    }

    //encontrar entidade pelo codigo
    public Linha findLinhaByCodigo(String codigo){
        return this.linhaRepository.findByCodigoLinha(codigo);
    }
}
