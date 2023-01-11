package com.yabetancourt.application.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.yabetancourt.application.utils.Parser.getMatrix;
import static com.yabetancourt.application.utils.Parser.parseMatrix;

public class DoolittleFactorization {

    public static Map<String, double[][]> doolittleDecomposition(double[][] mat, int n) throws DecompositionException {
        double[][] lower = new double[n][n];
        double[][] upper = new double[n][n];

        // Descomponiendo la matriz en una matriz triangular superior
        // y en una triangular inferior con diagonal unitaria.
        for (int i = 0; i < n; i++) {
            // Triangular Superior
            for (int k = i; k < n; k++) {
                // Sumando L(i, j) * U(j, k)
                double sum = 0.0;
                for (int j = 0; j < i; j++)
                    sum += (lower[i][j] * upper[j][k]);

                // Evaluando U(i, k)
                upper[i][k] = mat[i][k] - sum;
            }
            if (upper[i][i] == 0)
                throw new DecompositionException();
            // Triangular Inferior
            for (int k = i; k < n; k++) {
                if (i == k)
                    lower[i][i] = 1; // La Diagonal es 1
                else {
                    // Realizando la sumatoria de L(k, j) * U(j, i)
                    double sum = 0;
                    for (int j = 0; j < i; j++)
                        sum += (lower[k][j] * upper[j][i]);

                    // Evaluando L(k, i)
                    lower[k][i] = (mat[k][i] - sum) / upper[i][i];
                }
            }

        }

        Map<String, double[][]> ans = new HashMap<>();
        ans.put("lower", lower);
        ans.put("upper", upper);

        return ans;
    }

    public static void main(String[] arr) throws IrregularMatrixException, DecompositionException{
        Scanner sc = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 3; i++){
            s.append(sc.nextLine()).append('\n');
        }
        parseMatrix(s.toString());
        doolittleDecomposition(getMatrix(), 3);

    }

}
