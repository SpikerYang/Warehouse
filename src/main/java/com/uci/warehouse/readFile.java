
package com.uci.warehouse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
    public class readFile {
        public Map<Integer,double[]> readfile(String fileLocation) throws FileNotFoundException {
            Scanner input = new Scanner(new File(fileLocation));
            Map<Integer,double[]> productsLocation = new HashMap<>();
            input.nextLine();
            while(input.hasNextLine()){
                String str = input.nextLine();
                String[] data = str.split("\t");
                int key = Integer.parseInt(data[0]);
                double xx = new Double(data[1]);
                double yy = new Double(data[2]);
                double [] location = new double[]{xx,yy};
                productsLocation.put(key,location);
            }
            return productsLocation;
        }
    }
