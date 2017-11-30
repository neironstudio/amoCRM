package com.example.den.amogettrans;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView listView;
    private ProgressDialog pDialog;
    private List<Transaction.Leads> transList = new ArrayList<>();
    private CustomListAdapter adapter;
    Retrofit retrofit;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new CustomListAdapter(this, transList);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);

        pDialog.setMessage("Loading...");
        pDialog.show();
        autorities();
        getData();
        getStatuses();
        getStatuses();

    }

    private void autorities() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient()

                .newBuilder()

                .addInterceptor
                        (new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                                Request originalRequest = chain.request();

                                Request.Builder builder = originalRequest

                                        .newBuilder()
                                        .header("Authorization",
                                                Credentials.basic("ksp2000@yandex.ru", "xHiLX4cT"));

                                Request newRequest = builder.build();
                                return chain.proceed(newRequest);
                            }
                        })
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://new5a1c1bb1961ea.amocrm.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
       apiService = retrofit.create(APIService.class);
    }

    private void getStatuses() {

        Call<Statuses> statlist = apiService.getStatuses();
        statlist.enqueue(new Callback<Statuses>() {
            @Override
            public void onResponse(Call<Statuses> calllist, Response<Statuses> response) {
                hidePDialog();
                try {
                    if (response.isSuccess()) {
                        ArrayList arrayStatus = (ArrayList) response.body().response.account.leads_statuses;
                        for (Object item : transList) {
                            for (Object itemStat : arrayStatus) {
                                itemStat.toString();
                                if (((Transaction.Leads) item).status_id.toString().equals(((Statuses.Status_) itemStat).id.toString())) {
                                    ((Transaction.Leads) item).status_id = ((Statuses.Status_) itemStat).name.toString();
                                }
                                adapter.notifyDataSetChanged();
                            }


                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed! : " + response.errorBody().string(), Toast.LENGTH_LONG).show();


                    }
                } catch (IOException e) {
                    Log.e("LOG_TAG", "IOException:" + e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<Statuses> call, Throwable t) {
                hidePDialog();
                if (t != null) {
                    Toast.makeText(getApplicationContext(), "Failed! OnFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void getData() {



        Call<Transaction> calllist = apiService.getTransactionService();
        calllist.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> calllist, Response<Transaction> response) {
                hidePDialog();
                try {
                    if (response.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        transList.clear();
                        for (Transaction.Leads item : response.body().response.leads) {
                            transList.add(item);
                        }
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed! : " + response.errorBody().string(), Toast.LENGTH_LONG).show();


                    }
                } catch (IOException e) {
                    Log.e("LOG_TAG", "IOException:" + e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                hidePDialog();
                if (t != null) {
                    Toast.makeText(getApplicationContext(), "Failed! OnFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
}
