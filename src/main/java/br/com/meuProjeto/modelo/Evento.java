package main.java.br.com.meuProjeto.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private List<Palestra> palestras;

    public Evento(String nome, List<Palestra> palestras) {
        this.nome = nome;
        this.palestras = palestras;
    }

    public String getNome() {
        return nome;
    }

    public List<Palestra> getPalestras() {
        return palestras;
    }

    public boolean adicionarPalestra(Palestra novaPalestra) {
        if (!verificarConflito(novaPalestra)) {
            palestras.add(novaPalestra);
            return true;
        }
        return false;
    }

    public boolean alterarPalestra(Palestra palestraExistente, LocalDateTime novoDiaHora, String novoLocal) {
        palestras.remove(palestraExistente);
        Palestra palestraAtualizada = new Palestra(palestraExistente.getTitulo(), palestraExistente.getPalestrante(), novoDiaHora, novoLocal, palestraExistente.getMaxOuvintes());
        if (!verificarConflito(palestraAtualizada)) {
            palestras.add(palestraAtualizada);
            return true;
        } else {
            palestras.add(palestraExistente);
            return false;
        }
    }

    private boolean verificarConflito(Palestra novaPalestra) {
        for (Palestra p : palestras) {
            if (p.getLocal().equals(novaPalestra.getLocal()) && p.getDiaHora().isEqual(novaPalestra.getDiaHora())) {
                return true;
            }
        }
        return false;
    }
}
