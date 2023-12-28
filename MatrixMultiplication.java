import java.util.Scanner;

public class MatrixMultiplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       
        System.out.print("Enter the number of rows for matrix A: ");
        int rowsA = scanner.nextInt();

        System.out.print("Enter the number of columns for matrix A: ");
        int columnsA = scanner.nextInt();

        System.out.print("Enter the number of columns for matrix B: ");
        int columnsB = scanner.nextInt();

       
        int[][] matrixA = new int[rowsA][columnsA];
        int[][] matrixB = new int[columnsA][columnsB];

    
        System.out.println("Enter the elements of matrix A:");
        fillMatrix(scanner, matrixA);

       
        System.out.println("Enter the elements of matrix B:");
        fillMatrix(scanner, matrixB);

       
        int[][] resultMatrix = matrixMultiplication(matrixA, matrixB);

        
        System.out.println("Result Matrix:");
        printMatrix(resultMatrix);
    }

    
    private static void fillMatrix(Scanner scanner, int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print("Enter element at position (" + (i + 1) + ", " + (j + 1) + "): ");
                matrix[i][j] = scanner.nextInt();
            }
        }
    }

 
    public static int[][] matrixMultiplication(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int columnsA = matrixA[0].length;
        int columnsB = matrixB[0].length;

        int[][] resultMatrix = new int[rowsA][columnsB];

        if (columnsA != matrixB.length) {
            System.out.println("Invalid matrix dimensions for multiplication.");
            return resultMatrix; // Empty matrix
        }

        if (rowsA <= 64 || columnsA <= 64 || columnsB <= 64) {
          
            for (int i = 0; i < rowsA; i++) {
                for (int j = 0; j < columnsB; j++) {
                    for (int k = 0; k < columnsA; k++) {
                        resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        } else {
        
            int halfRowsA = rowsA / 2;
            int halfColumnsA = columnsA / 2;
            int halfColumnsB = columnsB / 2;

            int[][] A11 = new int[halfRowsA][halfColumnsA];
            int[][] A12 = new int[halfRowsA][halfColumnsA];
            int[][] A21 = new int[halfRowsA][halfColumnsA];
            int[][] A22 = new int[halfRowsA][halfColumnsA];

            int[][] B11 = new int[halfColumnsA][halfColumnsB];
            int[][] B12 = new int[halfColumnsA][halfColumnsB];
            int[][] B21 = new int[halfColumnsA][halfColumnsB];
            int[][] B22 = new int[halfColumnsA][halfColumnsB];

           
            splitMatrix(matrixA, A11, 0, 0);
            splitMatrix(matrixA, A12, 0, halfColumnsA);
            splitMatrix(matrixA, A21, halfRowsA, 0);
            splitMatrix(matrixA, A22, halfRowsA, halfColumnsA);

           
            splitMatrix(matrixB, B11, 0, 0);
            splitMatrix(matrixB, B12, 0, halfColumnsB);
            splitMatrix(matrixB, B21, halfColumnsA, 0);
            splitMatrix(matrixB, B22, halfColumnsA, halfColumnsB);

          
            int[][] P1 = matrixMultiplication(addMatrices(A11, A22), addMatrices(B11, B22));
            int[][] P2 = matrixMultiplication(addMatrices(A21, A22), B11);
            int[][] P3 = matrixMultiplication(A11, subtractMatrices(B12, B22));
            int[][] P4 = matrixMultiplication(A22, subtractMatrices(B21, B11));
            int[][] P5 = matrixMultiplication(addMatrices(A11, A12), B22);
            int[][] P6 = matrixMultiplication(subtractMatrices(A21, A11), addMatrices(B11, B12));
            int[][] P7 = matrixMultiplication(subtractMatrices(A12, A22), addMatrices(B21, B22));

           
            int[][] C11 = subtractMatrices(addMatrices(addMatrices(P1, P4), P7), P5);
            int[][] C12 = addMatrices(P3, P5);
            int[][] C21 = addMatrices(P2, P4);
            int[][] C22 = subtractMatrices(addMatrices(addMatrices(P1, P3), P6), P2);

          
            joinMatrices(C11, resultMatrix, 0, 0);
            joinMatrices(C12, resultMatrix, 0, halfColumnsB);
            joinMatrices(C21, resultMatrix, halfRowsA, 0);
            joinMatrices(C22, resultMatrix, halfRowsA, halfColumnsB);
        }

        return resultMatrix;
    }

    
    private static void splitMatrix(int[][] source, int[][] target, int rowOffset, int colOffset) {
        for (int i = 0; i < target.length; i++) {
            System.arraycopy(source[i + rowOffset], colOffset, target[i], 0, target[i].length);
        }
    }

  
    private static int[][] addMatrices(int[][] matrixA, int[][] matrixB) {
        int rows = matrixA.length;
        int columns = matrixA[0].length;
        int[][] resultMatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }

        return resultMatrix;
    }

    private static int[][] subtractMatrices(int[][] matrixA, int[][] matrixB) {
        int rows = matrixA.length;
        int columns = matrixA[0].length;
        int[][] resultMatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }

        return resultMatrix;
    }

    
    private static void joinMatrices(int[][] source, int[][] target, int rowOffset, int colOffset) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, target[i + rowOffset], colOffset, source[i].length);
        }
    }

   
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

