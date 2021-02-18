package com.Roclh.UI;

import com.Roclh.wrapper.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Terminal {
    private final Scanner scanner = new Scanner(System.in);

    public Terminal() {

    }

    public String readLine() {
        System.out.println("Введите строку:");
        return scanner.next();
    }

    public double readDouble() {
        System.out.println("Введите число:");
        return scanner.nextDouble();
    }

    public int readInt() {
        System.out.println("Введите целое число:");
        return scanner.nextInt();
    }


    //Ввод матрицы для вычисления
    public Matrix readMatrix(Matrix emptyMatrix) {
        for (int i = 0; i < emptyMatrix.height; i++) {
            for (int j = 0; j < emptyMatrix.length; j++) {
                System.out.print("Введите элемент матрицы по индексу [" + i + "][" + j + "]: ");
                emptyMatrix.getArray()[i][j] = readDouble();
                System.out.println("");
            }
        }
        return emptyMatrix;
    }

    //Ввод матрицы для вычисления, только массив создается в методе
    public Matrix readMatrix(int height, int length) {
        Matrix emptyMatrix = new Matrix(height, length);
        for (int i = 0; i < emptyMatrix.height; i++) {
            for (int j = 0; j < emptyMatrix.length; j++) {
                System.out.print("Введите элемент матрицы по индексу [" + (i + 1) + "][" + (j + 1) + "]: ");
                emptyMatrix.getArray()[i][j] = readDouble();
                System.out.println("");
            }
        }
        return emptyMatrix;
    }

    public Matrix readMatrixF(String filePath) throws FileNotFoundException {
        Matrix value = null;
        File file = new File(filePath);
        try {
            System.out.println("Начинаю считывать матрицу из файла");
            int height = 0;
            Scanner sizeScanner = new Scanner(file);
            String[] temp = new String[0];
            while (sizeScanner.hasNextLine()) {
                height++;
                temp = sizeScanner.nextLine().split(" ");
            }
            if (height >= temp.length) {
                System.out.println("Данная матрица не соответствует решаемой СЛАУ. Чтение из файла прервано");
                throw new FileNotFoundException();
            }

            sizeScanner.close();
            Scanner scanner = new Scanner(file);
            value = new Matrix(height, temp.length);
            for (int i = 0; i < value.height; i++) {
                String[] numbers = scanner.nextLine().split(" ");
                for (int j = 0; j < value.length; j++) {
                    value.getArray()[i][j] = Double.parseDouble(numbers[j]);
                }
            }
            scanner.close();
            return value;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    public void writeMatrix(Matrix matrix) {
        int maxLength = matrix.getMaxNumberLength();
        for (int j = 0; j < matrix.length; j++) {
            System.out.printf("|%" + maxLength + "d", j + 1);
        }
        System.out.println("|");
        for (int i = 0; i < matrix.height; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix.getArray()[i][j] < 0.0000001d && matrix.getArray()[i][j] > -0.0000001d) {
                    System.out.printf("|%" + maxLength + "f", 0d);
                } else {
                    System.out.printf("|%" + maxLength + "f", matrix.getArray()[i][j]);
                }

            }
            System.out.println("|");
        }
    }

    public void printArray(double[] array){
        for(double num:array){
            System.out.printf("|%.6f", num);
        }
        System.out.println("|");
    }

    public void printScientificArray(double[] array){
        for(double num:array){
            System.out.printf("|%.3e", num);
        }
        System.out.println("|");
    }

}
