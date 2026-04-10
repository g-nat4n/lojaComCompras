package loja.loja.service;

import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import loja.loja.entity.Pedido;
import loja.loja.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {


    @Autowired
    private PedidoRepository repository;

    public String criarPagamento(Pedido pedido) throws Exception {

        pedido.setStatus("PENDENTE");
        repository.save(pedido);

        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title("Produto Teste")
                        .quantity(1)
                        .unitPrice(pedido.getValor())
                        .build();

        PreferenceRequest preferenceRequest =
                PreferenceRequest.builder()
                        .items(List.of(item))
                        .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        pedido.setPaymentId(preference.getId());
        repository.save(pedido);

        return preference.getInitPoint();
    }
}
