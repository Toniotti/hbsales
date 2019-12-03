package br.com.hbsis.crud.pedidos;

import br.com.hbsis.crud.funcionario.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    @Query("select idPedido from Pedido where fkFuncionario = ?1")
    List<Integer> findPedidoByIdFuncionario(Funcionario funcionario);
}
