package br.com.hbsis.crud.pedidos;

import br.com.hbsis.crud.csv.ExportProdutoFuncionarioFornecedor;
import br.com.hbsis.crud.produtoPedido.ExportProdutoPeriodo;
import br.com.hbsis.crud.produtoPedido.ProdutoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidosRest {

    private PedidosService pedidosService;
    private final ExportProdutoFuncionarioFornecedor exportProdutoFuncionarioFornecedor;
    private final ExportProdutoPeriodo exportProdutoPeriodo;

    @Autowired
    public PedidosRest(PedidosService pedidosService, ExportProdutoFuncionarioFornecedor exportProdutoFuncionarioFornecedor, ExportProdutoPeriodo exportProdutoPeriodo) {
        this.pedidosService = pedidosService;
        this.exportProdutoFuncionarioFornecedor = exportProdutoFuncionarioFornecedor;
        this.exportProdutoPeriodo = exportProdutoPeriodo;
    }

    //salvar
    @PostMapping
    public Pedido save(@RequestBody PedidosDTO pedidosDTO){
        return this.pedidosService.save(pedidosDTO);
    }

    //listar todos
    @GetMapping("/listAll")
    public List<Pedido> listAll(){
        return this.pedidosService.findAll();
    }

    //listar por funcionario
    @GetMapping("/funcionario/{id}")
    public List<List<ProdutoPedido>> listByFuncionario(@PathVariable("id") int id){
        return this.pedidosService.listByFuncionario(id);
    }

    //listar

    @GetMapping("/{id}")
    public Pedido findById(@PathVariable int id){
        return this.pedidosService.findById(id);
    }

    //atualizar
    @PutMapping("/{id}")
    //atualizar informações dos pedidos
    public Pedido update(@PathVariable("id") int id, @RequestBody PedidosDTO pedidosDTO){
        return this.pedidosService.update(pedidosDTO, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        return this.pedidosService.delete(id);
    }

    //retirar pedido
    @PutMapping("/status/{id}")
    public String retirarPedido(@PathVariable("id") int idPedido){
        return this.pedidosService.retirarPedido(idPedido);
    }

    //exportar produtos vendidos por funcionario por fornecedor
    @GetMapping("/exportarFuncionarioFornecedor/{idFuncionario}/{idFornecedor}")
    public void exportarProdutoFuncionarioFornecedor(@PathVariable("idFuncionario") int idFuncionario, @PathVariable("idFornecedor") int idFornecedor, HttpServletResponse response) throws IOException {
        this.exportProdutoFuncionarioFornecedor.exportar(idFornecedor, idFuncionario, response);
    }

    //exportar produtos vendidos por periodo por fornecedor
    @GetMapping("/periodo/{idPeriodo}")
    public void proutoPeriodo(@PathVariable("idPeriodo") int idPeriodo, HttpServletResponse response) throws IOException {
        this.exportProdutoPeriodo.exportPeriodoFornecedor(response, idPeriodo);
    }

}
