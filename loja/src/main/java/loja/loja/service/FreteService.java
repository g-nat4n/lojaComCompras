package loja.loja.service;

import loja.loja.dto.FreteResponse;
import org.apache.maven.lifecycle.internal.LifecycleStarter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FreteService {

    public List<FreteResponse> calcular(String cep, Double valorProduto){
        List<FreteResponse> opces = new ArrayList<>();


        String cepLimpo = cep.replaceAll("\\D", "");

        double baseFrete = cepLimpo.startsWith("8") ? 10.0 : 25.0;

        if (valorProduto > 200.0){
            opces.add(new FreteResponse("Econômico (Frete Grátis)", 0.0));

            opces.add(new FreteResponse("Expresso", baseFrete + 15.0));
        } else {
            opces.add(new FreteResponse("Padrão", baseFrete));
            opces.add(new FreteResponse("Sedex", baseFrete + 18.50));
        }

        return opces;
    }
}