package com.example.den.amogettrans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Transaction {


    @SerializedName("response")
    @Expose
    Transaction response;
    @SerializedName("leads")
    @Expose
    // Leads leads;
    List<Leads> leads;

    class Leads {
        @SerializedName("name")
        @Expose
        String name;
        @SerializedName("date_create")
        @Expose
        String date_create;
        @SerializedName("price")
        @Expose
        String price;


        @SerializedName("status_id")
        @Expose
        String status_id;
    }
}