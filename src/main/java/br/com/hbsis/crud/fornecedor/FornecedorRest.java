package br.com.hbsis.crud.fornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorRest {

    private final FornecedorService fornecedorService;

    @Autowired
    public FornecedorRest(FornecedorService fornecedorService){this.fornecedorService = fornecedorService;}

    @PostMapping
    public FornecedorDTO save(@RequestBody FornecedorDTO fornecedorDTO){
        return this.fornecedorService.save(fornecedorDTO);
    }

    @GetMapping("/{id}")
    public FornecedorDTO find(@PathVariable("id") int id){
        return this.fornecedorService.findById(id);
    }

    @PutMapping("/{id}")
    public FornecedorDTO update(@PathVariable("id") int id, @RequestBody FornecedorDTO fornecedorDTO){
        return this.fornecedorService.update(fornecedorDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        this.fornecedorService.delete(id);
    }

}
