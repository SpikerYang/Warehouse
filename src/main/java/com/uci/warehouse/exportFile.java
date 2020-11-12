package com.uci.warehouse;

import java.io.*;
import java.util.Scanner;

public class exportFile {

    private static String filename;
    private static boolean append;

    private static void creatfile(String pathname) {
        try {
            File file = new File(pathname);

            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                append=false;
            } else {
                System.out.println("File already exists. Content will be appended.");
                append=true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * export a txt with direction
     * @param direction: string of route instruction
     */
    public static void exportTxt(String filename, String direction) {

        try {
            creatfile(filename);
            FileWriter myWriter = new FileWriter(filename,append);

            myWriter.write(direction);
            myWriter.close();
            System.out.printf("Successfully wrote to %s.\n", filename);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

//    public static void main(String[] args){
//        exportTxt("go east");
//        exportTxt("go north");
//    }
}