package com.example.volleyatoz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText phone;
    EditText password;
    Button login;

    String username,passwordd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone=findViewById(R.id.editText_one);
        password=findViewById(R.id.editText_two);
        login=findViewById(R.id.login_id);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                username = phone.getText().toString();
                passwordd = password.getText().toString();

               StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://demo.olivineltd.com/primary_attendance/api/school/login",
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {

                               if(response.equals("Invalid Credentials"))
                               {
                                   Toast.makeText(LoginActivity.this, "MOira ja", Toast.LENGTH_SHORT).show();
                               }
                               else
                               {

                                   try {
                                       JSONObject jsonObject = new JSONObject(response);
                                       String name__en = jsonObject.getString("school_name_en");
                                       String name__bn = jsonObject.getString("school_name_bn");
                                       String phone = jsonObject.getString("school_teacher_mobile");
                                       String upazila__id = jsonObject.getString("school_upazila_id");
                                       String teacher__name = jsonObject.getString("school_teacher_name");
                                       String school__eiin = jsonObject.getString("school_eiin_no");
                                       String school__id = jsonObject.getString("school_id");


                                       SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                                       SharedPreferences.Editor editor=sharedPreferences.edit();
                                       editor.putString("name_key", name__bn);
                                       editor.putString("phone_key",phone);
                                       editor.apply();


                                       //Toast.makeText(LoginActivity.this, "data store succefull", Toast.LENGTH_SHORT).show();
                                       Toast.makeText(LoginActivity.this, name__bn, Toast.LENGTH_SHORT).show();
                                       Intent intent=new Intent(LoginActivity.this,profileActivity.class);
                                       startActivity(intent);


                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }

                           }
                       }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               }){
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {

                       Map<String,String>prams=new HashMap<>();
                       prams.put("mobile_no",username);
                       prams.put("password",passwordd);
                       return prams;
                   }
               };

            Volley.newRequestQueue(LoginActivity.this).add(stringRequest);

            }
        });



    }


}
