package com.appdow.quod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.appdow.quod.QuoD.mAinActivity;
import static com.appdow.quod.Urls.insertLoginInfo;
import static com.appdow.quod.Urls.returnOneRow;

public class SignUP extends AppCompatActivity {

    TextView textView;
    Button button;
    EditText name,email,password;
    private final String TAG = "SignUP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

        textView = (TextView) findViewById( R.id.link_login );
        textView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        } );

        button = (Button) findViewById( R.id.btn_signup );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMethod();
            }
        } );
        name = (EditText) findViewById( R.id.signup_name );
        email = (EditText) findViewById( R.id.signup_email );
        password = (EditText) findViewById( R.id.signup_password );

    }


    public void requestMethod() {
        StringRequest stringRequest = new StringRequest( Request.Method.POST, insertLoginInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w(TAG, "gvnvhmvjbk" + response );
                Toast.makeText(mAinActivity, "Your Account Was Successfully Created", Toast.LENGTH_SHORT).show();
                if(response.trim().equals( "success" )){
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.w(TAG, "gvnvhmvjbk" + error);

            }
        })

        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("QuoD", "QuoD");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;

            }
        };

        MySingleton.getInstance(mAinActivity).addToRequestQueue(stringRequest);

    }


}
