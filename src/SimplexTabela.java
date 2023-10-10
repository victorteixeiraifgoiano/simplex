//Alunos:
//          Ana Carolina Silva Borges
//          Victor Emannuel de Souza Teixeira

import java.util.Scanner;

public class SimplexTabela {
    private int numRows;
    private int numColumns;
    private double[][] tableau;

    public SimplexTabela(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        tableau = new double[numRows][numColumns];
    }

    public void setEntry(int row, int column, double value) {
        tableau[row][column] = value;
    }

    public double getEntry(int row, int column) {
        return tableau[row][column];
    }

    public void printTableau() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(tableau[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void findBasicAndNonBasicVariables() {
        int[] basicVariables = new int[numRows - 1];
        int[] nonBasicVariables = new int[numColumns - numRows + 1];

        for (int i = 1; i < numRows; i++) {
            int pivotColumn = -1;
            for (int j = 0; j < numColumns - 1; j++) {
                if (tableau[i][j] == 1) {
                    pivotColumn = j;
                    break;
                }
            }
            if (pivotColumn != -1) {
                basicVariables[i - 1] = pivotColumn;
            }
        }

        int nbIndex = 0;
        for (int j = 0; j < numColumns - 1; j++) {
            boolean isNonBasic = true;
            for (int i = 1; i < numRows; i++) {
                if (basicVariables[i - 1] == j) {
                    isNonBasic = false;
                    break;
                }
            }
            if (isNonBasic) {
                nonBasicVariables[nbIndex] = j;
                nbIndex++;
            }
        }

        System.out.println("\nVariáveis Básicas:");
        for (int i = 0; i < basicVariables.length; i++) {
            System.out.println("x" + (basicVariables[i] + 1));
        }

        System.out.println("\nVariáveis Não Básicas:");
        for (int i = 0; i < nonBasicVariables.length; i++) {
            System.out.println("x" + (nonBasicVariables[i] + 1));
        }
    }

    public int findEnteringVariable() {
        int numVariables = numColumns - 1;
        double[] coefficients = new double[numVariables];
    
        // Calcule os coeficientes da função objetivo
        for (int j = 0; j < numVariables; j++) {
            coefficients[j] = tableau[0][j];
        }
    
        // Encontre a coluna de entrada (variável candidata)
        int enteringVariable = -1;
        for (int j = 0; j < numVariables; j++) {
            if (coefficients[j] > 0) {
                enteringVariable = j;
                break;
            }
        }
    
        return enteringVariable;
    }
    
    public int findLeavingVariable(int pivotColumn) {
        int pivotRow = -1;
        double minRatio = Double.MAX_VALUE;
    
        for (int i = 1; i < numRows; i++) {
            double rhs = getEntry(i, numColumns - 1);
            double coefficient = getEntry(i, pivotColumn);
    
            if (coefficient > 0) {
                double ratio = rhs / coefficient;
    
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }
        }
    
        return pivotRow;
    }

    public void performPivotOperation(int pivotRow, int pivotColumn) {
        double pivotElement = tableau[pivotRow][pivotColumn];
        int numRows = tableau.length;
        int numColumns = tableau[0].length;
    
        // Fazer o elemento pivot igual a 1
        for (int j = 0; j < numColumns; j++) {
            tableau[pivotRow][j] /= pivotElement;
        }
    
        // Fazer os elementos na coluna do pivô (exceto o pivô) igual a zero
        for (int i = 0; i < numRows; i++) {
            if (i != pivotRow) {
                double factor = tableau[i][pivotColumn];
                for (int j = 0; j < numColumns; j++) {
                    tableau[i][j] -= factor * tableau[pivotRow][j];
                }
            }
        }
    }
    

    public void simplexAlgorithm() {
        while (true) {
            int pivotColumn = findEnteringVariable();
            if (pivotColumn == -1) {
                // Todas as entradas na linha da função objetivo são não positivas, pare
                break;
            }

            int pivotRow = findLeavingVariable(pivotColumn);
            if (pivotRow == -1) {
                // Não há saída válida, o problema é ilimitado, pare
                break;
            }

            performPivotOperation(pivotRow, pivotColumn);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Número de variáveis de decisão: ");
        int numVariables = scanner.nextInt();

        System.out.print("Número de restrições: ");
        int numConstraints = scanner.nextInt();

        int numRows = numConstraints + 1; // Número de linhas na tabela (incluindo a linha da função objetivo)
        int numColumns = numVariables + numConstraints + 1; // Número de colunas na tabela

        SimplexTabela tableau = new SimplexTabela(numRows, numColumns);

        System.out.println("Insira os coeficientes da função objetivo:");
        for (int j = 0; j < numVariables; j++) {
            System.out.print("Coeficiente da variável x" + (j + 1) + ": ");
            double coefficient = scanner.nextDouble();
            tableau.setEntry(0, j, coefficient);
        }

        System.out.println("Insira os coeficientes das restrições:");
        for (int i = 1; i < numRows; i++) {
            for (int j = 0; j < numVariables; j++) {
                System.out.print("Coeficiente da variável x" + (j + 1) + " na restrição " + i + ": ");
                double coefficient = scanner.nextDouble();
                tableau.setEntry(i, j, coefficient);
            }

            System.out.print("Lado direito da restrição " + i + ": ");
            double rhs = scanner.nextDouble();
            tableau.setEntry(i, numVariables + i - 1, rhs); // Variáveis de folga
        }

        // Imprima a tabela
        System.out.println("\nTabela Simplex:");
        tableau.printTableau();

        // Encontre as variáveis básicas e não básicas
        tableau.findBasicAndNonBasicVariables();

        // Chame o algoritmo simplex
        tableau.simplexAlgorithm();

        //
        //
        //
// Mostre o resultado final das variáveis e da função objetivo
System.out.println("\nResultado Final das Variáveis:");
for (int j = 0; j < numVariables; j++) {
    boolean isBasic = false;
    for (int i = 1; i < numRows; i++) {
        if (tableau.getEntry(i, j) == 1) {
            isBasic = true;
            System.out.println("x" + (j + 1) + " = " + tableau.getEntry(i, numColumns - 1));
            break;
        }
    }
    if (!isBasic) {
        System.out.println("x" + (j + 1) + " = 0");
    }
}

System.out.println("\nResultado Final da Função Objetivo:");
System.out.println("Z = " + tableau.getEntry(0, numColumns - 1));
        //
        //
        //


        scanner.close();
    }

}
