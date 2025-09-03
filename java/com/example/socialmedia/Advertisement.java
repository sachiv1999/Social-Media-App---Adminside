package com.example.socialmedia;

public class Advertisement {
    private String advertiserName;
    private String companyName;
    private String companyAddress;
    private String adName;
    //private int payment;

    // Empty constructor for Firebase
    public Advertisement(String adId, String advertiserName, String companyName, String companyAddress, String adName) {
        // unique Firestore document ID
        this.advertiserName = advertiserName;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.adName = adName;
//        this.payment = payment;
    }

    // Getters
    public String getAdvertiserName() {
        return advertiserName; }
    public String getCompanyName() {
        return companyName; }
    public String getCompanyAddress() {
        return companyAddress; }
    public String getAdName() {
        return adName; }
//    public int getPayment() {
//        return payment; }

    // Setters

    public void setAdvertiserName(String advertiserName)
    { this.advertiserName = advertiserName; }
    public void setCompanyName(String companyName)
    { this.companyName = companyName; }
    public void setCompanyAddress(String companyAddress)
    { this.companyAddress = companyAddress; }
    public void setAdName(String adName)
    { this.adName = adName; }
//    public void setPayment(int payment)
//    { this.payment = payment; }

    public void setAdId(String id) {
    }
}
