package br.com.hbsis.crud.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
}
