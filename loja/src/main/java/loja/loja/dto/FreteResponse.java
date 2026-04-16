package loja.loja.dto;

public class FreteResponse {
    private String servico;
    private Double valor;

    public FreteResponse(String servico, Double valor) {
        this.servico = servico;
        this.valor = valor;
    }


    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}