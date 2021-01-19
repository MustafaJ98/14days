package com.app.a14days;

/**
 *  Encapsulation for contact to be added on scanning QR and shown in Contact Fragment
 */
public class Contact {
    private String contactName;
    private String contactDate;
    private boolean covid_positive = false;

    public Contact() {
    }


    public Contact(String contactName, String contactDate, boolean covid_positive) {
        this.contactName = contactName;
        this.contactDate = contactDate;
        this.covid_positive = covid_positive;
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

    public boolean isCovid_positive() {
        return covid_positive;
    }

    public void setCovid_positive(boolean covid_positive) {
        this.covid_positive = covid_positive;
    }
}
