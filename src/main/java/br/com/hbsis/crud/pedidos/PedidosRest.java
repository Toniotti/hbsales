package br.com.hbsis.crud.pedidos;

import br.com.hbsis.crud.produtoPedido.ProdutoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidosRest {

    private PedidosService pedidosService;

    @Autowired
    public PedidosRest(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
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


}
