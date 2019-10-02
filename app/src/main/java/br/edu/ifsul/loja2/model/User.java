package br.edu.ifsul.loja2.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

public class User {

    private Long codigoDeBarras;
    private String nome;
    private String sobrenome;
    private String funcao;
    private String key;
    private Integer index;
    private String email;
    private FirebaseUser firebaseUser;

    public User() {
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    @Exclude
    public Integer getIndex(){
        return index;
    }

    @Exclude
    public void setIndex(Integer index){
        this.index = index;
    }

    @Exclude
    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    @Exclude
    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "codigoDeBarras=" + codigoDeBarras +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", funcao='" + funcao + '\'' +
                ", email='" + email + '\'' +
                ", firebaseUser=" + firebaseUser +
                '}';
    }
}
