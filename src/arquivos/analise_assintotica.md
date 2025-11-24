# Análise Assintótica dos Algoritmos de Escalonamento

Este documento apresenta a análise de complexidade de tempo e espaço para os dois algoritmos de escalonamento implementados: **Guloso** e **Programação Dinâmica (DP)**.

## Notação
*   $n$: Número de tarefas.
*   $T$: Tempo total disponível (capacidade da mochila).

---

## 1. Escalonador Guloso (`EscalonadorGuloso.java`)

O algoritmo guloso consiste em duas etapas principais: ordenação e seleção.

### Complexidade de Tempo

1.  **Ordenação (Quick Sort)**:
    *   O algoritmo ordena as $n$ tarefas com base na densidade.
    *   **Caso Médio**: $O(n \log n)$.
    *   **Pior Caso**: $O(n^2)$ (ocorre se o pivô for sempre o menor ou maior elemento, embora a implementação aleatória ou mediana mitigue isso, aqui usamos o último elemento fixo).
    *   *Nota: Na prática, para entradas aleatórias, comporta-se como $O(n \log n)$.*

2.  **Seleção (Iteração Linear)**:
    *   O algoritmo percorre a lista ordenada uma única vez.
    *   Para cada tarefa, realiza operações de tempo constante $O(1)$ (verificação de tempo e soma).
    *   Complexidade: $O(n)$.

**Complexidade Total (Tempo):**
$$ O(n \log n) + O(n) = O(n \log n) $$

### Complexidade de Espaço

*   Armazena a lista de tarefas: $O(n)$.
*   A recursão do Quick Sort utiliza espaço de pilha.
    *   Caso Médio: $O(\log n)$.
    *   Pior Caso: $O(n)$.
*   Não utiliza estruturas de dados auxiliares grandes além da lista.

**Complexidade Total (Espaço):**
$$ O(n) $$

---

## 2. Escalonador por Programação Dinâmica (`EscalonadorDP.java`)

O algoritmo utiliza uma matriz para armazenar os subproblemas resolvidos.

### Complexidade de Tempo

1.  **Ordenação (Quick Sort)**:
    *   Assim como no Guloso, realizamos uma ordenação prévia.
    *   Complexidade: $O(n \log n)$.

2.  **Preenchimento da Tabela DP**:
    *   A tabela possui dimensões $(n + 1) \times (T + 1)$.
    *   O algoritmo preenche cada célula da matriz com operações de tempo constante $O(1)$.
    *   Número total de células: $n \times T$.
    *   Complexidade: $O(n \cdot T)$.

3.  **Backtracking**:
    *   Percorre a tabela de $(n, T)$ até $(0, 0)$ no pior caso.
    *   Complexidade: $O(n)$.

**Complexidade Total (Tempo):**
$$ O(n \log n) + O(n \cdot T) + O(n) = O(n \cdot T) $$
*(Assumindo que $T$ é significativo em relação a $\log n$, o termo dominante é $n \cdot T$. Se $T$ for muito pequeno, a ordenação domina).*

*Observação: Este é um algoritmo pseudo-polinomial, pois depende da magnitude do valor $T$, não apenas do número de elementos.*

### Complexidade de Espaço

1.  **Tabela DP**:
    *   Matriz de tamanho $(n + 1) \times (T + 1)$.
    *   Complexidade: $O(n \cdot T)$.

2.  **Lista de Tarefas**:
    *   Armazena as tarefas originais e escolhidas: $O(n)$.

**Complexidade Total (Espaço):**
$$ O(n \cdot T) $$

---

## Comparativo Resumido

| Algoritmo | Tempo (Caso Médio) | Espaço | Característica |
| :--- | :--- | :--- | :--- |
| **Guloso** | $O(n \log n)$ | $O(n)$ | Rápido, Baixo consumo de memória, Não garante ótimo global. |
| **Prog. Dinâmica** | $O(n \cdot T)$ | $O(n \cdot T)$ | Mais lento para $T$ grande, Alto consumo de memória, Garante ótimo global. |

### Quando usar qual?

*   Use **Guloso** quando:
    *   O número de tarefas ($n$) for muito grande.
    *   O tempo disponível ($T$) for muito grande (o que inviabilizaria a matriz DP).
    *   A resposta precisa ser obtida em tempo real.
    *   Uma solução aproximada é aceitável.

*   Use **Programação Dinâmica** quando:
    *   A precisão é crítica (precisa do valor máximo absoluto).
    *   O tempo disponível ($T$) e o número de tarefas ($n$) são razoáveis para a memória disponível.
