package Simulation;

public class Matrix {
	
	public int width, height;
	public double[][] matrix;
	
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
		this.width = matrix[0].length;
		this.height = matrix.length;
	}
	
	public static Matrix Mult(Matrix m1, Matrix m2) {
		if(m1.width == m2.height) {
			double[][] matrix3 = new double[m1.height][m2.width];
			for(int l1 = 0; l1 < m1.height; l1++) {
				for(int c2 = 0; c2 < m2.width; c2++) {
					double soma = 0;
					for(int lc = 0; lc < m2.height; lc++) {
						soma += m1.matrix[l1][lc] * m2.matrix[lc][c2];
					}
					matrix3[l1][c2] = soma;
				}
			}
			Matrix m3 = new Matrix(matrix3);
			return m3;
		}
		return null;
	}
	
}
