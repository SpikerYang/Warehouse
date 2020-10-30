import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class readFile {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(new File("qvBox-warehouse-data-f20-v01.txt"));
        Map<Integer,double[]> products = new HashMap<>();
        input.nextLine();
        while(input.hasNextLine()){
            String str = input.nextLine();
            String[] data = str.split(" ");
            int key = Integer.getInteger(data[0]);
            double xx = new Double(data[1]);
            double yy = new Double(data[2]);
            double [] location = new double[]{xx,yy};
            products.put(key,location);
        }
        System.out.println("Finish!");
    }
}
