package com.yabetancourt.application.utils;

public class Parser {

    private static double[][] mat;

    private static double[] b;

    private static int n;

    public static void parseMatrix(String matrixText) throws IrregularMatrixException, NumberFormatException {
        matrixText = matrixText.replaceAll("\\s{2,}", " ");
        String[] equations = matrixText.split("\n");
        n = equations.length;
        mat = new double[n][n];
        b = new double[n];
        for (int i = 0; i < equations.length; i++) {
            String[] values = equations[i].split(" ");
            if (values.length != n + 1)
                throw new IrregularMatrixException();
            for (int j = 0; j < n; j++)
                mat[i][j] = Double.parseDouble(values[j]);
            b[i] = Double.parseDouble(values[n]);
        }

    }

    public static double[][] getMatrix() {
        return mat;
    }

    public static double[] getIndependentVector() {
        return b;
    }

    public static int getDimension() {
        return n;
    }
}
