package controllers;

import app.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class AdminLogInC {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    private Connection connection;
    @FXML
    private ImageView backgroundImage;

    public AdminLogInC() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }

    @FXML
    public void initialize() {
        backgroundImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/background2.jpeg"))));
    }

    @FXML
    // validates user credentials for login
    public void logInAdmin(ActionEvent actionEvent) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {  //empty fields
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Enter email and password.");
            return;
        }
        //validating from the database
        if (adminValidation(email, password)){
            showAlert(Alert.AlertType.INFORMATION, "Logged in Successfully", "Logged In!");
            goToViewReports(); //the user is redirected after logging in
        }else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
        }
    }

    //redirection
    private void goToViewReports() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewReports.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Redirecting Failed", "Unable to redirect to the admin reports.");
        }
    }

    //fetching data from database to validate
    private boolean adminValidation(String email, String password) {
        String query = "SELECT * FROM admins WHERE email = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Display alerts
    private void showAlert(Alert.AlertType alertType, String alert, String message) {
        Alert newAlert = new Alert(alertType);
        newAlert.setTitle(alert);
        newAlert.setHeaderText(null);
        newAlert.setContentText(message);
        newAlert.showAndWait();
    }

    //Navigated from sign up button
    @FXML
    public void goToSignUpPage() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminSignUp.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Redirecting Failed", "Unable to redirect to the Sign Up page.");
        }
    }

    //Navigated from go back button
    public void goToUserSelection() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserSelection.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Redirecting Failed", "Unable to redirect. Try again.");
        }
    }
}
