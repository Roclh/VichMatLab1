package com.Roclh;

import com.Roclh.UI.Terminal;
import com.Roclh.wrapper.Matrix;

public class Gaussian {
    //Класс, отвечающий за ввод и вывод матриц
    Terminal terminal = new Terminal();

    public Gaussian() {

    }

    //Метод объединяет все действия, необходимые для решения
    public void solve(Matrix matrix) {
        terminal.writeMatrix(matrix);
        if(!checkMainLine(matrix)){
            if(isCorrectable(matrix)){
                System.out.println("Исправляю матрицу");
                Matrix correctMatrix = correctMatrix(matrix);
                doMath(correctMatrix);

            }else{
                System.out.println("Так как матрица имеет столбцы или строки полностью состоящие из нулей,\n" +
                        "определитель матрицы всегда будет равен 0.");
                System.out.println("Матрицу нельзя привести к треугольному виду");
            }
        }else{
            doMath(matrix);
        }


    }

    private void doMath(Matrix correctMatrix) {
        System.out.println("Получение треугольной матрицы");
        Matrix triangleMatrix = getTriangleMatrix(correctMatrix);
        terminal.writeMatrix(triangleMatrix);
        System.out.println("Рассчет определителя: ");
        System.out.println(determinant(correctMatrix.shorten()));
        System.out.println("Рассчет вектора результатов: ");
        double[] results = results(triangleMatrix);
        terminal.printArray(results);
        System.out.println("Рассчет вектора невязок:");
        terminal.printScientificArray(residualVector(correctMatrix, results));
    }

    public boolean isCorrectable(Matrix matrix){
        for(int i=0; i<matrix.height; i++){
            int counter = 0;
            for(int j=0; j< matrix.length; j++){
                if(matrix.getArray()[i][j]==0) counter++;
            }
            if(counter >= matrix.length-1)return false;
        }
        for(int j=0; j<matrix.length; j++){
            int counter = 0;
            for(int i=0; i<matrix.height; i++){
                if(matrix.getArray()[i][j]==0) counter++;
            }
            if(counter >= matrix.height)return false;
        }
        return true;
    }

    public boolean checkMainLine(Matrix matrix){
        for(int i=0; i<matrix.height; i++){
            if(matrix.getArray()[i][i]==0d){
                return false;
            }
        }
        return true;
    }

    public Matrix correctMatrix(Matrix matrix){
        Matrix copy = new Matrix(matrix);
        while(!checkMainLine(copy)){
            System.out.println("На главной диагонале есть нули");
            for(int i=0; i<copy.height; i++){
                if(copy.getArray()[i][i]==0){
                    for(int j=0; j<copy.length; j++){
                        double buffer = copy.getArray()[i][j];
                        if(i == copy.height-1){
                            copy.getArray()[i][j] = copy.getArray()[0][j];
                            copy.getArray()[0][j] = buffer;
                        }else{
                            copy.getArray()[i][j] = copy.getArray()[i+1][j];
                            copy.getArray()[i+1][j] = buffer;
                        }
                    }
                }
            }
        }
        return copy;
    }

    //Метод рассчитывает определитель матрицы перемножая значения основной диагонали треугольной матрицы
    public double determinant(Matrix matrix) {
        Matrix temp = getTriangleMatrix(matrix);
        double determinant = 1;
        for (int i = 0; i < temp.height; i++) {
                determinant *= temp.getArray()[i][i];
        }
        if(Math.ceil(determinant)-determinant<0.000001d){
            return Math.ceil(determinant);
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
