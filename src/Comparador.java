import java.util.List;

public class Comparador {

    public static void main(String[] args) {
        EscalonadorGuloso guloso = new EscalonadorGuloso();
        EscalonadorDP dp = new EscalonadorDP();

        System.out.println("============================================================");
        System.out.println("   COMPARATIVO: GULOSO vs PROGRAMAÇÃO DINÂMICA");
        System.out.println("============================================================");

        // ---------------------------------------------------------
        // CENÁRIO 1: FAVORÁVEL AO GULOSO
        // ---------------------------------------------------------
        System.out.println("\n>>> CENÁRIO 1: Favorável ao Guloso (Tempo Limite: 10)");
        List<Tarefa> caso1 = GeradorTestes.getCasoFavoravelGuloso();
        executarTeste(guloso, dp, caso1, 10);

        // ---------------------------------------------------------
        // CENÁRIO 2: GULOSO FALHA
        // ---------------------------------------------------------
        System.out.println("\n>>> CENÁRIO 2: Guloso Falha (Tempo Limite: 6)");
        List<Tarefa> caso2 = GeradorTestes.getCasoGulosoFalha();
        executarTeste(guloso, dp, caso2, 6);

        // ---------------------------------------------------------
        // CENÁRIO 3: TAREFAS IDÊNTICAS
        // ---------------------------------------------------------
        System.out.println("\n>>> CENÁRIO 3: Tarefas Idênticas (Tempo Limite: 30)");
        List<Tarefa> caso3 = GeradorTestes.getCasoTarefasIdenticas();
        executarTeste(guloso, dp, caso3, 30);

        // ---------------------------------------------------------
        // CENÁRIO 4: INSTÂNCIA GRANDE (ALEATÓRIA)
        // ---------------------------------------------------------
        System.out.println("\n>>> CENÁRIO 4: Instância Grande (20 tarefas, Tempo Limite: 100)");
        List<Tarefa> caso4 = GeradorTestes.getCasoAleatorio(20, 20, 50);
        executarTeste(guloso, dp, caso4, 100);
    }

    private static void executarTeste(EscalonadorGuloso guloso, EscalonadorDP dp, List<Tarefa> tarefas,
            int tempoLimite) {
        // Execução Guloso
        long startG = System.nanoTime();
        Resultado resG = guloso.escalonar(tarefas, tempoLimite);
        long endG = System.nanoTime();
        double timeG = (endG - startG) / 1_000_000.0; // ms

        // Execução DP
        long startDP = System.nanoTime();
        Resultado resDP = dp.escalonar(tarefas, tempoLimite);
        long endDP = System.nanoTime();
        double timeDP = (endDP - startDP) / 1_000_000.0; // ms

        // Exibir Resultados Resumidos
        System.out.printf("%-20s | Valor: %-5d | Tempo Usado: %-5d | Execução: %.4f ms%n",
                "Guloso", resG.getValorTotal(), resG.getTempoUsado(), timeG);

        System.out.printf("%-20s | Valor: %-5d | Tempo Usado: %-5d | Execução: %.4f ms%n",
                "Prog. Dinâmica", resDP.getValorTotal(), resDP.getTempoUsado(), timeDP);

        int diff = resDP.getValorTotal() - resG.getValorTotal();
        if (diff == 0) {
            System.out.println(">> RESULTADO: Empate (Ambos encontraram o mesmo valor)");
        } else {
            double percent = (double) diff / resG.getValorTotal() * 100;
            System.out.printf(">> RESULTADO: DP foi melhor por %d pontos (%.1f%%)%n", diff, percent);
        }

        // Opcional: Imprimir listas detalhadas se necessário
        // resG.exibirRelatorio();
        // resDP.exibirRelatorio();
    }
}
