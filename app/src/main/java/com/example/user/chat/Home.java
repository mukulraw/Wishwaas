package com.example.user.chat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.chat.Mobilelogin.MoblileBean;
import com.example.user.chat.updateuser.updateBean;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Home extends AppCompatActivity {

    Button chat;
    TextView des;

    ImageView logout;

    TextView dob, time;
    EditText name, pdob;

    String date = "";

    ProgressBar bar;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = pref.edit();

        des = findViewById(R.id.texview);
        bar = findViewById(R.id.progress);
        chat = findViewById(R.id.chat);
        logout = findViewById(R.id.logout);
        time = findViewById(R.id.time);
        dob = findViewById(R.id.dob);
        pdob = findViewById(R.id.pdob);
        name = findViewById(R.id.name);


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(Home.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.datedialog);
                dialog.setCancelable(true);
                dialog.show();

                final DatePicker picker = dialog.findViewById(R.id.picker);
                final Button ok = dialog.findViewById(R.id.ok);
                final Button cancel = dialog.findViewById(R.id.cancel);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String day = String.valueOf(picker.getDayOfMonth());
                        String month = String.valueOf(picker.getMonth() + 1);
                        String year = String.valueOf(picker.getYear());

                        date = day + "-" + month + "-" + year;

                        dob.setText(date);

                        dialog.dismiss();


                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });

                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final Dialog dialog = new Dialog(Home.this);
                        dialog.setContentView(R.layout.dialog);
                        dialog.setCancelable(true);
                        dialog.show();

                        final TimePicker picker = (TimePicker) dialog.findViewById(R.id.picker);
                        final Button ok = (Button) dialog.findViewById(R.id.ok);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Integer hour = picker.getCurrentHour();
                                Integer min = picker.getCurrentMinute();

                                String time1 = hour + " : " + min;


                                String format = "";

                                if (hour == 0) {
                                    hour += 12;
                                    format = "AM";
                                } else if (hour == 12) {
                                    format = "PM";
                                } else if (hour > 12) {
                                    hour -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }

                                time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                                        .append(" ").append(format));


                                dialog.dismiss();


                            }
                        });


                    }
                });


            }
        });

        Bean b = (Bean)getApplicationContext();

        dob.setText(b.dob);
        pdob.setText(b.pdob);
        time.setText(b.time);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Home.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);


                edit.remove("moblie");
                edit.remove("password");
                edit.apply();

                Bean b = (Bean) getApplicationContext();

                b.user_id = "";
                b.usernmae = "";

                startActivity(i);
                finish();

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p = pdob.getText().toString();
                
                
                if (dob.length()>0){
                    
                    
                    if (pdob.length()>0){
                        
                        
                        if (time.length()>0){

                            bar.setVisibility(View.VISIBLE);

                            final Bean b = (Bean)getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApi cr = retrofit.create(AllApi.class);

                            Call<updateBean> call = cr.update(b.user_id , dob.getText().toString() , p ,time.getText().toString());
                            call.enqueue(new Callback<updateBean>() {
                                @Override
                                public void onResponse(Call<updateBean> call, Response<updateBean> response) {

                                    Toast.makeText(Home.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(Home.this, ChatScreen.class);
                                    startActivity(i);

                                    bar.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<updateBean> call, Throwable t) {

                                    bar.setVisibility(View.GONE);


                                }
                            });


                        }
                        else {
                            Toast.makeText(Home.this, "Invalid Time of Birth", Toast.LENGTH_SHORT).show();
                        }
                        
                    }else {
                        Toast.makeText(Home.this, "Invalid Place of Birth", Toast.LENGTH_SHORT).show();
                    }
                    
                }else {
                    Toast.makeText(Home.this, "Invalid Date of Birth", Toast.LENGTH_SHORT).show();
                }





            }
        });
    }
}
