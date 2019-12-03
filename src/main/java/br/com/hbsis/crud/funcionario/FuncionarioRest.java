package br.com.hbsis.crud.funcionario;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioRest {

    private FuncionarioService funcionarioService;

    public FuncionarioRest(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

    //salvar

    @PostMapping
    public FuncionarioDTO save(@RequestBody FuncionarioDTO funcionarioDTO){
        return this.funcionarioService.save(funcionarioDTO);
    }

    //listar

    @GetMapping("/{id}")
    public FuncionarioDTO listar(@PathVariable("id") int id){
        return this.funcionarioService.findById(id);
    }

    //atualizar

    @PutMapping("/{id}")
    public FuncionarioDTO update(@RequestBody FuncionarioDTO funcionarioDTO, @PathVariable("id") int id){
        return this.funcionarioService.update(funcionarioDTO, id);
    }

    //deletar

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        return this.funcionarioService.delete(id);
    }
}
