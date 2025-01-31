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
        updateReportStatus();
        setUpDisasterBreakdown();
    }

    //Pie chart
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

    // fetch data from the database for the dashboard
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

    private void updateReportStatus() {
        totalReportsL.setText(String.valueOf(getReportCount(null)));
        pendingReports.setText(String.valueOf(getReportCount("Pending")));
        resolvedReports.setText(String.valueOf(getReportCount("Resolved")));
        inProgressReports.setText(String.valueOf(getReportCount("In Progress")));
    }

    // number of report requests from the database for the dashboard
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

    // Navigated to reports table
    public void goToViewReports() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewReports.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) viewReportsBtn.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
