package com.example.servicenovigrad.data.Class;

public class NovApplication {

    private String firstName;
    private String lastName;
    private Address address;
    private NovLocalDate doB;
    private String Serviceid;
    private String Usersid;
    private String license;
    private String applicationID;
    private boolean approved;

    public NovApplication(String firstName, String lastName, Address address, NovLocalDate doB, String applicationID,String Serviceid, String Usersid){
        this.firstName = firstName;
        this.lastName =lastName;
        this.address = address;
        this.doB = doB;
        this.Serviceid = Serviceid;
        this.Usersid = Usersid;
        this.applicationID = applicationID;
    }

    public NovApplication(){

    }

    public NovApplication(String firstName, String lastName, Address address, NovLocalDate doB, String applicationID, String Serviceid, String license, String Usersid){
        this.firstName = firstName;
        this.lastName =lastName;
        this.address = address;
        this.doB = doB;
        this.Serviceid = Serviceid;
        this.license = license;
        this.Usersid = Usersid;
        this.applicationID = applicationID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public NovLocalDate getDoB() {
        return doB;
    }

    public void setDoB(NovLocalDate doB) {
        this.doB = doB;
    }

    public String getServiceid() {
        return Serviceid;
    }

    public void setServiceid(String serviceid) {
        Serviceid = serviceid;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getUsersid() {
        return Usersid;
    }

    public void setUsersid(String usersid) {
        Usersid = usersid;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }
}
