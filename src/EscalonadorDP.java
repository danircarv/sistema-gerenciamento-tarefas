import java.util.List;
import java.util.ArrayList;

public class EscalonadorDP {

    public Resultado escalonar(List<Tarefa> tarefasOriginal, int tempoTotalDisponivel) {
        Resultado resultado = new Resultado("Programação Dinâmica (0/1 Knapsack Adaptado)");

        // Cria uma cópia e a ordena
        List<Tarefa> tarefas = new ArrayList<>(tarefasOriginal);
        quickSort(tarefas, 0, tarefas.size() - 1);

        int n = tarefas.size();
        int T = tempoTotalDisponivel;

        // dp[i][t] armazena o valor máximo usando as primeiras i tarefas com limite de
        // tempo t
        int[][] dp = new int[n + 1][T + 1];

        // Constrói a tabela
        for (int i = 1; i <= n; i++) {
            Tarefa tarefaAtual = tarefas.get(i - 1);
            int tempoTarefa = tarefaAtual.getTempo();
            int valorTarefa = tarefaAtual.getPrioridade();

            for (int t = 1; t <= T; t++) {
                // Opção 1: Não incluir a tarefa i
                dp[i][t] = dp[i - 1][t];

                // Opção 2: Incluir a tarefa i (se couber)
                if (tempoTarefa <= t) {
                    int valorComTarefa = dp[i - 1][t - tempoTarefa] + valorTarefa;
                    if (valorComTarefa > dp[i][t]) {
                        dp[i][t] = valorComTarefa;
                    }
                }
            }
        }

        // Backtracking para encontrar quais tarefas foram escolhidas
        List<Tarefa> tarefasEscolhidas = new ArrayList<>();
        int i = n;
        int t = T;

        while (i > 0 && t > 0) {
            if (dp[i][t] != dp[i - 1][t]) {
                // Tarefa i foi incluída
                Tarefa tarefaEscolhida = tarefas.get(i - 1);
                tarefasEscolhidas.add(tarefaEscolhida);
                t = t - tarefaEscolhida.getTempo();
            }
            i--;
        }

        // O backtracking adiciona tarefas na ordem inversa de consideração (n até 1)
        // Poderíamos inverter de volta ou manter como está.
        // O problema não especifica ordem, mas para exibição pode ficar bom.
        // Vamos manter simples conforme os requisitos.

        resultado.setTarefasExecutadas(tarefasEscolhidas);
        // Nota: setTarefasExecutadas recalcula o valor total e o tempo usado com base
        // na lista.
        // Isso age como uma verificação dupla contra o valor da tabela DP (dp[n][T]).

        return resultado;
    }

    private void quickSort(List<Tarefa> tarefas, int low, int high) {
        if (low < high) {
            int pi = partition(tarefas, low, high);
            quickSort(tarefas, low, pi - 1);
            quickSort(tarefas, pi + 1, high);
        }
    }

    private int partition(List<Tarefa> tarefas, int low, int high) {
        Tarefa pivot = tarefas.get(high);
        double pivotDensity = pivot.getDensidade();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // Ordem decrescente: se o elemento atual for maior que o pivô
            if (tarefas.get(j).getDensidade() > pivotDensity) {
                i++;
                swap(tarefas, i, j);
            }
        }
        swap(tarefas, i + 1, high);
        return i + 1;
    }

    private void swap(List<Tarefa> tarefas, int i, int j) {
        Tarefa temp = tarefas.get(i);
        tarefas.set(i, tarefas.get(j));
        tarefas.set(j, temp);
    }
}
