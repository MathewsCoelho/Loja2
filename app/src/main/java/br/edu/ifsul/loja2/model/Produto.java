package br.edu.ifsul.loja2.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Produto implements Serializable{
    private Long codigoDeBarras;
    private String nome;
    private String descricao;
    private Double valor;
    private Integer quantidade;
    private boolean situacao;
    private String url_foto = "";
    private String key;
    private Integer index;

    public Produto() {
    }

    public Long getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Long codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Integer getIndex() {
        return index;
    }

    @Exclude
    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigoDeBarras=" + codigoDeBarras +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", situacao=" + situacao +
                ", url_foto='" + url_foto + '\'' +
                ", key='" + key + '\'' +
                ", index=" + index +
                '}';
    }
}
