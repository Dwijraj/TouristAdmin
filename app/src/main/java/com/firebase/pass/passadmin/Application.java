package com.firebase.pass.passadmin;

/**
 * Created by 1405214 on 22-01-2017.
 */


public class Application {

    public  String Name;
    public  String Address;
    public  String Mobile;
    public  String ID_No;
    public  String Purpose;
    public  String ApplicantPhoto;
    public   String ApplicantScanId;
    public   String Uid;
    public   String ApplicationStatus;
    public   String DateOfBirth;
    public  String DateOfJourney;
    public  String Barcode_Image;
    public String ID_Source;
    public String Carnumber;
    public String Drivername;

    public Application(String name, String address, String mobile, String ID_No, String purpose, String applicantPhoto, String applicantScanId, String uid, String applicationStatus, String dateOfBirth, String dateOfJourney,String barcode_Image,String id_source,String Carnumber,String Drivername) {
        Name = name;
        Address = address;
        Mobile = mobile;
        Barcode_Image=barcode_Image;
        this.ID_No = ID_No;
        Purpose = purpose;
        ApplicantPhoto = applicantPhoto;
        ApplicantScanId = applicantScanId;
        Uid = uid;
        this.Carnumber=Carnumber;
        this.Drivername=Drivername;
        ID_Source=id_source;
        ApplicationStatus = applicationStatus;
        DateOfBirth = dateOfBirth;
        DateOfJourney = dateOfJourney;
    }

    public Application() {              //Important or else the app crashes firebase requires a datamodel class with default constructor
    }
}