import java.util.List;
import java.util.ArrayList;

public class Resultado {
    private List<Tarefa> tarefasExecutadas;
    private int valorTotal;
    private int tempoUsado;
    private String algoritmo;

    public Resultado(String algoritmo) {
        this.algoritmo = algoritmo;
        this.tarefasExecutadas = new ArrayList<>();
        this.valorTotal = 0;
        this.tempoUsado = 0;
    }

    public void adicionarTarefa(Tarefa t) {
        tarefasExecutadas.add(t);
        valorTotal += t.getPrioridade();
        tempoUsado += t.getTempo();
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setTarefasExecutadas(List<Tarefa> tarefas) {
        this.tarefasExecutadas = tarefas;
        // Recalcula totais para garantir consistência
        this.valorTotal = 0;
        this.tempoUsado = 0;
        for (Tarefa t : tarefas) {
            this.valorTotal += t.getPrioridade();
            this.tempoUsado += t.getTempo();
        }
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public int getTempoUsado() {
        return tempoUsado;
    }

    public void exibirRelatorio() {
        System.out.println("=== Relatório: " + algoritmo + " ===");
        System.out.println("Valor Total: " + valorTotal);
        System.out.println("Tempo Usado: " + tempoUsado);
        System.out.println("Tarefas Escolhidas (" + tarefasExecutadas.size() + "):");
        for (Tarefa t : tarefasExecutadas) {
            System.out.println("  - " + t);
        }
        System.out.println("------------------------------------------------");
    }
}
