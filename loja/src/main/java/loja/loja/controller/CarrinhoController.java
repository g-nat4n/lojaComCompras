package loja.loja.controller;

import loja.loja.dto.CarrinhoDTO;
import loja.loja.entity.Carrinho;
import loja.loja.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionar(@RequestBody CarrinhoDTO dto) {

        System.out.println("🔥🔥🔥 CHEGOU NO CONTROLLER");

        service.adicionarItem(dto.getProdutoId(), dto.getQuantidade());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        service.removerItem(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/limpar")
    public ResponseEntity<?> limpar() {
        service.limparCarrinho();
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public Carrinho get() {
        Carrinho carrinho = service.getCarrinhoUsuario();

        carrinho.getItens().forEach(i -> {
            i.getProduto().getProduto();
        });

        return carrinho;
    }


}