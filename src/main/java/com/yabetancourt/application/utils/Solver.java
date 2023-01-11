package com.yabetancourt.application.utils;

import java.util.Map;
import java.util.Scanner;

import static com.yabetancourt.application.utils.DoolittleFactorization.doolittleDecomposition;
import static com.yabetancourt.application.utils.Parser.*;

public class Solver {

    private double[][] L;
    private double[][] U;
    private double[] y;
    private double[] x;

    public Solver(String matrixText) throws IrregularMatrixException, NumberFormatException, DecompositionException, NullDiagonalException {
        parseMatrix(matrixText);
        Map<String, double[][]> decomposition = doolittleDecomposition(getMatrix(), getDimension());
        L = decomposition.get("lower");
        U = decomposition.get("upper");
        y = forwardSubstitution(L, getIndependentVector(), getDimension());
        x = backSubstitution(U, y, getDimension());
        System.out.println(computeDeterminant());
    }

    public static double[] backSubstitution(double[][] mat, double[] b, int n) {
        double[] solution = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++)
                sum += mat[i][j] * solution[j];
            solution[i] = (b[i] - sum) / mat[i][i];
        }
        return solution;
    }

    public static double[] forwardSubstitution(double[][] mat, double[] b, int n) throws NullDiagonalException {
        double[] solution = new double[n];

        for (int i = 0; i < n; i++) {
            if (mat[i][i] == 0) throw new NullDiagonalException();
            double sum = 0.0;
            for (int j = i - 1; j >= 0; j--)
                sum += mat[i][j] * solution[j];
            solution[i] = (b[i] - sum) / mat[i][i];
        }
        return solution;
    }

    public double computeDeterminant(){
        double ans = 1;
        for (int i = 0; i < U.length; i++)
            ans *= U[i][i];
        return ans;
    }

    public static void main(String[] arr) throws IrregularMatrixException, DecompositionException {
        Scanner sc = new Scanner(System.in);
        String s = "";
        for (int i = 0; i < 3; i++) {
            s += sc.nextLine() + '\n';
        }
        //Solver solver = new Solver(s);
//        System.out.println(Arrays.deepToString(solver.getL()));
//        System.out.println(Arrays.deepToString(solver.getU()));
//
//        System.out.println(Arrays.toString(solver.getY()));
//        System.out.println(Arrays.toString(solver.getX()));

    }

    public String getL() {
        String ans = "";
        for (double[] doubles : L) {
            for (int j = 0; j < L.length; j++) {
                ans += doubles[j] + " ";
            }
            ans += '\n';
        }
        return ans;
    }

    public String getU() {
        String ans = "";
        for (double[] doubles : U) {
            for (int j = 0; j < U.length; j++) {
                ans += doubles[j] + " ";
            }
            ans += '\n';
        }
        return ans;
    }

    public String getY() {
        String s = "";
        for (Double d : y)
            s += d + "  ";
        return s;
    }

    public String getX() {
        String s = "";
        for (Double d : x)
            s += d + "  ";
        return s;
    }

}
