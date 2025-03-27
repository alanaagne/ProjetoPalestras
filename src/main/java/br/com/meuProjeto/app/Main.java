package main.java.br.com.meuProjeto.app;

import main.java.br.com.meuProjeto.modelo.*;
import main.java.br.com.meuProjeto.persistencia.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Evento> eventos = inicializarEventos();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Escolha um evento:");
            for (int i = 0; i < eventos.size(); i++) {
                System.out.println((i + 1) + ". " + eventos.get(i).getNome());
            }
            System.out.println((eventos.size() + 1) + ". Mostrar todas as palestras");
            System.out.println((eventos.size() + 2) + ". Sair");

            int escolhaEvento = obterEscolha(scanner, eventos.size() + 2);
            
            if (escolhaEvento == eventos.size() + 2) {
                break;
            } else if (escolhaEvento == eventos.size() + 1) {
                listarTodasPalestras(eventos);
                continue;
            }

            Evento eventoEscolhido = eventos.get(escolhaEvento - 1);
            System.out.println("Evento escolhido: " + eventoEscolhido.getNome());

            System.out.println("1. Cadastrar uma nova palestra");
            System.out.println("2. Inscrever-se em uma palestra existente");
            System.out.println("3. Alterar uma palestra existente");

            int escolhaAcao = obterEscolha(scanner, 3);
            
            if (escolhaAcao == 1) {
                cadastrarNovaPalestra(scanner, eventoEscolhido);
            } else if (escolhaAcao == 2) {
                inscreverEmPalestra(scanner, eventoEscolhido);
            } else if (escolhaAcao == 3) {
                alterarPalestra(scanner, eventoEscolhido);
            }

            EventoDAO.salvarEventos(eventos);
        }

        listarPalestrasPorDiaEHorario(eventos);
    }

    private static int obterEscolha(Scanner scanner, int maxOpcoes) {
        int escolha = -1;
        while (escolha < 1 || escolha > maxOpcoes) {
            System.out.print("Digite sua escolha: ");
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
                if (escolha < 1 || escolha > maxOpcoes) {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
                scanner.next(); // clear the invalid input
            }
        }
        scanner.nextLine(); // clear the newline character after the integer input
        return escolha;
    }

    private static void cadastrarNovaPalestra(Scanner scanner, Evento eventoEscolhido) {
        System.out.print("Digite o título da palestra: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite o nome do palestrante: ");
        String palestrante = scanner.nextLine();

        LocalDateTime diaHora = null;
        boolean dataValida = false;
        while (!dataValida) {
            try {
                System.out.print("Digite o dia e hora (YYYY-MM-DDTHH:MM): ");
                String input = scanner.nextLine();
                diaHora = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                dataValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data e hora inválido. Tente novamente.");
            }
        }

        System.out.print("Digite o local (sala): ");
        String local = scanner.nextLine();
        System.out.print("Digite a quantidade máxima de ouvintes: ");
        int maxOuvintes = scanner.nextInt();
        scanner.nextLine();

        Palestra novaPalestra = new Palestra(titulo, palestrante, diaHora, local, maxOuvintes);

        if (eventoEscolhido.adicionarPalestra(novaPalestra)) {
            System.out.println("Palestra adicionada ao evento " + eventoEscolhido.getNome() + " com sucesso!");
        } else {
            System.out.println("Erro: Conflito de agenda. Palestra não adicionada.");
        }
    }

    private static void inscreverEmPalestra(Scanner scanner, Evento eventoEscolhido) {
        System.out.println("Escolha uma palestra para se inscrever:");
        List<Palestra> palestras = eventoEscolhido.getPalestras();
        for (int i = 0; i < palestras.size(); i++) {
            System.out.println((i + 1) + ". " + palestras.get(i).getTitulo() + " - " + palestras.get(i).getPalestrante());
        }

        int escolhaPalestra = obterEscolha(scanner, palestras.size());

        Palestra palestraEscolhida = palestras.get(escolhaPalestra - 1);

        System.out.print("Digite seu nome: ");
        String nomeOuvinte = scanner.nextLine();
        System.out.print("Digite seu email: ");
        String emailOuvinte = scanner.nextLine();

        Ouvinte ouvinte = new Ouvinte(nomeOuvinte, emailOuvinte);

        if (palestraEscolhida.adicionarOuvinte(ouvinte)) {
            System.out.println(ouvinte.getNome() + " inscrito na palestra " + palestraEscolhida.getTitulo());
        } else {
            System.out.println("Capacidade máxima atingida para a palestra " + palestraEscolhida.getTitulo());
        }
    }

    private static void alterarPalestra(Scanner scanner, Evento eventoEscolhido) {
        System.out.println("Escolha uma palestra para alterar:");
        List<Palestra> palestras = eventoEscolhido.getPalestras();
        for (int i = 0; i < palestras.size(); i++) {
            System.out.println((i + 1) + ". " + palestras.get(i).getTitulo() + " - " + palestras.get(i).getPalestrante());
        }

        int escolhaPalestra = obterEscolha(scanner, palestras.size());

        Palestra palestraEscolhida = palestras.get(escolhaPalestra - 1);

        LocalDateTime novoDiaHora = null;
        boolean dataValida = false;
        while (!dataValida) {
            try {
                System.out.print("Digite o novo dia e hora (YYYY-MM-DDTHH:MM): ");
                String input = scanner.nextLine();
                novoDiaHora = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                dataValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data e hora inválido. Tente novamente.");
            }
        }

        System.out.print("Digite o novo local (sala): ");
        String novoLocal = scanner.nextLine();

        if (eventoEscolhido.alterarPalestra(palestraEscolhida, novoDiaHora, novoLocal)) {
            System.out.println("Palestra alterada com sucesso!");
        } else {
            System.out.println("Erro: Conflito de agenda. Palestra não alterada.");
        }
    }

    private static List<Evento> inicializarEventos() {
        List<Evento> eventos = EventoDAO.carregarEventos();

        adicionarEventoSeNaoExistir(eventos, "Imersão Java");
        adicionarEventoSeNaoExistir(eventos, "Semana Tecnologia");
        adicionarEventoSeNaoExistir(eventos, "Semana da Diversidade");

        return eventos;
    }

    private static void adicionarEventoSeNaoExistir(List<Evento> eventos, String nomeEvento) {
        for (Evento evento : eventos) {
            if (evento.getNome().equals(nomeEvento)) {
                return;
            }
        }
        eventos.add(new Evento(nomeEvento, new ArrayList<>()));
    }


    private static void inscreverOuvinte(Palestra palestra, Ouvinte ouvinte) {
        if (palestra.adicionarOuvinte(ouvinte)) {
            System.out.println(ouvinte.getNome() + " inscrito na palestra " + palestra.getTitulo());
        } else {
            System.out.println("Capacidade máxima atingida para a palestra " + palestra.getTitulo());
        }
    }

    private static void listarTodasPalestras(List<Evento> eventos) {
        System.out.println("Todas as Palestras Cadastradas:");
        eventos.forEach(evento -> {
            evento.getPalestras().forEach(palestra -> {
                System.out.println(palestra.getTitulo() + " - " + palestra.getPalestrante() + " - " + palestra.getDiaHora() + " - " + palestra.getLocal());
            });
        });
    }
    private static void listarPalestrasPorDiaEHorario(List<Evento> eventos) {
        List<Palestra> todasPalestras = new ArrayList<>();
        eventos.forEach(evento -> todasPalestras.addAll(evento.getPalestras()));

        todasPalestras.sort(Comparator.comparing(Palestra::getDiaHora));

        System.out.println("Palestras por Dia e Horário:");
        todasPalestras.forEach(palestra -> {
            System.out.println(palestra.getDiaHora() + " - " + palestra.getTitulo() + " - " + palestra.getPalestrante() + " - " + palestra.getLocal());
        });
    }

}


