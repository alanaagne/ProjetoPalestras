package main.java.br.com.meuProjeto.persistencia;

import main.java.br.com.meuProjeto.modelo.Evento;
import main.java.br.com.meuProjeto.modelo.Palestra;
import main.java.br.com.meuProjeto.modelo.Ouvinte;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {
    private static final String FILE_NAME = "data/eventos.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static void salvarEventos(List<Evento> eventos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Evento evento : eventos) {
                writer.write(evento.getNome());
                writer.newLine();
                for (Palestra palestra : evento.getPalestras()) {
                    writer.write(palestra.getTitulo() + ";" +
                            palestra.getPalestrante() + ";" +
                            palestra.getDiaHora().format(formatter) + ";" +
                            palestra.getLocal() + ";" +
                            palestra.getMaxOuvintes() + ";" +
                            palestra.getInscritos());
                    writer.newLine();
                    for (Ouvinte ouvinte : palestra.getOuvintes()) {
                        writer.write(ouvinte.getNome() + ";" + ouvinte.getEmail());
                        writer.newLine();
                    }
                    writer.write("---");
                    writer.newLine();
                }
                writer.write("###");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            Evento evento = null;
            List<Palestra> palestras = null;
            Palestra palestra = null;
            while ((linha = reader.readLine()) != null) {
                if (linha.equals("---")) {
                    palestra = null;
                } else if (linha.equals("###")) {
                    if (evento != null && palestras != null) {
                        eventos.add(evento);
                    }
                    evento = null;
                    palestras = null;
                } else if (palestras == null) {
                    evento = new Evento(linha, new ArrayList<>());
                    palestras = evento.getPalestras();
                } else if (palestra == null) {
                    String[] dados = linha.split(";");
                    if (dados.length >= 6) {
                        palestra = new Palestra(dados[0],
                                dados[1],
                                LocalDateTime.parse(dados[2], formatter),
                                dados[3],
                                Integer.parseInt(dados[4]));
                        palestras.add(palestra);
                    }
                } else {
                    String[] dados = linha.split(";");
                    if (dados.length == 2) {
                        Ouvinte ouvinte = new Ouvinte(dados[0], dados[1]);
                        palestra.adicionarOuvinte(ouvinte);
                    }
                }
            }

            if (evento != null && palestras != null) {
                eventos.add(evento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventos;
    }
}
