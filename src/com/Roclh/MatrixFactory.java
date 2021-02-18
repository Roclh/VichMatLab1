package com.Roclh;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MatrixFactory {

    public static void createMatrix(String fileName, int height, int length) throws IOException {
        File file = new File("arrays/"+fileName);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, false);
        String result = "";
        for(int i=0; i< height; i++){
            for(int j=0; j< length; j++){
                result += (int)(Math.floor(Math.random()*10)+1);
                if(j<length-1){
                    result+=" ";
                }
            }
            result+="\r\n";
        }
        fileWriter.write(result);
        fileWriter.flush();
    }
}
