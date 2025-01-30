package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ReportService;
import java.io.IOException;
import java.util.Objects;


public class ReportDisasterC {
    @FXML
    private TextField FullName, ContactNumber, EmailAddress, NIC, Location, incidentTime, GramaNiladhariDivision;
    @FXML
    private ComboBox<String> districtSelection, provinceSelection, disasterType, urgencyLevel;
    @FXML
    private DatePicker incidentDate;
    @FXML
    private TextArea impactDescription;
    @FXML
    private Spinner<Integer> noOfAffectedIndividuals;
    @FXML
    private ImageView logoImage;
    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        districtSelection.getItems().addAll("Ampara", "Anuradhapura", "Badulla", "Batticaloa", "Colombo", "Galle", "Gampaha", "Hambantota", "Jaffna", "Kalutara", "Kandy", "Kegalle", "Kilinochchi", "Kurunegala", "Mannar", "Matale", "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya","Polonnaruwa", "Puttalam", "Ratnapura", "Trincomalee", "Vavuniyawa");
        provinceSelection.getItems().addAll("Central", "Eastern", "North Central", "Northern", "North Western", "Sabaragamuwa", "Southern", "Uva", "Western");
        disasterType.getItems().addAll("Animal Attack", "Extreme Wind Events", "Heavy Rain", "Fire", "Landslides", "Lightning", "Floods", "Drought", "Cyclone");
        urgencyLevel.getItems().addAll("Low", "Medium", "Critical");
        noOfAffectedIndividuals.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100));
    }

    @FXML
    public void SubmitReport() {
        String fullName = FullName.getText();
        String contactNumber = ContactNumber.getText();
        String emailAddress = EmailAddress.getText();
        String nicNumber = NIC.getText();
        String streetAddress = Location.getText();
        String gramaNiladhariDivision = GramaNiladhariDivision.getText();
        String district = districtSelection.getValue();
        String province = provinceSelection.getValue();
        String disaster = disasterType.getValue();
        String date = (incidentDate.getValue() != null) ? this.incidentDate.getValue().toString() : null;
        String time = incidentTime.getText();
        String impactDescriptionText = impactDescription.getText();
        int noOfAffected = noOfAffectedIndividuals.getValue();
        String urgencyLevelSelection = urgencyLevel.getValue();

        if (fullName.isEmpty() || contactNumber.isEmpty()|| nicNumber.isEmpty() || streetAddress.isEmpty() || district == null || province == null || disaster == null || date == null || time == null ||urgencyLevelSelection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the mandatory fields", ButtonType.OK);
            alert.show();
            return;
        }

        boolean isSuccess = reportService.submitDisasterReport(fullName, contactNumber, emailAddress, nicNumber, streetAddress, gramaNiladhariDivision, district, province, disaster, date, time, impactDescriptionText,noOfAffected, urgencyLevelSelection);

        if (isSuccess) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Report Submitted", ButtonType.OK);
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to submit.", ButtonType.OK);
            alert.show();
        }
    }
    @FXML
    private void goToSelectionPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignInPage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
