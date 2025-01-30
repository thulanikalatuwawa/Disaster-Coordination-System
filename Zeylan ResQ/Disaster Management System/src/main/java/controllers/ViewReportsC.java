package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.ReportModel;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;


public class ViewReportsC {
    @FXML
    private Button dashboardBtn;
    @FXML
    private ComboBox<String> filterByProvince, filterByDistrict, filterByDisaster, filterByUrgency;
    @FXML
    private TableView<ReportModel> disasterReportsTable;
    @FXML
    private TableColumn<ReportModel, String> reporterName, contact, email, nic, locationDetails, gramaNiladhariDivision, province, district, incidentDate, incidentTime, urgencyLevel ,disasterInformation, impactDescription, status;
    @FXML
    private TableColumn<ReportModel, Integer> reportID,noOfAffected;
    @FXML
    private ImageView logoImage;

    private ObservableList<ReportModel> disasterReportsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        setUpFilters();
        fillReportsDbTable();
        applyFilters();

        reportID.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        reporterName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        nic.setCellValueFactory(new PropertyValueFactory<>("nicNumber"));
        locationDetails.setCellValueFactory(new PropertyValueFactory<>("location"));
        gramaNiladhariDivision.setCellValueFactory(new PropertyValueFactory<>("gramaNiladhariDivision"));
        province.setCellValueFactory(new PropertyValueFactory<>("province"));
        district.setCellValueFactory(new PropertyValueFactory<>("district"));
        disasterInformation.setCellValueFactory(new PropertyValueFactory<>("disasterType"));
        incidentDate.setCellValueFactory(new PropertyValueFactory<>("incidentDate"));
        incidentTime.setCellValueFactory(new PropertyValueFactory<>("incidentTime"));
        urgencyLevel.setCellValueFactory(new PropertyValueFactory<>("urgencyLevel"));
        impactDescription.setCellValueFactory(new PropertyValueFactory<>("impactDescription"));
        noOfAffected.setCellValueFactory(new PropertyValueFactory<>("noOfAffectedIndividuals"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setUpFilters() {
        filterByProvince.getItems().addAll("Central", "Eastern", "North Central", "Northern", "North Western", "Sabaragamuwa", "Southern", "Uva", "Western");
        filterByDistrict.getItems().addAll("Ampara", "Anuradhapura", "Badulla", "Batticaloa", "Colombo", "Galle", "Gampaha", "Hambantota", "Jaffna", "Kalutara", "Kandy", "Kegalle", "Kilinochchi", "Kurunegala", "Mannar", "Matale", "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya","Polonnaruwa", "Puttalam", "Ratnapura", "Trincomalee", "Vavuniyawa");
        filterByDisaster.getItems().addAll("Animal Attack", "Extreme Wind Events", "Fire", "Landslides", "Lightning", "Floods", "Drought", "Cyclone");
        filterByUrgency.getItems().addAll("Low", "Medium", "Critical");
    }

    private void fillReportsDbTable() {
        String query = "SELECT * FROM disaster_reports";

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zeylan resq", "root", "")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            disasterReportsList.clear();

            while (resultSet.next()) {
                int reportId = resultSet.getInt("report_id");
                String fullName = resultSet.getString("full_name");
                String contactNumber = resultSet.getString("contact_number");
                String email = resultSet.getString("email");
                String nic = resultSet.getString("nic_number");
                String location = resultSet.getString("location");
                String gramaNiladhariDivision = resultSet.getString("grama_niladhari_division");
                String province = resultSet.getString("province");
                String district = resultSet.getString("district");
                String disasterType = resultSet.getString("disaster_type");
                String incidentDate = resultSet.getString("incident_date");
                String incidentTime = resultSet.getString("incident_time");
                String urgencyLevel = resultSet.getString("urgency_level");
                String impactDescription = resultSet.getString("impact_description");
                int noOfAffected = resultSet.getInt("no_of_affected");
                String status = resultSet.getString("status");

                System.out.println("Retrieved: " + reportId + ", " + fullName + ", " + contactNumber);

                ReportModel report = new ReportModel(reportId, fullName,contactNumber, email, nic, location, gramaNiladhariDivision, district, province, disasterType, incidentDate, incidentTime,impactDescription,noOfAffected, urgencyLevel,status);
                disasterReportsList.add(report);
            }
            disasterReportsTable.setItems(disasterReportsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToDashboard() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminDashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) dashboardBtn.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.getScene().getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void applyFilters() {
        ObservableList<ReportModel> filteredList = FXCollections.observableArrayList(disasterReportsList);

        String selectedProvince = filterByProvince.getValue();
        String selectedDistrict = filterByDistrict.getValue();
        String selectedDisasterType = filterByDisaster.getValue();
        String selectedUrgency = filterByUrgency.getValue();

        filteredList.removeIf(report -> {
            if (selectedProvince != null && !selectedProvince.isEmpty() && !report.getProvince().contains(selectedProvince)) {
                return true;
            }
            if (selectedDistrict != null && !selectedDistrict.isEmpty() && !report.getDistrict().contains(selectedDistrict)) {
                return true;
            }
            if (selectedDisasterType != null && !selectedDisasterType.isEmpty() && !report.getDisasterType().equalsIgnoreCase(selectedDisasterType)) {
                return true;
            }
            if (selectedUrgency != null && !selectedUrgency.isEmpty() &&
                    !report.getUrgencyLevel().equalsIgnoreCase(selectedUrgency)) {
                return true;
            }
            return false;
        });
        disasterReportsTable.setItems(filteredList);
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

    public void updateStatus(ActionEvent actionEvent) {
        ReportModel selectedReport = disasterReportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zeylan resq", "root", "")) {
                String updateQuery = "UPDATE disaster_reports SET status = ? WHERE report_id = ?";
                PreparedStatement statement = connection.prepareStatement(updateQuery);

                String newStatus = updateForStatus();
                if (newStatus != null && !newStatus.isEmpty()) {
                    selectedReport.setStatus(newStatus); // Update locally
                    statement.setString(1, newStatus); // Update in the database
                    statement.setInt(2, selectedReport.getReportId());
                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        disasterReportsTable.refresh(); // Refresh the table
                        System.out.println("Status updated successfully.");
                    } else {
                        System.out.println("Failed to update status.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a report to update.");
        }
    }

    private String updateForStatus() {
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Pending", "In Progress", "Resolved");

        ComboBox<String> comboBox = new ComboBox<>(statusOptions);
        comboBox.setValue("Pending");

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Status");
        dialog.setHeaderText("You are updating the status as: ");
        dialog.getDialogPane().setContent(comboBox);

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return comboBox.getValue();
            }
            return null;
        });
        return dialog.showAndWait().orElse(null);
    }

    public void resetFilters() {
        filterByProvince.setValue(null);
        filterByDistrict.setValue(null);
        filterByDisaster.setValue(null);
        filterByUrgency.setValue(null);

        disasterReportsTable.setItems(FXCollections.observableArrayList(disasterReportsList));
    }
}
