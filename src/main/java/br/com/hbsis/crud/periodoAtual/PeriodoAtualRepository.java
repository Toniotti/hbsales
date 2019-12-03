package br.com.hbsis.crud.periodoAtual;

import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
interface PeriodoAtualRepository extends JpaRepository<PeriodoAtual, Integer> {
    @Query(value = "select fkFornecedor from PeriodoAtual where fkFornecedor = ?1")
    List<Fornecedor> findFornecedorByIdFornecedor(Fornecedor fornecedor);

    @Query("select idPeriodoAtual from PeriodoAtual where fkFornecedor = ?1")
    int findPeriodoByFornecedor(Fornecedor fornecedor);
}
