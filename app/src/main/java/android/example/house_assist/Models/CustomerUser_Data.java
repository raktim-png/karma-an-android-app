package android.example.house_assist.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerUser_Data implements Parcelable {
    private String name;
    private String mobile;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String locality;
    private String pincode;
    private String State;
    private String latitude ;
    private String longitude;
    private String Full_Address;
    private String customer_id;
    private String service_provider_id;
    private String user_type;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFull_Address() {
        return Full_Address;
    }

    public void setFull_Address(String full_Address) {
        Full_Address = full_Address;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
        this.email = email;
        return email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return State;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
        return customer_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getService_provider_id() {
        return service_provider_id;
    }

    public void setService_provider_id(String service_provider_id) {
        this.service_provider_id = service_provider_id;
    }

    public void setState(String state) {
        State = state;



    }

    public CustomerUser_Data() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.email);
        dest.writeString(this.address1);
        dest.writeString(this.address2);
        dest.writeString(this.locality);
        dest.writeString(this.pincode);
        dest.writeString(this.State);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.Full_Address);
    }

    protected CustomerUser_Data(Parcel in) {
        this.name = in.readString();
        this.mobile = in.readString();
        this.email = in.readString();
        this.address1 = in.readString();
        this.address2 = in.readString();
        this.locality = in.readString();
        this.pincode = in.readString();
        this.State = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.Full_Address = in.readString();
    }

    public CustomerUser_Data(String user_type) {
        this.user_type = user_type;
    }

    public static final Creator<CustomerUser_Data> CREATOR = new Creator<CustomerUser_Data>() {
        @Override
        public CustomerUser_Data createFromParcel(Parcel source) {
            return new CustomerUser_Data(source);
        }

        @Override
        public CustomerUser_Data[] newArray(int size) {
            return new CustomerUser_Data[size];
        }
    };
}