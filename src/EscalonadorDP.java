import java.util.List;
import java.util.ArrayList;

public class EscalonadorDP {

    public Resultado escalonar(List<Tarefa> tarefasOriginal, int tempoTotalDisponivel) {
        Resultado resultado = new Resultado("Programação Dinâmica (0/1 Knapsack Adaptado)");

        // Create a copy and sort it
        List<Tarefa> tarefas = new ArrayList<>(tarefasOriginal);
        quickSort(tarefas, 0, tarefas.size() - 1);

        int n = tarefas.size();
        int T = tempoTotalDisponivel;

        // dp[i][t] stores the max value using first i tasks with time limit t
        int[][] dp = new int[n + 1][T + 1];

        // Build table
        for (int i = 1; i <= n; i++) {
            Tarefa tarefaAtual = tarefas.get(i - 1);
            int tempoTarefa = tarefaAtual.getTempo();
            int valorTarefa = tarefaAtual.getPrioridade();

            for (int t = 1; t <= T; t++) {
                // Option 1: Don't include task i
                dp[i][t] = dp[i - 1][t];

                // Option 2: Include task i (if it fits)
                if (tempoTarefa <= t) {
                    int valorComTarefa = dp[i - 1][t - tempoTarefa] + valorTarefa;
                    if (valorComTarefa > dp[i][t]) {
                        dp[i][t] = valorComTarefa;
                    }
                }
            }
        }

        // Backtracking to find which tasks were chosen
        List<Tarefa> tarefasEscolhidas = new ArrayList<>();
        int i = n;
        int t = T;

        while (i > 0 && t > 0) {
            if (dp[i][t] != dp[i - 1][t]) {
                // Task i was included
                Tarefa tarefaEscolhida = tarefas.get(i - 1);
                tarefasEscolhidas.add(tarefaEscolhida);
                t = t - tarefaEscolhida.getTempo();
            }
            i--;
        }

        // The backtracking adds tasks in reverse order of consideration (n down to 1)
        // We might want to reverse it back or just keep it as is.
        // The problem doesn't specify order, but for display it might look nice.
        // Let's keep it simple as per requirements.

        resultado.setTarefasExecutadas(tarefasEscolhidas);
        // Note: setTarefasExecutadas recalculates the total value and time used based
        // on the list.
        // This acts as a double-check against the DP table value (dp[n][T]).

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
            // Descending order: if current element is greater than pivot
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
