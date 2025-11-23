public class Tarefa {
    private String id;
    private int tempo;
    private int prazo; // Not used in the core logic of these specific algorithms but part of the problem domain
    private int prioridade;

    public Tarefa(String id, int tempo, int prioridade) {
        this.id = id;
        this.tempo = tempo;
        this.prioridade = prioridade;
        this.prazo = 0; // Default
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
            // If tempo is 0, density is infinite unless prazo is also 0 (undefined or special case).
            // Assuming a task with 0 tempo and 0 prazo is not well-defined for density calculation,
            // or represents an immediate, infinitely dense task.
            // For practical purposes, we can return a very large number or handle as an error.
            // Let's return a large number if prazo is also 0, otherwise infinity if prazo > 0.
            if (prazo == 0) return Double.MAX_VALUE; // Immediate task with no duration, highest density
            return Double.POSITIVE_INFINITY; // Task with 0 duration but a deadline
        }
        // If prazo is 0, it implies an immediate deadline, which should increase density.
        // To avoid division by zero and reflect high urgency, treat 0 prazo as 1 for calculation
        // or apply a different logic. A common approach is to add 1 to prazo to avoid division by zero
        // and ensure that a prazo of 0 results in the highest urgency factor.
        // Let's assume prazo 0 means "as soon as possible" and should maximize its impact.
        // A simple way to incorporate prazo such that smaller prazo increases density is to divide by (prazo + 1)
        // or multiply by (1 / (prazo + 1)).
        // Or, if prazo is a deadline, a smaller prazo means higher urgency.
        // Let's use a formula that increases density for smaller prazo, and avoids division by zero.
        // A common heuristic for priority considering deadline is priority / (time * (deadline + 1))
        // or priority / time + (1 / (deadline + 1)).
        // Given the existing structure, let's modify the denominator.
        // If prazo is 0, it means immediate. If prazo is large, it means less urgent.
        // So, (prioridade / tempo) * (1 / (prazo + 1)) or prioridade / (tempo * (prazo + 1))
        // This makes density inversely proportional to prazo (and tempo).
        return (double) prioridade / (tempo * (prazo + 1));
    }

    @Override
    public String toString() {
        return String.format("Tarefa{id='%s', tempo=%d, prioridade=%d, densidade=%.2f}", id, tempo, prioridade, getDensidade());
    }
}
