package praktik;

import javafx.beans.property.SimpleStringProperty;

public class Company {
    private final SimpleStringProperty companyName;
    private final SimpleStringProperty activities;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty mail;
    private final SimpleStringProperty website;
    private final SimpleStringProperty state;

    public Company(String companyName, String activities, String phone, String mail, String website, String state) {
        this.companyName = new SimpleStringProperty(companyName);
        this.activities = new SimpleStringProperty(activities);
        this.phone = new SimpleStringProperty(phone);
        this.mail = new SimpleStringProperty(mail);
        this.website = new SimpleStringProperty(website);
        this.state = new SimpleStringProperty(state);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getActivities() {
        return activities.get();
    }

    public SimpleStringProperty activitiesProperty() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities.set(activities);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getMail() {
        return mail.get();
    }

    public SimpleStringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public String getWebsite() {
        return website.get();
    }

    public SimpleStringProperty websiteProperty() {
        return website;
    }

    public void setWebsite(String website) {
        this.website.set(website);
    }

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }
}
