package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class AdminDashboardC {
    @FXML
    private Button viewReportsBtn;
    @FXML
    private Label totalReportsL, pendingReports, inProgressReports, resolvedReports;
    @FXML
    private PieChart disasterBreakdown;


    @FXML
    public void initialize() {
        updateStatistics();
        setUpDisasterBreakdown();
    }

    private void setUpDisasterBreakdown() {
        disasterBreakdown.getData().clear();
        try{
            Map<String, Integer> disasterData = fetchDisasterData();
            for (Map.Entry<String, Integer> entry : disasterData.entrySet()) {
                disasterBreakdown.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("Failed to fetch data: "+ e.getMessage());
        }
    }

    private Map<String, Integer> fetchDisasterData() throws Exception {
        Map<String, Integer> disasterData = new HashMap<>();
        String query = "SELECT disaster_type, COUNT(*) AS count FROM disaster_reports GROUP BY disaster_type";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zeylan resq", "root", "");
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String disasterType = resultSet.getString("disaster_type");
                int count = resultSet.getInt("count");
                disasterData.put(disasterType, count);
            }
        }
        return disasterData;
    }


    private void updateStatistics() {
        totalReportsL.setText(String.valueOf(getReportCount(null)));
        pendingReports.setText(String.valueOf(getReportCount("Pending")));
        resolvedReports.setText(String.valueOf(getReportCount("Resolved")));
        inProgressReports.setText(String.valueOf(getReportCount("In Progress")));

    }

    private int getReportCount(String status) {
        int count = 0;
        String query;

        if (status == null) {
            query = "SELECT COUNT(*) AS count FROM disaster_reports";
        } else {
            query = "SELECT COUNT(*) AS count FROM disaster_reports WHERE status = ?";
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zeylan resq", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (status != null) {
                statement.setString(1, status);
            }

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }


    public void goToViewReports() {
        try {
            // Load the ViewReports FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewReports.fxml"));
            Parent root = loader.load();

            // Get the current stage using any UI element that is part of the scene (like the button)
            Stage stage = (Stage) viewReportsBtn.getScene().getWindow(); // Assuming viewReportsButton is your Button ID

            // Set the new root in the existing scene
            stage.getScene().setRoot(root);

            // Reapply the stylesheet to ensure styles are retained
            stage.getScene().getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
