package main.java.com.uci.warehouse.Draw;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DrawMap {

    private double unit = 20;
    private DrawColors drawColors = new DrawColors();


    public Pane drawShelve(int x, int y){
        Pane pane = setPane(calTopLeftX(x),calTopLeftY(y));
        DrawShelve drawShelve = new DrawShelve(unit);
        pane.getChildren().addAll(
                drawShelve.getLeftBound(),
                drawShelve.getRightBound(),
                drawShelve.getTopBound()
        );
        return pane;
    }

//    public Circle drawProduct(double x, double y){
//        Circle product = new Circle();
//        product.setCenterX(calTopLeftX(calTopLeftX(x)));
//        product.setCenterY(calTopLeftY(calTopLeftX(y)));
//        product.setRadius(1);
//        return product;
//    }

    public void drawProduct(double x, double y, GraphicsContext gc){
        Canvas canvas = new Canvas(460, 820);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BLACK);
//        gc.setFont(Font.getDefault());
//        gc.fillText("hello   world!", 15, 50);

        gc.setLineWidth(2);
        gc.setStroke(Color.RED);
        gc.strokeOval(calTopLeftX(x), calTopLeftY(y)+0.5*unit, 5, 5);

//        Canvas canvas = new Canvas(460, 820);
//        final GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//        gc.setFill(Color.BLACK);
//        gc.setFont(Font.getDefault());
//        gc.fillText("hello   world!", 15, 50);
//
//        gc.setLineWidth(5);
//        gc.setStroke(Color.PURPLE);
//
//        gc.strokeOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.strokeRect(30, 100, 40, 40);
//        menuPane.getChildren().addAll(canvas);

    }

    //type: 0 up, 1 down, 2 right, 3 left
    public void drawRoute(int sx, int sy, int ex, int ey, int type,int color,  GraphicsContext gc){

        sx = sx+1;
        sy = sy+1;
        ex = ex+1;
        ey = ey+1;
//        Canvas canvas = new Canvas(460, 820);
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

//        gc.setFont(Font.getDefault());
//        gc.fillText("hello   world!", 15, 50);

        //gc.setLineWidth(4);
        gc.setFill(drawColors.getColor((color%drawColors.getColorSize())));
        gc.setStroke(drawColors.getColor((color%drawColors.getColorSize())));
        gc.strokeLine(calCenterX(sx),calCenterY(sy),calCenterX(ex),calCenterY(ey));
        printLine(calCenterX(sx),calCenterY(sy),calCenterX(ex),calCenterY(ey));
        double centerX = calCenterX(ex), centerY = calCenterY(ey);
        double polUnit = 3;
        double[] x = new double[3];
        double[] y = new double[3];
        if(type==2){
            x = new double[]{centerX, centerX-2*polUnit, centerX-2*polUnit};
            y = new double[]{centerY, centerY-polUnit, centerY+polUnit};
        }else if(type==3){
            x = new double[]{centerX, centerX+2*polUnit, centerX+2*polUnit};
            y = new double[]{centerY, centerY-polUnit, centerY+polUnit};
        }else if(type==0){
            x = new double[]{centerX, centerX-polUnit, centerX+polUnit};
            y = new double[]{centerY, centerY+2*polUnit, centerY+2*polUnit};
        }else{
            x = new double[]{centerX, centerX-polUnit, centerX+polUnit};
            y = new double[]{centerY, centerY-2*polUnit, centerY-2*polUnit};

        }

        gc.fillPolygon(x ,y , 3);

    }

    public Pane drawCoordinate(int x, int y, String text){
        Pane pane = setPane(calTopLeftX(x),calTopLeftY(y));
        Text t = new Text(text);
        t.setLayoutX(0);
        t.setLayoutY(unit);
        pane.getChildren().addAll(t);
        return pane;
    }

    public void drawText(int x, int y, int color, String text,  GraphicsContext gc){
        x = x+1;
        y = y+1;
        gc.setFill(drawColors.getColor((color%drawColors.getColorSize())));
        gc.setStroke(drawColors.getColor((color%drawColors.getColorSize())));
//
//        Text t = new Text(text);
//        t.setFill(drawColors.getColor((color%drawColors.getColorSize())));
//        t.setLayoutX(0);
//        t.setLayoutY(0.5*unit);
        gc.strokeText(text,calCenterX(x),calCenterY(y));
    }



    public double calTopLeftX(int x){
        return x*unit;
    }

    public double calTopLeftY(int y){
        return (22-y)*unit;
    }

    public double calTopLeftX(double x){
        return x*unit;
    }

    public double calTopLeftY(double y){
        return (22-y)*unit;
    }

    public double calCenterX(int x){
        return x*unit + 0.5*unit;
    }

    public double calCenterY(int y){
        return (22-y)*unit + 0.5*unit;
    }


    public double calCenterX(double x){
        return x*unit + 0.5*unit;
    }

    public double calCenterY(double y){
        return (22-y)*unit + 0.5*unit;
    }



    public Pane setPane(double x, double y){
        Pane pane = new Pane();
        pane.setMaxHeight(unit);
        pane.setMinHeight(unit);
        pane.setMaxWidth(unit);
        pane.setMinWidth(unit);
        pane.setLayoutX(x);
        pane.setLayoutY(y);

//        pane.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
        return pane;

    }
    public double getUnit(){
        return unit;
    }

    public void printLine(double sx, double sy, double ex, double ey){
        System.out.println("start at" + sx + "," + sy +"; end at" + ex + "," + ey);
    }

    public void printIndLine(double sx, double sy, double ex, double ey){
        System.out.println("Ind: start at" + sx + "," + sy +"; end at" + ex + "," + ey);
    }


}
