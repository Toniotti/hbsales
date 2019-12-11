package br.com.hbsis.crud.pedidos;

import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.funcionario.Funcionario;
import br.com.hbsis.crud.periodoAtual.PeriodoAtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    @Query("select idPedido from Pedido where fkFuncionario = ?1")
    List<Integer> findPedidoByIdFuncionario(Funcionario funcionario);

    //achar pedidos por funcionario por fornecedor

    List<Pedido> findByFkFuncionario(Funcionario funcionario);

    //pedidos por periodo
    List<Pedido> findByFkPeriodoAtual(PeriodoAtual periodoAtual);
}
