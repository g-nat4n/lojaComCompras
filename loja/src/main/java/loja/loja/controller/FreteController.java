package loja.loja.controller;


import loja.loja.dto.FreteResponse;
import loja.loja.repository.ProdutoRepository;
import loja.loja.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frete")
@CrossOrigin("*")
public class FreteController {

    @Autowired
    private FreteService freteService;

    @Autowired
    private ProdutoRepository produtoRepository;


    @GetMapping("/calcular")
    public ResponseEntity<List<FreteResponse>> calcularFrete(
            @RequestParam String cep,
            @RequestParam Long produtoId){


        return produtoRepository.findById(produtoId)
                .map(produto -> {
                    List<FreteResponse> resultado = freteService.calcular(cep, produto.getValor());
                    return ResponseEntity.ok(resultado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
