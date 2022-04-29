package com.example.dbts;

public class FirebaseDataCollection {

    private String NAME;
    private int STUDENTID;
    private String EMAIL;
    private String PASSWORD;

    public FirebaseDataCollection() {
    }

    public String getNAME() {
        return NAME;
    }

    public FirebaseDataCollection(String NAME, int STUDENTID, String EMAIL, String PASSWORD) {
        this.NAME = NAME;
        this.STUDENTID = STUDENTID;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getSTUDENTID() {
        return STUDENTID;
    }

    public void setSTUDENTID(int STUDENTID) {
        this.STUDENTID = STUDENTID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
