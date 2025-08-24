package com.example.emailsystem.model;

public class Employee {

    private String employeeName;
    private String empId;
    private String email;
    private String department;
    private String doj;
    private String newDesignation;
    private String effectiveDate;
    private String pmsRating;
    private String newCtc;
    private String letterType;

    // === Letter Type Constants ===
    public static final String LETTER_TYPE_NO_INCREMENT = "NO_INCREMENT";
    public static final String LETTER_TYPE_APPRAISAL = "APPRAISAL";
    public static final String LETTER_TYPE_PARTIAL_INCREMENT = "PARTIAL_INCREMENT";

    // Getters and Setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getNewDesignation() {
        return newDesignation;
    }

    public void setNewDesignation(String newDesignation) {
        this.newDesignation = newDesignation;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getPmsRating() {
        return pmsRating;
    }

    public void setPmsRating(String pmsRating) {
        this.pmsRating = pmsRating;
    }

    public String getNewCtc() {
        return newCtc;
    }

    public void setNewCtc(String newCtc) {
        this.newCtc = newCtc;
    }

    public String getLetterType() {
        return letterType;
    }

    public void setLetterType(String letterType) {
        this.letterType = letterType;
    }
}
