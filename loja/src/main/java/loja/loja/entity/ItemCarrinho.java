package loja.loja.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ItemCarrinho {
    @Id
    @GeneratedValue
    private Long id;

    private Integer quantidade;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;


    public ItemCarrinho() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }
}
