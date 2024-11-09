package edu.pims.thriftstore.Adapter;

public class ContactUs {

    private String phone;
    private String email;
    private String message;

    // No-argument constructor required for Firestore
    public ContactUs() {
    }

    // Constructor to initialize the fields
    public ContactUs(String phone, String email, String message) {
        this.phone = phone;
        this.email = email;
        this.message = message;
    }

    // Getters for the fields
    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
