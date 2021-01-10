package com.app.a14days;

/**
 *  Encapsulation for contact to be added on scanning QR and shown in Contact Fragment
 */
public class Contact {
    private String contactName;
    private String contactDate;

    public Contact() {
    }

    public Contact(String contactName, String contactDate) {
        this.contactName = contactName;
        this.contactDate = contactDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactDate() {
        return contactDate;
    }

    public void setContactDate(String contactDate) {
        this.contactDate = contactDate;
    }
}
