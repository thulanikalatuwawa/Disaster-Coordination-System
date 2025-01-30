package models;


public class ReportModel {
    private int reportId;
    private String fullName;
    private String contactNumber;
    private String emailAddress;
    private String nicNumber;
    private String location;
    private String gramaNiladhariDivision;
    private String district;
    private String province;
    private String disasterType;
    private String incidentDate;
    private String incidentTime;
    private String impactDescription;
    private int noOfAffectedIndividuals;
    private String urgencyLevel;
    private String status;

    // Constructor to fetch details from the database
    public ReportModel(int reportId, String fullName, String contactNumber, String emailAddress, String nicNumber, String location,
                       String gramaNiladhariDivision, String district, String province, String disasterType,
                       String incidentDate, String incidentTime, String impactDescription, int noOfAffectedIndividuals, String urgencyLevel, String status) {
        this.reportId = reportId;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.nicNumber = nicNumber;
        this.location = location;
        this.gramaNiladhariDivision = gramaNiladhariDivision;
        this.district = district;
        this.province = province;
        this.disasterType = disasterType;
        this.incidentDate = incidentDate;
        this.incidentTime = incidentTime;
        this.impactDescription = impactDescription;
        this.noOfAffectedIndividuals = noOfAffectedIndividuals;
        this.urgencyLevel = urgencyLevel;
        this.status=status;
    }

    // Constructor to submit the report
    public ReportModel(String fullName, String contactNumber, String emailAddress, String nicNumber, String location,
                       String gramaNiladhariDivision, String district, String province, String disasterType,
                       String incidentDate, String incidentTime, String impactDescription, int noOfAffectedIndividuals, String urgencyLevel) {
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.nicNumber = nicNumber;
        this.location = location;
        this.gramaNiladhariDivision = gramaNiladhariDivision;
        this.district = district;
        this.province = province;
        this.disasterType = disasterType;
        this.incidentDate = incidentDate;
        this.incidentTime = incidentTime;
        this.impactDescription = impactDescription;
        this.noOfAffectedIndividuals = noOfAffectedIndividuals;
        this.urgencyLevel = urgencyLevel;
    }

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getNicNumber() {
        return nicNumber;
    }
    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getGramaNiladhariDivision() {
        return gramaNiladhariDivision;
    }
    public void setGramaNiladhariDivision(String gramaNiladhariDivision) {
        this.gramaNiladhariDivision = gramaNiladhariDivision;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getDisasterType() {
        return disasterType;
    }
    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }
    public String getIncidentDate() {
        return incidentDate;
    }
    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }
    public String getImpactDescription() {
        return impactDescription;
    }
    public void setImpactDescription(String impactDescription) {
        this.impactDescription = impactDescription;
    }
    public int getNoOfAffectedIndividuals() {
        return noOfAffectedIndividuals;
    }
    public void setNoOfAffectedIndividuals(int noOfAffectedIndividuals) {
        this.noOfAffectedIndividuals = noOfAffectedIndividuals;
    }
    public String getUrgencyLevel() {
        return urgencyLevel;
    }
    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(String incidentTime) {
        this.incidentTime = incidentTime;
    }
}
