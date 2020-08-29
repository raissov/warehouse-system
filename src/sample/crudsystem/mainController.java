package sample.crudsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.DatabaseConnection;

public class mainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField pr_name;

    @FXML
    private TextField pr_cost;

    @FXML
    private TextField pr_country;

    @FXML
    private TextField pr_id;

    @FXML
    private ChoiceBox<String> pr_category;

    @FXML
    private TableView<Products> tableView;

    @FXML
    private TableColumn<Products, Integer> id;

    @FXML
    private TableColumn<Products, String> name;

    @FXML
    private TableColumn<Products, Integer> cost;

    @FXML
    private TableColumn<Products, String> country;

    @FXML
    private TableColumn<Products, String> category;

    @FXML
    private Button pr_insert;

    @FXML
    private Button pr_update;

    @FXML
    private Button pr_delete;

    @FXML
    private Button order_page;

    @FXML
    private void handleButtonAction(ActionEvent event){
        if(event.getSource() == pr_insert){
            insertRecords();
        }else if(event.getSource()==pr_update){
            updateRecords();
        }else if(event.getSource()==pr_delete){
            deleteRecord();
        }
    }
    @FXML
    void initialize() {
        pr_category.getItems().add("Electronic Device");
        pr_category.getItems().add("Cloth");
        showBooks();
        order_page.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../order/main.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 400);
                Stage stage = new Stage();
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Products products = tableView.getSelectionModel().getSelectedItem();
        pr_id.setText(String.valueOf(((int)products.getId())));
        pr_name.setText(products.getName());
        pr_cost.setText(String.valueOf(((int)products.getCost())));
        pr_country.setText(products.getCountry());
    }
    public ObservableList<Products> getProductList(){
        ObservableList<Products> productsObservableList = FXCollections.observableArrayList();
        Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
        String select = "SELECT * FROM products";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = databaseConnection.createStatement();
            resultSet = statement.executeQuery(select);
            Products products;
            while (resultSet.next()){
                products = new Products(resultSet.getInt("id"),resultSet.getString("name"), resultSet.getInt("cost"), resultSet.getString("country"), resultSet.getString("category"));
                productsObservableList.add(products);
            }
        }catch (SQLException e){
            System.out.println("something is wrong" + " " +e.getMessage());
        }
        return productsObservableList;
    }
    public void showBooks(){
        ObservableList<Products> list = getProductList();
        id.setCellValueFactory(new PropertyValueFactory<Products, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
        cost.setCellValueFactory(new PropertyValueFactory<Products, Integer>("cost"));
        country.setCellValueFactory(new PropertyValueFactory<Products, String>("country"));
        category.setCellValueFactory(new PropertyValueFactory<Products, String>("category"));
        tableView.setItems(list);
    }
    private void insertRecords(){
        String query = "INSERT INTO products(name, cost, country, category) VALUES ('" + pr_name.getText() + "','" + pr_cost.getText() + "','" + pr_country.getText() + "','" + pr_category.getValue() +"')";
        executeQuery(query);
        showBooks();
    }

    private void updateRecords(){
        Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
        String query = "UPDATE products SET name = ?, cost = ?, country = ?, category = ?  WHERE id = ?";
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1,pr_name.getText());
            preparedStatement.setInt(2, Integer.parseInt(pr_cost.getText()));
            preparedStatement.setString(3,pr_country.getText());
            preparedStatement.setString(4,pr_category.getValue());
            preparedStatement.setInt(5, Integer.parseInt(pr_id.getText()));
            preparedStatement.executeUpdate();
            showBooks();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Something is wrong" + " " + e.getMessage());
        }

    }
    private void deleteRecord(){
        Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
        String query = "DELETE FROM products WHERE id = ?";
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(pr_id.getText()));
            preparedStatement.executeUpdate();
            showBooks();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Something is wrong" + " " + e.getMessage());
        }
    }
    private void executeQuery(String query){
        Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
        Statement st;
        try {
            st = databaseConnection.createStatement();
            st.executeQuery(query);
        }catch (SQLException e){
            System.out.println("SQL problem" + " " + e.getMessage());
        }
    }

}
