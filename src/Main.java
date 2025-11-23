import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Tarefa> tarefas = new ArrayList<>();
    private static int tempoTotalDisponivel = 100; // Default value

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=========================================");
            System.out.println("   SISTEMA DE GERENCIAMENTO DE TAREFAS");
            System.out.println("=========================================");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Definir Tempo Total Disponível (Atual: " + tempoTotalDisponivel + ")");
            System.out.println("3. Executar Comparação (Guloso vs DP)");
            System.out.println("4. Listar Tarefas Atuais");
            System.out.println("5. Limpar Todas as Tarefas");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    adicionarTarefa(scanner);
                    break;
                case "2":
                    definirTempoTotal(scanner);
                    break;
                case "3":
                    executarComparacao();
                    break;
                case "4":
                    listarTarefas();
                    break;
                case "5":
                    tarefas.clear();
                    System.out.println("Todas as tarefas foram removidas.");
                    break;
                case "6":
                    running = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    private static void adicionarTarefa(Scanner scanner) {
        System.out.println("\n--- Nova Tarefa ---");
        System.out.print("ID da Tarefa: ");
        String id = scanner.nextLine();

        int tempo = lerInteiro(scanner, "Tempo de Execução: ");
        int prioridade = lerInteiro(scanner, "Prioridade (Valor): ");
        int prazo = lerInteiro(scanner, "Prazo (Deadline): ");

        Tarefa novaTarefa = new Tarefa(id, tempo, prioridade, prazo);
        tarefas.add(novaTarefa);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private static void definirTempoTotal(Scanner scanner) {
        tempoTotalDisponivel = lerInteiro(scanner, "Novo Tempo Total Disponível: ");
        System.out.println("Tempo total atualizado para: " + tempoTotalDisponivel);
    }

    private static void listarTarefas() {
        System.out.println("\n--- Lista de Tarefas ---");
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            for (Tarefa t : tarefas) {
                System.out.println(t);
            }
        }
    }

    private static void executarComparacao() {
        if (tarefas.isEmpty()) {
            System.out.println("Não há tarefas para escalonar. Adicione tarefas primeiro.");
            return;
        }

        System.out.println("\nExecutando comparação com Tempo Limite: " + tempoTotalDisponivel);

        EscalonadorGuloso guloso = new EscalonadorGuloso();
        EscalonadorDP dp = new EscalonadorDP();

        // Execução Guloso
        long startG = System.nanoTime();
        Resultado resG = guloso.escalonar(tarefas, tempoTotalDisponivel);
        long endG = System.nanoTime();
        double timeG = (endG - startG) / 1_000_000.0;

        // Execução DP
        long startDP = System.nanoTime();
        Resultado resDP = dp.escalonar(tarefas, tempoTotalDisponivel);
        long endDP = System.nanoTime();
        double timeDP = (endDP - startDP) / 1_000_000.0;

        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%-20s | Valor: %-5d | Tempo: %-5d | Exec: %.4f ms%n",
                "Guloso", resG.getValorTotal(), resG.getTempoUsado(), timeG);
        System.out.printf("%-20s | Valor: %-5d | Tempo: %-5d | Exec: %.4f ms%n",
                "Prog. Dinâmica", resDP.getValorTotal(), resDP.getTempoUsado(), timeDP);
        System.out.println("------------------------------------------------------------");

        System.out.println("\nDetalhes Guloso:");
        listarTarefasResultado(resG);

        System.out.println("\nDetalhes DP:");
        listarTarefasResultado(resDP);
    }

    private static void listarTarefasResultado(Resultado res) {
        // Accessing private list via reflection or just trusting the print method?
        // Resultado has exibirRelatorio, let's use that or just print summary.
        // But the user might want to see WHICH tasks were chosen.
        // Resultado.exibirRelatorio() prints to stdout.
        res.exibirRelatorio();
    }

    private static int lerInteiro(Scanner scanner, String prompt) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(prompt);
            try {
                valor = Integer.parseInt(scanner.nextLine());
                if (valor < 0) {
                    System.out.println("Por favor, insira um valor não negativo.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
        return valor;
    }
}
