package br.com.hbsis.crud.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
