package org.example.demo;

import java.util.Date;

public class Card {
    private int id;
    private String front;
    private String back;
    private String flag;
    private Date endDate;
    private int daysJump;
    private int deskId;

    public Card(int id, String front, String back, String flag, Date endDate, int daysJump, int deskId) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.flag = flag;
        this.endDate = endDate;
        this.daysJump = daysJump;
        this.deskId = deskId;
    }

    public int getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public String getFlag() {
        return flag;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getDaysJump() {
        return daysJump;
    }

    public int getDeskId() {
        return deskId;
    }




    public void setId(int id) {
        this.id = id;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDaysJump(int daysJump) {
        this.daysJump = daysJump;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }
}
