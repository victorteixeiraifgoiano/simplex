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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Número de variáveis de decisão: ");
        int numVariables = scanner.nextInt();

        System.out.print("Número de restrições: ");
        int numConstraints = scanner.nextInt();

        int numRows = numConstraints + 1; 
        int numColumns = numVariables + numConstraints + 1; 

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
            tableau.setEntry(i, numVariables + i - 1, rhs);
        }

        System.out.println("\nTabela Simplex:");
        tableau.printTableau();

        scanner.close();
    }
}
