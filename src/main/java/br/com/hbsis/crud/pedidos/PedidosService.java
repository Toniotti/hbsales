package br.com.hbsis.crud.pedidos;

import br.com.hbsis.crud.categoriaFornecedor.CategoriaFornecedor;
import br.com.hbsis.crud.fornecedor.Fornecedor;
import br.com.hbsis.crud.funcionario.FuncionarioService;
import br.com.hbsis.crud.periodoAtual.PeriodoAtual;
import br.com.hbsis.crud.periodoAtual.PeriodoAtualDTO;
import br.com.hbsis.crud.periodoAtual.PeriodoAtualServices;
import br.com.hbsis.crud.produto.ProdutoService;
import br.com.hbsis.crud.produtoPedido.ProdutoPedido;
import br.com.hbsis.crud.produtoPedido.ProdutoPedidoServices;
import br.com.hbsis.crud.relacaoProduto.RelacaoProduto;
import br.com.hbsis.crud.relacaoProduto.RelacaoProdutoService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

;

@Service
public class PedidosService {
    private final PedidosRepository pedidosRepository;
    private final FuncionarioService funcionarioService;
    private final ProdutoPedidoServices produtoPedidoServices;
    private final PeriodoAtualServices periodoAtualServices;
    private final RelacaoProdutoService relacaoProdutoService;
    private final ProdutoService produtoService;

    public PedidosService(PedidosRepository pedidosRepository, FuncionarioService funcionarioService, ProdutoPedidoServices produtoPedidoServices, PeriodoAtualServices periodoAtualServices, RelacaoProdutoService relacaoProdutoService, ProdutoService produtoService) {
        this.pedidosRepository = pedidosRepository;
        this.funcionarioService = funcionarioService;
        this.produtoPedidoServices = produtoPedidoServices;
        this.periodoAtualServices = periodoAtualServices;
        this.relacaoProdutoService = relacaoProdutoService;
        this.produtoService = produtoService;
    }

    //save
    public Pedido save(PedidosDTO pedidosDTO){
        Pedido pedido = new Pedido();
        pedido.setFkFuncionario(this.funcionarioService.findEntityById(pedidosDTO.getIdFuncionario()));




        for (int i = 0; i < pedidosDTO.getProdutos().size(); i++){

            CategoriaFornecedor categoriaFornecedor = this.relacaoProdutoService.findCategoriaFornecedorByProduto(this.produtoService.findEntityById(pedidosDTO.getProdutos().get(i)));

            Fornecedor fornecedor = categoriaFornecedor.getFkFornecedor();

            PeriodoAtual periodoAtual = this.periodoAtualServices.findByFornecedor(fornecedor);

            if(this.periodoAtualServices.periodoAtualExiste(periodoAtual)){
                Date agora = new Date();
                LocalDateTime ldt = LocalDateTime.ofInstant(agora.toInstant(), ZoneId.systemDefault());
                Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

                if(periodoAtual.getDataInicio().compareTo(agora) < 0 && periodoAtual.getDataFinal().compareTo(agora) > 0){
                    pedido = this.pedidosRepository.save(pedido);
                    PeriodoAtualDTO periodoAtualDTO = PeriodoAtualDTO.of(periodoAtual);
                    periodoAtualDTO.setQtdProdutoVendido(periodoAtualDTO.getQtdProdutoVendido()+pedidosDTO.getQtdComprada().get(i));
                    this.periodoAtualServices.update(periodoAtualDTO, periodoAtual.getId());

                    //salvar pedido produto

                    this.produtoPedidoServices.save(pedido, pedidosDTO.getProdutos().get(i));
                }
            }
        }
        return pedido;
    }

    //listar
    public Pedido findById(int id) {
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(id);

        if (pedidosOptional.isPresent()) {
            return pedidosOptional.get();
        }
        throw new IllegalArgumentException(String.format("O pedido fornecido(id %s) não existe", id));
    }

    //listar tudo

    public List<Pedido> findAll() {
        List<Pedido> pedidoOptional = this.pedidosRepository.findAll();

        return pedidoOptional;
    }

    //pegar entidade
    public Pedido findEntityById(int id) {
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(id);

        if (pedidosOptional.isPresent()) {
            return pedidosOptional.get();
        }
        throw new IllegalArgumentException(String.format("O pedido informado(id %s) não existe", id));
    }

    //deletar
    public String delete(int id) {
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(id);

        List<ProdutoPedido> produtoPedido = this.produtoPedidoServices.findProdutoPedidoByPedido(pedidosOptional.get());

        if (pedidosOptional.isPresent()) {
            produtoPedido.forEach(
                    produtoPedido1 -> {this.produtoPedidoServices.delete(produtoPedido1);}
            );
            this.pedidosRepository.deleteById(id);
            return String.format("Pedido de id %s deletado com sucesso", id);
        }
        throw new IllegalArgumentException(String.format("O pedido informado(id %s) não existe", id));
    }

    //listar por funcionario

    public List<List<ProdutoPedido>> listByFuncionario(int id) {
        List<Integer> idLista = this.pedidosRepository.findPedidoByIdFuncionario(this.funcionarioService.findEntityById(id));

        List<Pedido> pedidos = new ArrayList<>();

        idLista.forEach(integer -> pedidos.add(this.pedidosRepository.findById(integer).get()));

        return this.produtoPedidoServices.listByFuncionario(pedidos);
    }

    //atualizar informações
    public Pedido update(PedidosDTO pedidosDTO, int idPedido){
        Optional<Pedido> pedidosOptional = this.pedidosRepository.findById(idPedido);

        if(pedidosOptional.isPresent()){
            Pedido pedidoExistente = pedidosOptional.get();
            pedidoExistente.setFkFuncionario(this.funcionarioService.findEntityById(pedidosDTO.getIdFuncionario()));

            pedidoExistente = this.pedidosRepository.save(pedidoExistente);

            return pedidoExistente;
        }
        throw new IllegalArgumentException(String.format("ID(%s) do pedido não existe", idPedido));
    }

}
