package main.java.com.uci.warehouse.Util;

import javafx.scene.control.Alert;

/**
 * @Author spike
 * @Date: 2020-11-27 22:41
 */
public class MyAlert {
    public static void sendAlert(String Title, String Header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void sendErrorAlert(String header, String content) {
        sendAlert("ERROR", header, content);
    }
}
