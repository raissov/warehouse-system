package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private Button mainLogInButton;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
            mainLogInButton.setOnAction(actionEvent -> {
                String loginText = login_field.getText().trim();
                String loginPassword = password_field.getText().trim();
                if(!loginText.equals("") && !loginPassword.equals("")){
                    loginUser(loginText, loginPassword);
                }else {
                    System.out.println("Please fill the all lines");
                }
            });
            signUpButton.setOnAction(actionEvent -> {
                openNewScene("/sample/signUp.fxml");
            });
    }

    private void loginUser(String loginText, String loginPassword) {
        UserDatabase userDatabase = new UserDatabase();
        Users users = new Users();
        users.setUsername(loginText);
        users.setPassword(loginPassword);
        ResultSet resultSet = userDatabase.getUser(users);
        int count = 0;
        try {
            while(resultSet.next()){
                count++;
                users.setId(resultSet.getInt(1));
            }
            if(count >= 1){
                System.out.println("Entered to the system!");
                openNewScene("/sample/crudsystem/main.fxml");
            }
        }catch (Exception e){
            System.out.println("Something is wrong" + " " + e.getMessage());
        }
    }
    public void openNewScene(String window){
        mainLogInButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}

