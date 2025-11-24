# Funcionamento do Sistema de Gerenciamento de Tarefas

Este documento descreve detalhadamente o funcionamento do sistema de escalonamento de tarefas, que compara duas abordagens algorítmicas: **Gulosa (Greedy)** e **Programação Dinâmica (Dynamic Programming)**.

## Visão Geral

O sistema tem como objetivo selecionar um subconjunto de tarefas a serem executadas dentro de um tempo limite disponível, maximizando o valor total (prioridade) das tarefas escolhidas. Este é um problema clássico de otimização conhecido como **Problema da Mochila (Knapsack Problem)** ou **Weighted Job Scheduling**.

### Componentes Principais

1.  **Tarefa (`Tarefa.java`)**: Representa uma unidade de trabalho.
2.  **Escalonador Guloso (`EscalonadorGuloso.java`)**: Implementa uma estratégia heurística.
3.  **Escalonador DP (`EscalonadorDP.java`)**: Implementa uma estratégia exata baseada em Programação Dinâmica.
4.  **Resultado (`Resultado.java`)**: Armazena e exibe o resultado do escalonamento.
5.  **Main (`Main.java`)**: Interface de linha de comando (CLI) para interação com o usuário.
6.  **Comparador (`Comparador.java`)**: Executa cenários de teste pré-definidos para comparação direta.
7.  **Gerador de Testes (`GeradorTestes.java`)**: Cria cenários de teste padronizados.

---

## Detalhes dos Componentes

### 1. Classe `Tarefa`

A classe `Tarefa` é a estrutura de dados básica. Cada tarefa possui:
*   **ID**: Identificador único (String).
*   **Tempo**: Duração necessária para executar a tarefa (inteiro).
*   **Prioridade**: Valor ou importância da tarefa (inteiro).
*   **Prazo (Deadline)**: Um fator de urgência (inteiro).

**Cálculo de Densidade:**
A "densidade" de uma tarefa é uma métrica crucial para o algoritmo guloso. Ela representa o "custo-benefício" da tarefa.
A fórmula implementada é:
$$
\text{Densidade} = \frac{\text{Prioridade}}{\text{Tempo} \times (\text{Prazo} + 1)}
$$

*   Quanto maior a prioridade, maior a densidade.
*   Quanto menor o tempo, maior a densidade.
*   Quanto menor o prazo (mais urgente), maior a densidade (devido ao termo no denominador).

### 2. Escalonador Guloso (`EscalonadorGuloso`)

Este algoritmo toma decisões locais ótimas na esperança de encontrar um ótimo global.

**Funcionamento:**
1.  **Ordenação**: As tarefas são ordenadas em ordem **decrescente de densidade** utilizando o algoritmo **Quick Sort**.
2.  **Seleção**: O algoritmo itera sobre a lista ordenada.
3.  **Verificação**: Para cada tarefa, verifica se ela cabe no tempo restante disponível.
    *   Se couber: A tarefa é selecionada e o tempo disponível é decrementado.
    *   Se não couber: A tarefa é descartada e o algoritmo passa para a próxima.

**Características:**
*   **Rápido**: Toma decisões simples e diretas.
*   **Não Ótimo**: Nem sempre encontra a solução que maximiza o valor total (pode cair em ótimos locais), especialmente se houver tarefas de alto valor que ocupam muito tempo e impedem várias tarefas menores de serem escolhidas.

### 3. Escalonador por Programação Dinâmica (`EscalonadorDP`)

Este algoritmo resolve o problema de forma exata, garantindo a solução ótima. Ele utiliza a abordagem do **Problema da Mochila 0/1**.

**Funcionamento:**
1.  **Tabela DP**: Cria uma matriz `dp[n+1][T+1]`, onde `n` é o número de tarefas e `T` é o tempo total.
    *   `dp[i][t]` armazena o valor máximo possível considerando as primeiras `i` tarefas com um limite de tempo `t`.
2.  **Preenchimento (Bottom-Up)**:
    *   Para cada tarefa `i` e cada tempo `t` de 0 até `T`:
        *   **Opção 1 (Não incluir)**: O valor é o mesmo que o obtido com as `i-1` tarefas anteriores (`dp[i-1][t]`).
        *   **Opção 2 (Incluir)**: Se a tarefa cabe no tempo `t`, calcula-se o valor de incluí-la somado ao melhor valor possível com o tempo restante (`prioridade + dp[i-1][t-tempo]`).
        *   O valor de `dp[i][t]` será o máximo entre a Opção 1 e a Opção 2.
3.  **Recuperação da Solução (Backtracking)**:
    *   Após preencher a tabela, o valor máximo está em `dp[n][T]`.
    *   O algoritmo percorre a tabela de trás para frente para descobrir quais tarefas foram selecionadas (se `dp[i][t] != dp[i-1][t]`, a tarefa foi incluída).

**Ordenação Prévia:**
Embora não seja estritamente necessário para a lógica da DP, o código inclui uma etapa de ordenação (Quick Sort) igual à do Guloso. Isso serve para organizar a ordem de processamento e exibição, mas não altera o valor final ótimo.

**Características:**
*   **Ótimo Global**: Garante encontrar o maior valor total possível.
*   **Custo Computacional**: Consome mais memória e tempo de processamento, proporcional ao número de tarefas vezes o tempo total disponível ($O(n \times T)$).

### 4. Algoritmo de Ordenação (Quick Sort)

Ambos os escalonadores utilizam uma implementação manual do **Quick Sort**.
*   **Pivô**: Escolhe o último elemento como pivô.
*   **Particionamento**: Reorganiza a lista para que elementos com densidade maior que o pivô fiquem à esquerda e menores à direita.
*   **Recursão**: Aplica o mesmo processo às sublistas.

### 5. Interface (`Main.java`)

O sistema oferece um menu interativo no terminal:
1.  **Adicionar Tarefa**: Permite inserir ID, Tempo, Prioridade e Prazo.
2.  **Definir Tempo**: Ajusta a capacidade total de tempo para o escalonamento.
3.  **Executar Comparação**: Roda ambos os algoritmos com as tarefas cadastradas e exibe um relatório comparativo de Valor Total, Tempo Usado e Tempo de Execução (em ms).
4.  **Listar/Limpar**: Utilitários para gerenciar a lista de tarefas atual.

---

## Fluxo de Execução Típico

1.  O usuário cadastra um conjunto de tarefas com diferentes características.
2.  O usuário define um tempo limite (ex: 100 unidades de tempo).
3.  Ao solicitar a comparação:
    *   O **Guloso** ordena as tarefas e enche a "mochila" com as mais densas primeiro.
    *   O **DP** constrói uma tabela analisando todas as combinações de tempo viáveis.
4.  O sistema exibe os resultados lado a lado.
    *   Geralmente, o DP terá um Valor Total igual ou superior ao Guloso.
    *   O Guloso terá um tempo de execução (CPU) geralmente menor, mas pode perder em Valor Total.
