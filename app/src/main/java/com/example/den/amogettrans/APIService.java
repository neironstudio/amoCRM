package com.example.den.amogettrans;


import retrofit2.Call;
import retrofit2.http.GET;


public interface APIService {

    @GET("private/api/v2/json/leads")

    Call<Transaction> getTransactionService(
    );

   @GET("private/api/v2/json/accounts/current/")
    Call<Statuses> getStatuses();

}
