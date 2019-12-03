package br.com.hbsis.crud.categoriaProduto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
}
