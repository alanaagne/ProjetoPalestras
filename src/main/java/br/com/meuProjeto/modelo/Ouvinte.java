package main.java.br.com.meuProjeto.modelo;

import java.io.Serializable;

public class Ouvinte implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;

    public Ouvinte(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
