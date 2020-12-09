package main.java.com.uci.warehouse.Draw;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class DrawColors {
    private ArrayList<Color> colors;

    public DrawColors(){
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.PURPLE);
        colors.add(Color.DARKSLATEBLUE);
        colors.add(Color.DARKORANGE);
        colors.add(Color.GOLD);
        colors.add(Color.BLACK);
        colors.add(Color.BISQUE);
        colors.add(Color.ORANGE);
        colors.add(Color.BROWN);
        colors.add(Color.ANTIQUEWHITE);
        colors.add(Color.YELLOW);

    }

    public int getColorSize(){
        return colors.size();
    }

    public Color getColor(int i){
        i = i%colors.size();
        return colors.get(i);
    }
}
