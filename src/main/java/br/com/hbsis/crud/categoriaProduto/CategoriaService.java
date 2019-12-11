package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.csv.ExportCSV;
import br.com.hbsis.crud.csv.ImportCategoria;
import br.com.hbsis.crud.csv.SaveFile;
import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.fornecedor.FornecedorService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final FornecedorService fornecedorService;
    private final ExportCSV exportCSV;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, FornecedorService fornecedorService, ExportCSV exportCSV) {
        this.categoriaRepository = categoriaRepository;
        this.fornecedorService = fornecedorService;
        this.exportCSV = exportCSV;
    }

    //salvar categoria
    public Categoria save(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        Fornecedor entidadeFornecedor = this.fornecedorService.findEntityById(categoriaDTO.getFkFornecedor());
        categoria.setCodigoCategoria(this.gerarCodigoCategoria(categoriaDTO.getCodigoCategoria(), entidadeFornecedor));
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(entidadeFornecedor);

        try {
            categoria = this.categoriaRepository.save(categoria);
        }catch (ConstraintViolationException e){
            throw new IllegalArgumentException("O fornecedor informado já está vinculado a uma categoria, portanto informe outro codigo.");
        }

        return categoria;
    }

    //salvar entidade
    public Categoria save(Categoria categoria){
        return this.categoriaRepository.save(categoria);
    }

    //criar codigo da categoria
    public String gerarCodigoCategoria(String codigoInformado, Fornecedor fornecedorCategoria) {
        String cnpjFornecedor = fornecedorCategoria.getCnpj().substring(10, 14);

        while (codigoInformado.length() < 3) {
            codigoInformado = "0" + codigoInformado;
        }

        return "CAT" + cnpjFornecedor + codigoInformado;
    }

    //importar
    public Categoria importCategoria(String[] array){
        Categoria categoria = new Categoria();

        Fornecedor entidadeFornecedor = this.fornecedorService.fornecedorByCnpj(array[2]);
        categoria.setFornecedor(entidadeFornecedor);
        categoria.setCodigoCategoria(this.gerarCodigoCategoria(array[3], entidadeFornecedor));
        categoria.setNomeCategoria(array[1]);

        categoria = this.categoriaRepository.save(categoria);

        return categoria;
    }

    //exportar categorias
    public void exportarCategorias(HttpServletResponse response) throws IOException {
        List<Categoria> categoriaList = new ArrayList<>();

        categoriaList = this.categoriaRepository.findAll();

        this.exportCSV.exportCategoria(response, categoriaList);
    }

    //verificar se exite com codigo cat
    public boolean verificarExisteCode(String codigo){
        Optional<Categoria> categoriaOptional = Optional.ofNullable(this.categoriaRepository.findByCodigoCategoria(codigo));

        if(categoriaOptional.isPresent()){
            return true;
        }
        return false;
    }

    //encontrar dto por id
    public Categoria findById(int id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    //encontar entidade por id
    public Categoria findEntityById(int id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    //find all
    public List<CategoriaDTO> findAll() {
        List<Categoria> categoriaList = this.categoriaRepository.findAll();

        List<CategoriaDTO> categoriaDTOS = new ArrayList<>();

        categoriaList.forEach(categoria -> categoriaDTOS.add(CategoriaDTO.of(categoria)));

        return categoriaDTOS;
    }

    //atualizar informações
    public Categoria update(CategoriaDTO categoriaDTO, int id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);


        if (categoriaOptional.isPresent()) {
            Categoria categoriaExistente = categoriaOptional.get();
            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());

            Fornecedor fornecedor = this.fornecedorService.findEntityById(categoriaDTO.getFkFornecedor());

            String newCode = this.gerarCodigoCategoria(categoriaDTO.getCodigoCategoria(), fornecedor);
            categoriaExistente.setCodigoCategoria(newCode);

            categoriaExistente.setFornecedor(fornecedor);

            categoriaExistente = this.categoriaRepository.save(categoriaExistente);

            return categoriaExistente;
        }
        throw new IllegalArgumentException(String.format("Essa categoria não existe (id informado: %s)", id));
    }

    //deletar
    public String delete(int id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if(categoriaOptional.isPresent()){
            this.categoriaRepository.deleteById(id);
            return "Categoria deletada com sucesso";
        }
        throw new IllegalArgumentException(String.format("A categoria informada não existe. (id informado: %s)", id));
    }

    //verificar se a categoria existe
    public boolean categoriaExiste(int id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return true;
        }
        return false;
    }

    //pegar entidade categoria pelo codigo

    public Categoria entidadeCategoriaByCodigo(String codigo){
        return this.categoriaRepository.findByCodigoCategoria(codigo);
    }

}
