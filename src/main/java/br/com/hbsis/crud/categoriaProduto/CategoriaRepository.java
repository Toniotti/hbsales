package br.com.hbsis.crud.categoriaProduto;

import br.com.hbsis.crud.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

    //achar categoria por codigo
    Categoria findByCodigoCategoria(String codigo);

    //achar por fornecedor
    List<Categoria> findByFornecedor(Fornecedor fornecedor);
}
