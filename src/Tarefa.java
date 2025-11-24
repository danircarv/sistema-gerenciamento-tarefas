public class Tarefa {
    private String id;
    private int tempo;
    private int prazo; 
    private int prioridade;

    public Tarefa(String id, int tempo, int prioridade) {
        this.id = id;
        this.tempo = tempo;
        this.prioridade = prioridade;
        this.prazo = 0; // Padrão
    }

    public Tarefa(String id, int tempo, int prioridade, int prazo) {
        this.id = id;
        this.tempo = tempo;
        this.prioridade = prioridade;
        this.prazo = prazo;
    }

    public String getId() {
        return id;
    }

    public int getTempo() {
        return tempo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public double getDensidade() {
        if (tempo == 0) {

            if (prazo == 0)
                return Double.MAX_VALUE; // Tarefa imediata sem duração, densidade máxima
            return Double.POSITIVE_INFINITY; // Tarefa com duração 0 mas com prazo
        }

        return (double) prioridade / (tempo * (prazo + 1));
    }

    @Override
    public String toString() {
        return String.format("Tarefa{id='%s', tempo=%d, prioridade=%d, densidade=%.2f}", id, tempo, prioridade,
                getDensidade());
    }
}
