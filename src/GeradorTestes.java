import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorTestes {

    public static List<Tarefa> getCasoFavoravelGuloso() {
        List<Tarefa> tarefas = new ArrayList<>();
        tarefas.add(new Tarefa("T1", 2, 10)); // Densidade 5.0
        tarefas.add(new Tarefa("T2", 3, 12)); // Densidade 4.0
        tarefas.add(new Tarefa("T3", 5, 15)); // Densidade 3.0
        tarefas.add(new Tarefa("T4", 4, 8)); // Densidade 2.0
        return tarefas;
    }

    public static List<Tarefa> getCasoGulosoFalha() {
        List<Tarefa> tarefas = new ArrayList<>();
        tarefas.add(new Tarefa("T1", 1, 6)); // Densidade 6.0
        tarefas.add(new Tarefa("T2", 1, 5)); // Densidade 5.0
        tarefas.add(new Tarefa("T3", 5, 20)); // Densidade 4.0
        return tarefas;
    }

    public static List<Tarefa> getCasoTarefasIdenticas() {
        List<Tarefa> tarefas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tarefas.add(new Tarefa("T" + i, 5, 10));
        }
        return tarefas;
    }

    public static List<Tarefa> getCasoAleatorio(int quantidade, int maxTempo, int maxPrioridade) {
        List<Tarefa> tarefas = new ArrayList<>();
        Random rand = new Random(42); // Seed for reproducibility
        for (int i = 1; i <= quantidade; i++) {
            int tempo = rand.nextInt(maxTempo) + 1;
            int prioridade = rand.nextInt(maxPrioridade) + 1;
            tarefas.add(new Tarefa("T" + i, tempo, prioridade));
        }
        return tarefas;
    }
}
