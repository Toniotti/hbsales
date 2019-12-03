package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.categoriaFornecedor.CategoriaFornecedor;
import br.com.hbsis.crud.categoriaFornecedor.CategoriaFornecedorDTO;
import br.com.hbsis.crud.categoriaFornecedor.CategoriaFornecedorService;
import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.csv.ImportCategoria;
import br.com.hbsis.crud.csv.SaveFile;
import br.com.hbsis.crud.fornecedor.FornecedorService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
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
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ImportCategoria importCategoria;
    private final CategoriaFornecedorService categoriaFornecedorService;
    private final FornecedorService fornecedorService;
    private final ExportCSV exportCSV;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, ImportCategoria importCategoria, CategoriaFornecedorService categoriaFornecedorService, FornecedorService fornecedorService, ExportCSV exportCSV) {
        this.categoriaRepository = categoriaRepository;
        this.importCategoria = importCategoria;
        this.categoriaFornecedorService = categoriaFornecedorService;
        this.fornecedorService = fornecedorService;
        this.exportCSV = exportCSV;
    }

    //salvar categoria
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());

        categoria = this.categoriaRepository.save(categoria);

        //criar relacao categoria fornecedor
        List<Integer> idFornecedores = categoriaDTO.getFkFornecedor();

        for (int i = 0; i < idFornecedores.size(); i++) {
            CategoriaFornecedorDTO categoriaFornecedorDTO = new CategoriaFornecedorDTO(
                    idFornecedores.get(i),
                    categoria.getIdCategoria()
                    );

            CategoriaFornecedor categoriaFornecedor = new CategoriaFornecedor();
            categoriaFornecedor.setFkFornecedor(this.fornecedorService.findEntityById(categoriaFornecedorDTO.getFkFornecedor()));
            categoriaFornecedor.setFkCategoria(categoria);

            this.categoriaFornecedorService.save(categoriaFornecedor);
        }

        return CategoriaDTO.of(categoria);
    }

    //importar
    public List<CategoriaDTO> importCSV(MultipartFile multipartFile) throws IOException {
        SaveFile saveFile = new SaveFile();
        File file = saveFile.save(multipartFile);
        List<CategoriaDTO> categoriaList = this.importCategoria.importCategoria(file);

        categoriaList.forEach(categoriaDTO -> this.save(categoriaDTO));

        return categoriaList;
    }

    //exportar
    public void exportCSV(HttpServletResponse response) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        this.exportCSV.exportCategoria(response, this.findAll());
    }

    //encontrar dto por id
    public CategoriaDTO findById(int id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    //encontar entidade por id
    public Categoria findEntityById(int id){
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if(categoriaOptional.isPresent()){
            return categoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //find all

    public List<CategoriaDTO> findAll(){
        List<Categoria> categoriaList = this.categoriaRepository.findAll();

        List<CategoriaDTO> categoriaDTOS = new ArrayList<>();

        categoriaList.forEach(categoria -> categoriaDTOS.add(CategoriaDTO.of(categoria)));

        return  categoriaDTOS;
    }

    //atualizar informações
    public CategoriaDTO update(CategoriaDTO categoriaDTO, int id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            Categoria categoriaExistente = categoriaOptional.get();
            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());

            categoriaExistente = this.categoriaRepository.save(categoriaExistente);

            return CategoriaDTO.of(categoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //deletar
    public void delete(int id) {
        this.categoriaRepository.deleteById(id);
    }

    //verificar se a categoria existe
    public boolean categoriaExiste(int id){
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if(categoriaOptional.isPresent()){
            return true;
        }
        return false;
    }
}
