package sample.order;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.DatabaseConnection;
import sample.Users;
import sample.crudsystem.Products;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TextField text_filed;

    @FXML
    private TableView<Order> tableView_1;

    @FXML
    private TableColumn<Order, Integer> order_id;

    @FXML
    private TableColumn<Order, Integer> user_id;

    @FXML
    private TableColumn<Order, Integer> product_id;

    @FXML
    void handleButtonAction(ActionEvent event) {

    }

    @FXML
    void handleMouseAction(MouseEvent event) {

    }

    @FXML
    void initialize() {
        showBooks();
        pr_insert.setOnAction(event -> {
            insertRecords();
        });

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
    public ObservableList<Order> getOrderList(){
        ObservableList<Order> productsObservableList = FXCollections.observableArrayList();
        Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
        String select = "SELECT * FROM orders";
        Statement statement;
        ResultSet resultSet;
        try {
            statement = databaseConnection.createStatement();
            resultSet = statement.executeQuery(select);
            Order order;
            while (resultSet.next()){
                order = new Order(resultSet.getInt("order_id"),resultSet.getInt("user_id"), resultSet.getInt("product_id"));
                productsObservableList.add(order);
            }
        }catch (SQLException e){
            System.out.println("something is wrong" + " " +e.getMessage());
        }
        return productsObservableList;
    }
    public void showOrder(){
        ObservableList<Order> orders = getOrderList();
        order_id.setCellValueFactory(new PropertyValueFactory<Order,Integer>("order_id"));
        user_id.setCellValueFactory(new PropertyValueFactory<Order,Integer>("user_id"));
        product_id.setCellValueFactory(new PropertyValueFactory<Order,Integer>("product_id"));
        tableView_1.setItems(orders);
    }
    private void insertRecords(){
        Connection databaseConnection = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO orders(user_id,product_id) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setInt(1,Users.getId());
            preparedStatement.setInt(2, Integer.parseInt(text_filed.getText()));
            preparedStatement.executeUpdate();
            showOrder();
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("Something is wrong" + " " + e.getMessage());
        }
    }
}
