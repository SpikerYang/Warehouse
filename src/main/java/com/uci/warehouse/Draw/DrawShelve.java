package main.java.com.uci.warehouse.Draw;

import javafx.scene.shape.Line;

public class DrawShelve {
    private double unit;
    private Line leftBound;
    private Line rightBound;
    private Line topBound;

    public DrawShelve(double unit){
        this.unit = unit;
        leftBound = new Line(0,0,0,unit);
        topBound = new Line(0,unit,unit,unit);
        rightBound = new Line(unit,0,unit,unit);
    }

    public Line getLeftBound(){
        return leftBound;
    }

    public Line getRightBound(){
        return rightBound;
    }

    public Line getTopBound(){
        return topBound;
    }

}
