package loja.loja.controller;

import loja.loja.entity.Pedido;
import loja.loja.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    public ResponseEntity<String> pagar(@RequestBody Pedido pedido) throws Exception {
        String link = service.criarPagamento(pedido);
        return ResponseEntity.ok(link);
    }


    @GetMapping("/teste")
    public String teste() {
        return "API funcionando";
    }

}
