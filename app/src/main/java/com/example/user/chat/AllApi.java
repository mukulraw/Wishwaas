package com.example.user.chat;

import com.example.user.chat.Forgot.ForgotBean;
import com.example.user.chat.Mobilelogin.MoblileBean;
import com.example.user.chat.SendMsg.Sendbean;
import com.example.user.chat.SignUp.SignBean;
import com.example.user.chat.UserMsg.UserBean;
import com.example.user.chat.updateuser.updateBean;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by USER on 12-02-2018.
 */

public interface AllApi {


    @Multipart
    @POST("wishapp/api/sign_up.php")
    Call<SignBean>call
            (@Part("username")String user ,
             @Part("mobile")String mobile ,
             @Part("password")String password);



    @Multipart
    @POST("wishapp/api/forget-password.php")
    Call<ForgotBean>forgot
            (@Part("mobile")String user


             );


    @Multipart
    @POST("wishapp/api/user_send_msg.php")
    Call<Sendbean>send
            (@Part("user_id")String user ,
             @Part("reply_message")String mobile
            );


    @Multipart
    @POST("wishapp/api/mobile_signin.php")
    Call<MoblileBean>mobile
            (@Part("mobile")String user ,
             @Part("password")String mobile
            );

    @Multipart
    @POST("wishapp/api/user_msg_list.php")
    Call<UserBean>list
            (@Part("user_id")String user
            );


    @Multipart
    @POST("wishapp/api/update_user_birthday.php")
    Call<updateBean>update
            (@Part("user_id")String t ,
            @Part("birth_date")String tt ,
            @Part("birth_place")String gg ,
            @Part("birth_time")String ff
            );

}
