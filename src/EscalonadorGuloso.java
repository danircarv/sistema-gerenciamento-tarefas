import java.util.List;
import java.util.ArrayList;

public class EscalonadorGuloso {

    public Resultado escalonar(List<Tarefa> tarefas, int tempoTotalDisponivel) {
        Resultado resultado = new Resultado("Algoritmo Guloso (Weighted Job Scheduling)");

        // Create a copy to avoid modifying the original list
        List<Tarefa> tarefasOrdenadas = new ArrayList<>(tarefas);

        // Sort by density (descending) using Quick Sort
        quickSort(tarefasOrdenadas, 0, tarefasOrdenadas.size() - 1);

        int tempoDecorrido = 0;

        for (Tarefa t : tarefasOrdenadas) {
            if (tempoDecorrido + t.getTempo() <= tempoTotalDisponivel) {
                resultado.adicionarTarefa(t);
                tempoDecorrido += t.getTempo();
            }
        }

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
