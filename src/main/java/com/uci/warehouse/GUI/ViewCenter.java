package main.java.com.uci.warehouse.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.uci.warehouse.Controller.*;
import main.java.com.uci.warehouse.Config.StaticResourcesConfig;
import main.java.com.uci.warehouse.Model.Order;

import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO This is the flow!!!!
// Filename is included.
// Controller class and fxml are all created. Created one if you think is necessary
// Please finish your parts. See "To do" in each controller.
    /*
                                                            |---locate a product  --- (show product on map in Menu)
    Login--> LoadFile(set default when no input)---> Menu --|---create new orders --->CreateOrder (ask questions)              ---->Map(ask start and end and algorithm)
                                                            |___load an order     --->OrderList (show unfulfilled order list)  ---->Map(ask start and end and algorithm)
     */


public class ViewCenter extends Application {
    //TODO @qirui
    private static final Logger logger = Logger.getLogger(ViewCenter.class.getName());
    private Stage stage;




    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("RNG Warehouse System");
        gotoLogin();
        stage.show();
    }

    public void gotoLogin(){
        try {
            LoginController login = (LoginController) replaceSceneContent(StaticResourcesConfig.LOGIN_VIEW_PATH);
            login.setApp(this);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void gotoMenu() {
        try {
            MenuController menu = (MenuController) replaceSceneContent(StaticResourcesConfig.Menu_VIEW_PATH);
            menu.setApp(this);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    public void gotoOrder(){
        try {
            MenuController menuController = (MenuController) replaceSceneContent(StaticResourcesConfig.ORDER_VIEW_PATH);
            menuController.setApp(this);
        }catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public void gotoOrderList(){
        try {
            OrderListController orderListController = (OrderListController) replaceSceneContent(StaticResourcesConfig.ORDER_LIST_VIEW_PATH);
            orderListController.setApp(this);
        }catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
        }


    }

    public void gotoCreateOrder(){
        try {
            CreateOrderController createOrderController = (CreateOrderController) replaceSceneContent(StaticResourcesConfig.CREATE_ORDER_VIEW_PATH);
            createOrderController.setApp(this);
        }catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void gotoMap(){
        try {
            MapController mapController = (MapController) replaceSceneContent(StaticResourcesConfig.MAP_VIEW_PATH);
            mapController.setApp(this, 1);//TODO ID
        }catch (Exception ex){
            logger.log(Level.SEVERE, null, ex);
        }

    }



    /**
     * 替换场景
     * @param fxml
     * @return
     * @throws Exception
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        InputStream in = ViewCenter.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(ViewCenter.class.getResource(fxml));
        try {
            AnchorPane page = (AnchorPane) loader.load(in);
            Scene scene = new Scene(page, StaticResourcesConfig.STAGE_WIDTH, StaticResourcesConfig.STAGE_HEIGHT);
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "页面加载异常！");
        } finally {
            in.close();
        }
        return (Initializable) loader.getController();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
