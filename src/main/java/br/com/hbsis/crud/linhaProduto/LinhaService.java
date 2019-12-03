package br.com.hbsis.crud.linhaProduto;

import br.com.hbsis.crud.categoriaProduto.Categoria;
import br.com.hbsis.crud.categoriaProduto.CategoriaService;
import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.csv.SaveFile;
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

    public LinhaDTO save(LinhaDTO linhaDTO) {
        Linha linha = new Linha();
        Categoria categoriaEntidade = categoriaService.findEntityById(linhaDTO.getCategoria());
        linha.setCategoria(categoriaEntidade);
        linha.setNomeLinha(linhaDTO.getNome());

        linha = this.linhaRepository.save(linha);

        return LinhaDTO.of(linha);
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
    public List<Linha> listAll(){
        return this.linhaRepository.findAll();
    }

    //pegar entidade linha

    public Linha findEntityById(int id){
        Optional<Linha> linhaOptional = this.linhaRepository.findById(id);

        if(linhaOptional.isPresent()){
            return linhaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //find all
    public List<LinhaDTO> findAll(){
        List<Linha> linhaList = this.linhaRepository.findAll();

        List<LinhaDTO> linhaDTOS = new ArrayList<>();

        linhaList.forEach(linha -> linhaDTOS.add(LinhaDTO.of(linha)));

        return  linhaDTOS;
    }
    //alterar

    public LinhaDTO update(LinhaDTO linhaDTO, int id) {
        Optional<Linha> linhaOptional = this.linhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            Linha linhaExistente = linhaOptional.get();

            linhaExistente.setNomeLinha(linhaDTO.getNome());
            Categoria categoriaEntidade = categoriaService.findEntityById(linhaDTO.getCategoria());
            linhaExistente.setCategoria(categoriaEntidade);

            linhaExistente = this.linhaRepository.save(linhaExistente);

            return LinhaDTO.of(linhaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //deletar

    public void delete(int id) {
        this.linhaRepository.deleteById(id);
    }

    //exportar csv
    public void exportarCsv(HttpServletResponse response) throws IOException {

        this.exportCSV.exportLinha(response, this.findAll());
    }

    //import
    public void importCsv(MultipartFile multipartFile) throws IOException {
        SaveFile saveFile = new SaveFile();
        File file = saveFile.save(multipartFile);
    }

    //verificar se existe
    public boolean linhaExiste(int id){
        Optional<Linha> linhaOptional = this.linhaRepository.findById(id);

        if(linhaOptional.isPresent()){
            return true;
        }
        return false;
    }
}
