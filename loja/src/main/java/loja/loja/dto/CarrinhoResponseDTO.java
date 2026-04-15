package loja.loja.dto;

import java.util.List;

public class CarrinhoResponseDTO {

    private Long id;
    private List<ItemDTO> itens;
    private double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}