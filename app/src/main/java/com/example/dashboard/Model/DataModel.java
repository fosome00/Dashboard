package com.example.dashboard.Model;

public class DataModel {

    private String outletName, type, size, city, address, pincode;

    public DataModel() {
    }

    public DataModel(String outletName, String type, String size, String city, String address, String pincode) {
        this.outletName = outletName;
        this.type = type;
        this.size = size;
        this.city = city;
        this.address = address;
        this.pincode = pincode;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
