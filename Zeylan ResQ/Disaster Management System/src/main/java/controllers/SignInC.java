package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SignInC {
    @FXML
    public VBox openingScreen;
    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
    }

    public void openAdminDashboard() {
        navigateTo("/fxml/ViewReports.fxml", "ZRQ Admin");
    }

    public void openCitizenForm() {
        navigateTo("/fxml/DisasterReportForm.fxml", "Citizen - Report Disaster");
    }

    private void navigateTo(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
            Stage currentStage = (Stage) logoImage.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
