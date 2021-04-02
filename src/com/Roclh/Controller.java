package com.Roclh;

import com.Roclh.UI.Terminal;
import com.Roclh.wrapper.Matrix;

import java.io.FileNotFoundException;

//Класс, реализующий управление программой с помощью терминала.
public class Controller {
    private Matrix matrix;
    private final Terminal terminal = new Terminal();
    private final Gaussian gaussian = new Gaussian();

    public void start(){
//        try {
//            MatrixFactory.createMatrix("10000x10001",10000, 10001);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("Выберите действие:\n1. Ввод матрицы с клавиатуры.\n2. Ввод матрицы из файла");
        boolean isWorking = true;
        while (isWorking){
            switch (terminal.readInt()){
                case 1:
                    System.out.println("Введите длинну матрицы");
                    int length = terminal.readInt();
                    System.out.println("Введите высоту матрицы");
                    int height = terminal.readInt();
                    matrix = terminal.readMatrix(height, length);
                    gaussian.solve(matrix);
                    isWorking = false;
                    break;
                case 2:
                    while(true) {
                        try {
                            System.out.println("Введите путь до файла");
                            String filePath = terminal.readLine();
                            matrix = terminal.readMatrixF(filePath);
                            break;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    gaussian.solve(matrix);
                    isWorking = false;
                    break;
                default:
                    System.out.println("Такого варианта нету.");
            }
        }
    }

}
