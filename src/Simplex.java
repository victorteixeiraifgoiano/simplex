//Alunos:
//          Ana Carolina Silva Borges
//          Victor Emannuel de Souza Teixeira

import java.util.Scanner;

public class Simplex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Número de variáveis de decisão e restrições
        System.out.print("Número de variáveis de decisão: ");
        int numVariaveis = scanner.nextInt();
        System.out.print("Número de restrições: ");
        int numRestricoes = scanner.nextInt();

        // Inicialização da matriz (tabela simplex)
        double[][] tabela = new double[numRestricoes + 1][numVariaveis + numRestricoes + 1];

        // Leitura dos coeficientes das restrições
        for (int i = 0; i < numRestricoes; i++) {
            System.out.println("Coeficientes da restrição " + (i + 1));
            for (int j = 0; j < numVariaveis; j++) {
                System.out.print("Coeficiente " + (j + 1) + ": ");
                tabela[i][j] = scanner.nextDouble();
            }
            // Lado direito
            System.out.print("Lado direito: ");
            tabela[i][numVariaveis + numRestricoes] = scanner.nextDouble();
        }

        // Leitura dos coeficientes da função objetivo
        System.out.println("Coeficientes da função objetivo:");
        for (int j = 0; j < numVariaveis; j++) {
            System.out.print("Coeficiente " + (j + 1) + ": ");
            tabela[numRestricoes][j] = scanner.nextDouble();
        }

        // Inicialização das variáveis básicas
        for (int i = 0; i < numRestricoes; i++) {
            tabela[i][numVariaveis + i] = 1.0;
        }

        // Implemente o algoritmo simplex aqui
        while (existeColunaPivo(tabela)) {
            // Encontre a coluna do pivô e a linha do pivô
            int colunaPivo = encontrarColunaPivo(tabela);
            int linhaPivo = encontrarLinhaPivo(tabela, colunaPivo);

            // Faça a pivotação na tabela
            pivoteamento(tabela, linhaPivo, colunaPivo);

            // Exiba a tabela de iterações
            mostrarTabela(tabela);
        }

        // Exiba o resultado final
        System.out.println("Resultado Final:");

        for (int i = 0; i < tabela.length - 1; i++) {
            System.out.println("Variável " + (i + 1) + ": " + tabela[i][tabela[i].length - 1]);
        }

        System.out.println(
                "Valor da Função Objetivo: " + (-tabela[tabela.length - 1][tabela[tabela.length - 1].length - 1]));

        scanner.close();
    }

    // Verifique se há uma coluna pivo positiva
    private static boolean existeColunaPivo(double[][] tabela) {
        int numLinhas = tabela.length;
        int numColunas = tabela[0].length;

        for (int coluna = 0; coluna < numColunas; coluna++) {
            if (tabela[numLinhas - 1][coluna] > 0) {
                return true; // Encontrou uma coluna pivo positiva
            }
        }
        return false; // Nenhuma coluna pivo positiva encontrada
    }

    // Encontre a coluna pivo (maior coeficiente na linha de função objetivo)
    private static int encontrarColunaPivo(double[][] tabela) {
        int numVariaveis = tabela[0].length - 1;
        int colunaPivo = -1;
        double maiorCoeficiente = 0.0;

        for (int j = 0; j < numVariaveis; j++) {
            if (tabela[tabela.length - 1][j] > maiorCoeficiente) {
                maiorCoeficiente = tabela[tabela.length - 1][j];
                colunaPivo = j;
            }
        }

        return colunaPivo;
    }

    // Encontre a linha pivo (menor razão entre o lado direito e o coeficiente da
    // coluna pivo)
    private static int encontrarLinhaPivo(double[][] tabela, int colunaPivo) {
        int numRestricoes = tabela.length - 1;
        int linhaPivo = -1;
        double menorRazao = Double.MAX_VALUE;

        for (int i = 0; i < numRestricoes; i++) {
            if (tabela[i][colunaPivo] > 0) { // Verifique se o coeficiente é positivo
                double razao = tabela[i][tabela[0].length - 1] / tabela[i][colunaPivo];
                if (razao < menorRazao) {
                    menorRazao = razao;
                    linhaPivo = i;
                }
            }
        }

        return linhaPivo;
    }

    // Execute a pivotação na tabela
    private static void pivoteamento(double[][] tabela, int linhaPivo, int colunaPivo) {
        int numRows = tabela.length;
        int numCols = tabela[0].length;

        // Dividir a linha do pivô pelo elemento pivô para torná-lo 1
        double pivotValue = tabela[linhaPivo][colunaPivo];
        for (int j = 0; j < numCols; j++) {
            tabela[linhaPivo][j] /= pivotValue;
        }

        // Garanta que os outros elementos na coluna do pivô sejam zero
        for (int i = 0; i < numRows; i++) {
            if (i != linhaPivo) {
                double ratio = tabela[i][colunaPivo];
                for (int j = 0; j < numCols; j++) {
                    tabela[i][j] -= ratio * tabela[linhaPivo][j];
                }
            }
        }
    }

    // Exiba a tabela de iterações
    private static void mostrarTabela(double[][] tabela) {
        int linhas = tabela.length;
        int colunas = tabela[0].length;

        System.out.println("Tabela de Iterações:");

        // Imprimir cabeçalho da tabela
        for (int j = 0; j < colunas; j++) {
            System.out.printf("%-10s", "x" + (j + 1));
        }
        System.out.println();

        // Imprimir linhas da tabela
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("%-10.2f", tabela[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

}
