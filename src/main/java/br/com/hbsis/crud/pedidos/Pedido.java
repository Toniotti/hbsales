package br.com.hbsis.crud.pedidos;


import br.com.hbsis.crud.funcionario.Funcionario;
import br.com.hbsis.crud.periodoAtual.PeriodoAtual;
import br.com.hbsis.crud.produtoPedido.ProdutoPedido;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int idPedido;
    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    private Funcionario fkFuncionario;
    @Column(name = "status")
    private int status;
    @Column(name = "data_criacao")
    private Date dataCriacao;
    @ManyToOne
    @JoinColumn(name = "fk_periodo", referencedColumnName = "id_periodo_atual")
    private PeriodoAtual fkPeriodoAtual;


    //setters and getters

    public PeriodoAtual getFkPeriodoAtual() {
        return fkPeriodoAtual;
    }

    public void setFkPeriodoAtual(PeriodoAtual fkPeriodoAtual) {
        this.fkPeriodoAtual = fkPeriodoAtual;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Funcionario getFkFuncionario() {
        return fkFuncionario;
    }

    public void setFkFuncionario(Funcionario fkFuncionario) {
        this.fkFuncionario = fkFuncionario;
    }
}
