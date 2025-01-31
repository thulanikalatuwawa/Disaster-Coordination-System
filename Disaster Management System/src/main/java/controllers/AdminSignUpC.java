package controllers;

import app.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class AdminSignUpC {
    @FXML
    private TextField fullNameField, emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    public ImageView backgroundImage;

    @FXML
    public void initialize() {
        backgroundImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/background1.jpeg"))));
    }

    // user new account
    public void SignUp(ActionEvent actionEvent) {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) { //empty fields
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required");
            return;
        }
        if (adminRegistration(fullName, email, password)){ // all values validated
            showAlert(Alert.AlertType.INFORMATION, "Success", "Admin Registration Successful");
            goToViewReports(); // redirected view reports
        } else { // account creation failed
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create a new account. Try again");
        }
    }

    // redirection
    private void goToViewReports() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewReports.fxml"));
            Stage stage = (Stage) fullNameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Admin");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Redirecting Error", "Unable to load. Try again");
        }
    }
    // new admin details stored to the database
    private boolean adminRegistration(String fullName, String email, String password) {
        String query = "INSERT INTO admins (full_name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, fullName);
            statement.setString(2, email);
            statement.setString(3, password);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String alertTitle, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigated to the log in from button
    public void goToLogInPage(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminLogin.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Admin Login");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Redirecting Error", "Unable to load login page");
        }
    }
}
