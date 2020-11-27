
package main.java.com.uci.warehouse.Model;
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

    public Map<Integer,int[]> readfiletoInt(String fileLocation) throws FileNotFoundException {
        Scanner input = new Scanner(new File(fileLocation));
        Map<Integer,int[]> productsLocation = new HashMap<>();
        input.nextLine();
        while(input.hasNextLine()){
            String str = input.nextLine();
            String[] data = str.split("\t");
            int key = Integer.parseInt(data[0]);
            double xx = new Double(data[1]);
            double yy = new Double(data[2]);
            int [] location = new int[]{(int) xx,(int)yy};
            productsLocation.put(key,location);
        }
        return productsLocation;
    }

}
