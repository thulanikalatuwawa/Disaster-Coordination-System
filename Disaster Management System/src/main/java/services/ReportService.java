package services;

import app.DatabaseConnector;
import models.ReportModel;
import java.sql.*;

public class ReportService {
    // submit a disaster report into the database
    public boolean submitDisasterReport(String fullName, String contactNumber, String emailAddress, String nicNumber, String location, String gramaNiladhariDivision, String district, String province, String disasterType, String incidentDate, String incidentTime, String impactDescription, int noOfAffectedIndividuals, String urgencyLevel) {
        ReportModel report = new ReportModel(fullName, contactNumber, emailAddress, nicNumber, location, gramaNiladhariDivision,district, province, disasterType, incidentDate, incidentTime, impactDescription, noOfAffectedIndividuals, urgencyLevel);
        String query = "INSERT INTO disaster_reports (full_name, contact_number, email, nic_number, location, grama_niladhari_division, " +
                "district, province, disaster_type, incident_date, incident_time, impact_description, no_of_affected, urgency_level) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection connection = DatabaseConnector.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, report.getFullName());
            statement.setString(2, report.getContactNumber());
            statement.setString(3, report.getEmailAddress());
            statement.setString(4, report.getNicNumber());
            statement.setString(5, report.getLocation());
            statement.setString(6, report.getGramaNiladhariDivision());
            statement.setString(7, report.getDistrict());
            statement.setString(8, report.getProvince());
            statement.setString(9, report.getDisasterType());
            statement.setString(10, report.getIncidentDate());
            statement.setString(11, report.getIncidentTime());
            statement.setString(12, report.getImpactDescription());
            statement.setInt(13, report.getNoOfAffectedIndividuals());
            statement.setString(14, report.getUrgencyLevel());

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
