package com.Roclh;

import com.Roclh.UI.Terminal;
import com.Roclh.wrapper.Matrix;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Gaussian {
    Terminal terminal = new Terminal();

    public Gaussian() {

    }

    //Метод объединяет все действия, необходимые для решения
    public void solve(Matrix matrix) {
        terminal.writeMatrix(matrix);
        System.out.println("Получение треугольной матрицы");
        terminal.writeMatrix(getTriangleMatrix(matrix));
        System.out.println("Рассчет определителя: ");
        System.out.println(determinant(matrix.shorten()));
        System.out.println("Рассчет вектора результатов: ");
        terminal.printArray(results(getTriangleMatrix(matrix)));
        System.out.println("Рассчет вектора невязок:");
        terminal.printScientificArray(residualVector(matrix, results(getTriangleMatrix(matrix))));
    }

    //Метод рассчитывает определитель матрицы перемножая значения основной диагонали треугольной матрицы
    public double determinant(Matrix matrix) {
        Matrix temp = getTriangleMatrix(matrix);
        double determinant = 1;
        for (int i = 0; i < temp.height; i++) {
                determinant *= temp.getArray()[i][i];
        }
        if(Math.round(determinant)-determinant<0.000001d){
            return Math.round(determinant);
        }else if(determinant-Math.floor(determinant)<0.000001d){
            return Math.floor(determinant);
        }else return determinant;

    }

    //Метод преобразует матрицу в треугольню методом элементарных преобразований
    public Matrix getTriangleMatrix(Matrix matrix) {
        Matrix temp = new Matrix(matrix);
        double num;
        for (int j = 0; j < temp.height; j++) {
            for (int i = j + 1; i < temp.height; i++) {
                if (temp.getArray()[j][j] == 0) {
                    break;
                }
                num = temp.getArray()[i][j] / temp.getArray()[j][j];
                for (int k = j; k < temp.length; k++) {
                    temp.getArray()[i][k] = temp.getArray()[i][k] - temp.getArray()[j][k] * num;
                }
            }
            //terminal.writeMatrix(temp);
            if (j < temp.height - 1) {
                int count = 0;
                for (int i = j + 1; i < temp.height; i++) {
                    if (temp.getArray()[i][j + 1] != 0) {
                        for (int k = j; k < temp.length; k++) {
                            double buf = temp.getArray()[j + 1 + count][k];
                            temp.getArray()[j + 1 + count][k] = temp.getArray()[i][k];
                            temp.getArray()[i][k] = buf;
                        }
                        count++;
                    }
                }
            }
            //terminal.writeMatrix(temp);

        }
        return temp;
    }

    //Метод рассчитывает вектор результатов треугольной матрицы, вычисляя коэфициенты и подставляя значения в предыдущие уравнения.
    public double[] results(Matrix triangleMatrix){
        Matrix matrix = new Matrix(triangleMatrix);

        double[] results = new double[matrix.height];
        for(int i=matrix.height-1; i>=0; i--){
            results[i]=matrix.getArray()[i][i+1]/matrix.getArray()[i][i];
            for(int j = i; j>=0; j--){
                matrix.getArray()[j][i]=matrix.getArray()[j][i+1] - matrix.getArray()[j][i]*results[i];
            }
        }
        return results;
    }

    //Метод рассчитывает вектор невязок.
    public double[] residualVector(Matrix matrix, double[] results){
        Matrix copy = new Matrix(matrix);
        copy = copy.shorten();
        double[] residualVector = new double[results.length];
        for(int i=0; i< copy.height; i++){
            for(int j=0; j< copy.height; j++){
                residualVector[i]+=copy.getArray()[i][j]*results[j];
            }
            residualVector[i]-=matrix.getArray()[i][matrix.length-1];
        }
        return residualVector;
    }
}
