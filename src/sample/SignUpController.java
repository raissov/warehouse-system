package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpSurname;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpUsername;

    @FXML
    void initialize() {

        signUpButton.setOnAction(actionEvent -> {
            signUpUser();
        });
    }
    public void signUpUser(){
        UserDatabase userDatabase = new UserDatabase();
        String name = signUpName.getText();
        String surname = signUpSurname.getText();
        String username = signUpUsername.getText();
        String password = signUpPassword.getText();
        Users users =  new Users(name, surname, username, password);
        userDatabase.signUpUser(users);
    }
}
