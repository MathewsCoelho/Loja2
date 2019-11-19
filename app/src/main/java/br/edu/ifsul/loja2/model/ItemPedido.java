package br.edu.ifsul.loja2.model;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    private Long id;
    private Integer quantidade;
    private Double totalItem;
    private Boolean situacao;
    private Produto produto; //associação entre as classes ItemPedido-Produto

    public ItemPedido() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto)  { this.produto = produto; }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "quantidade=" + quantidade +
                ", totalItem=" + totalItem +
                ", situacao=" + situacao +
                ", produto=" + produto +
                '}';
    }
}
