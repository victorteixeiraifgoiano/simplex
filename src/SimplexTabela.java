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
        int numRows = 3; 
        int numColumns = 5; 

        SimplexTabela tableau = new SimplexTabela(numRows, numColumns);

        tableau.setEntry(0, 0, 1); 
        tableau.setEntry(0, 1, 2); 
        tableau.setEntry(0, 2, 0); 
        tableau.setEntry(0, 3, 0); 
        tableau.setEntry(0, 4, 0); 

        tableau.printTableau();
    }
}
