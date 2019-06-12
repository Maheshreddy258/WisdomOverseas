package com.example.mahesh.wisdomoverseas.models.Newresponses;

public class IncomingResponse {

    private  String number ,date,calltype,dir;



    public IncomingResponse(String number, String date, String calltype,String dir) {
        this.number = number;
        this.date = date;
        this.calltype = calltype;
        this.dir =dir;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }
}
