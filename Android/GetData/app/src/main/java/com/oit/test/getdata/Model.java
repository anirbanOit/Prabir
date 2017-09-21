package com.oit.test.getdata;

/**
 * Created by OPTLPT049 on 9/6/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("dept")
    @Expose
    private String dept;

    public Model(String firstName, String lastName, String gender, String dateOfBirth, String department, String image){

    }

    public Model(String firstName, String lastName, String gender, String dateOfBirth, String department, String image, Model model) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.gender=gender;
        this.dob=dateOfBirth;
        this.dept=department;
        this.photo=image;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

}