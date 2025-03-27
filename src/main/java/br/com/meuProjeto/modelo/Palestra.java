package main.java.br.com.meuProjeto.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Palestra implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titulo;
    private String palestrante;
    private LocalDateTime diaHora;
    private String local;
    private int maxOuvintes;
    private int inscritos;
    private List<Ouvinte> ouvintes;

    public Palestra(String titulo, String palestrante, LocalDateTime diaHora, String local, int maxOuvintes) {
        this.titulo = titulo;
        this.palestrante = palestrante;
        this.diaHora = diaHora;
        this.local = local;
        this.maxOuvintes = maxOuvintes;
        this.inscritos = 0;
        this.ouvintes = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPalestrante() {
        return palestrante;
    }

    public LocalDateTime getDiaHora() {
        return diaHora;
    }

    public String getLocal() {
        return local;
    }

    public int getMaxOuvintes() {
        return maxOuvintes;
    }

    public int getInscritos() {
        return inscritos;
    }

    public List<Ouvinte> getOuvintes() {
        return ouvintes;
    }

    public boolean adicionarOuvinte(Ouvinte ouvinte) {
        if (inscritos < maxOuvintes) {
            ouvintes.add(ouvinte);
            inscritos++;
            return true;
        }
        return false;
    }
}
