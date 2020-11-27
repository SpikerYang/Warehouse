package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable{
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private ViewCenter viewCenter;

    @FXML
    private Button login_button;

    @FXML
    private TextField login_username;

    @FXML
    private TextField login_password;


    public void loginButtonClick(){

        logger.log(Level.INFO, "the username is：" + login_username.getText());
        logger.log(Level.INFO, "the password is ：" + login_password.getText());
        if("user".equalsIgnoreCase(login_username.getText())
                && "123".equalsIgnoreCase(login_password.getText())) {
            logger.log(Level.INFO, "Log in successful！ Go to Order page");
            viewCenter.gotoOrder();
        } else {
            logger.log(Level.WARNING, "The username or the password is wrong！");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }
}
