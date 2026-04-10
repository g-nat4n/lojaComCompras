package loja.loja.controller;

import loja.loja.entity.Pedido;
import loja.loja.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private PedidoRepository repository;

    @PostMapping
    public ResponseEntity<Void> receber(@RequestBody Map<String, Object> payload) {

        String paymentId = payload.get("data").toString();

        Optional<Pedido> pedidoOpt = repository.findByPaymentId(paymentId);

        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus("PAGO");
            repository.save(pedido);
        }

        return ResponseEntity.ok().build();
    }
}