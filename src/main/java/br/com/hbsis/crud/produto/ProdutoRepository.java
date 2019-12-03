package br.com.hbsis.crud.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
