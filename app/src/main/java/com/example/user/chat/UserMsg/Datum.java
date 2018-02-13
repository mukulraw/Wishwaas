package com.example.user.chat.UserMsg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 12-02-2018.
 */

public class Datum {


    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("send_by")
    @Expose
    private String sendBy;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("creaated_date")
    @Expose
    private String creaatedDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreaatedDate() {
        return creaatedDate;
    }

    public void setCreaatedDate(String creaatedDate) {
        this.creaatedDate = creaatedDate;
    }

}
