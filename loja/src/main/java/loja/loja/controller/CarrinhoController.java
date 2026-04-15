package loja.loja.controller;

import loja.loja.dto.CarrinhoDTO;
import loja.loja.dto.CarrinhoResponseDTO;
import loja.loja.dto.ItemDTO;
import loja.loja.entity.Carrinho;
import loja.loja.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CarrinhoResponseDTO get() {

        Carrinho carrinho = service.getCarrinhoUsuario();

        CarrinhoResponseDTO dto = new CarrinhoResponseDTO();

        dto.setId(carrinho.getId());

        List<ItemDTO> itens = carrinho.getItens().stream().map(i -> {

            ItemDTO item = new ItemDTO();
            item.setProdutoId(i.getProduto().getId());
            item.setNome(i.getProduto().getProduto());
            item.setImagem(i.getProduto().getImagem());
            item.setValor(i.getProduto().getValor());
            item.setQuantidade(i.getQuantidade());

            return item;

        }).toList();

        dto.setItens(itens);

        double total = itens.stream()
                .mapToDouble(i -> i.getValor() * i.getQuantidade())
                .sum();

        dto.setTotal(total);

        return dto;
    }


}