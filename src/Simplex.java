// //Alunos:
// //          Ana Carolina Silva Borges
// //          Victor Emannuel de Souza Teixeira

// import java.util.Scanner;

// public class Simplex {

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         // Entrada de Dados
//         double[][] tableau = getInputTableau(scanner);

//         // Executar o Algoritmo Simplex
//         boolean optimal = false;
//         while (!optimal) {
//             // Determinar Variáveis Básicas e Não Básicas
//             int enteringVariable = selectEnteringVariable(tableau);
//             int leavingVariable = selectLeavingVariable(tableau, enteringVariable);

//             // Cálculos de Pivot
//             performPivot(tableau, enteringVariable, leavingVariable);

//             // Verificação de Solução Ótima
//             optimal = isOptimal(tableau);
//         }

//         // Exibir Resultados
//         printResult(tableau);

//         scanner.close();
//     }


//     private static double[][] getInputTableau(Scanner scanner) {
//         System.out.println("Informe o número de variáveis na função objetivo:");
//         int numVariables = scanner.nextInt();
    
//         System.out.println("Informe o número de restrições:");
//         int numConstraints = scanner.nextInt();
    
//         // Tamanho da matriz tableau é (numConstraints + 1) x (numVariables + numConstraints + 2)
//         double[][] tableau = new double[numConstraints + 1][numVariables + numConstraints + 2];
    
//         System.out.println("Informe os coeficientes da função objetivo:");
//         for (int j = 0; j < numVariables; j++) {
//             System.out.print("Coeficiente da variável x" + (j + 1) + ": ");
//             tableau[0][j] = scanner.nextDouble();
//         }
    
//         for (int i = 1; i <= numConstraints; i++) {
//             System.out.println("Informe os coeficientes da restrição " + i + ":");
//             for (int j = 0; j < numVariables; j++) {
//                 System.out.print("Coeficiente da variável x" + (j + 1) + ": ");
//                 tableau[i][j] = scanner.nextDouble();
//             }
//             System.out.print("Valor do lado direito da restrição: ");
//             tableau[i][numVariables + i - 1] = scanner.nextDouble();
//         }
    
//         // Definir os coeficientes da última coluna (coeficientes das variáveis de folga)
//         for (int i = 1; i <= numConstraints; i++) {
//             tableau[i][numVariables + numConstraints] = 1.0;
//         }
    
//         // Definir os coeficientes da última coluna (coeficientes da variável artificial)
//         for (int i = 1; i <= numConstraints; i++) {
//             tableau[i][numVariables + numConstraints + 1] = 0.0;
//         }
    
//         return tableau;
//     }
    

//     private static int selectEnteringVariable(double[][] tableau) {
//         int numVariables = tableau[0].length - 1; // O último elemento da linha é a função objetivo
    
//         int enteringVariable = -1;
//         double maxCoefficient = 0.0;
    
//         for (int j = 0; j < numVariables; j++) {
//             double coefficient = tableau[0][j]; // Coeficiente na linha da função objetivo
    
//             // Se o coeficiente for negativo e maior em magnitude do que o máximo atual
//             if (coefficient < 0 && Math.abs(coefficient) > Math.abs(maxCoefficient)) {
//                 maxCoefficient = coefficient;
//                 enteringVariable = j;
//             }
//         }
    
//         return enteringVariable;
//     }
    


//     private static int selectLeavingVariable(double[][] tableau, int enteringVariable) {
//         int numRows = tableau.length;
//         int numCols = tableau[0].length;
    
//         int leavingVariable = -1;
//         double minRatio = Double.MAX_VALUE;
    
//         for (int i = 1; i < numRows; i++) {
//             if (tableau[i][enteringVariable] > 0) {
//                 double ratio = tableau[i][numCols - 1] / tableau[i][enteringVariable];
//                 if (ratio < minRatio) {
//                     minRatio = ratio;
//                     leavingVariable = i;
//                 }
//             }
//         }
    
//         return leavingVariable;
//     }
    
    
    
    

//     private static void performPivot(double[][] tableau, int enteringVariable, int leavingVariable) {
//         int numRows = tableau.length;
//         int numCols = tableau[0].length;
    
//         double pivotElement = tableau[leavingVariable][enteringVariable];
        
//         // Fazer o elemento pivot igual a 1
//         for (int j = 0; j < numCols; j++) {
//             tableau[leavingVariable][j] /= pivotElement;
//         }
    
//         // Atualizar outras linhas
//         for (int i = 0; i < numRows; i++) {
//             if (i != leavingVariable) {
//                 double factor = tableau[i][enteringVariable];
//                 for (int j = 0; j < numCols; j++) {
//                     tableau[i][j] -= factor * tableau[leavingVariable][j];
//                 }
//             }
//         }
//     }
    

//     private static boolean isOptimal(double[][] tableau) {
//         int lastRow = tableau.length - 1;
//         int numColumns = tableau[0].length;
    
//         // Iterar pela última linha (função objetivo) para verificar se todos os coeficientes são não negativos
//         for (int j = 0; j < numColumns; j++) {
//             if (tableau[lastRow][j] < 0) {
//                 return false; // Se qualquer coeficiente for negativo, a solução não é ótima
//             }
//         }
    
//         return true; // Todos os coeficientes são não negativos, a solução é ótima
//     }
    

//     private static void printResult(double[][] tableau) {
//         int numRows = tableau.length;
//         int numCols = tableau[0].length;
    
//         System.out.println("Tabela Simplex:");
    
//         // Exibir cabeçalho das colunas
//         for (int j = 1; j < numCols - 1; j++) {
//             System.out.printf("%10s", "x" + j);
//         }
//         System.out.printf("%10s", "B");
//         System.out.println();
    
//         // Exibir variáveis básicas, não básicas e os valores das variáveis
//         for (int i = 1; i < numRows; i++) {
//             for (int j = 1; j < numCols - 1; j++) {
//                 System.out.printf("%10.2f", tableau[i][j]);
//             }
//             System.out.printf("%10.2f", tableau[i][numCols - 1]);
//             System.out.println();
//         }
    
//         // Exibir o valor da função objetivo na última linha
//         System.out.print("Z:");
//         for (int j = 1; j < numCols - 1; j++) {
//             System.out.printf("%10.2f", tableau[0][j]);
//         }
//         System.out.printf("%10.2f", tableau[0][numCols - 1]);
//         System.out.println();
//     }
    
// }
