package com.example.den.amogettrans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;



public class Statuses {
    @SerializedName("response")
    @Expose
    Statuses response;
    @SerializedName("account")
    @Expose
    Account account;

   class Account<Object>{
       @SerializedName("leads_statuses")
       @Expose
      List<Status_>  leads_statuses;
   }


    class Status_ {
        @SerializedName("id")
        @Expose
        String id;
        @SerializedName("name")
        @Expose
        String name;
        @SerializedName("color")
        @Expose
        String color;

    }


}
