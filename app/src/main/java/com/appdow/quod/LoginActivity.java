package com.appdow.quod;

import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.appdow.quod.QuoD.editor;
import static com.appdow.quod.QuoD.mAinActivity;
import static com.appdow.quod.Urls.loginInfoCheck;
import static com.appdow.quod.Urls.returnOneRow;

public class LoginActivity extends AppCompatActivity {
    final public String TAG = "LoginActivity";
    EditText email,password;
    Button button;
    TextView signUpTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.submitbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestMethod();
            }
        });
        signUpTextView = (TextView) findViewById( R.id.link_signup );
        signUpTextView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mAinActivity, "Maa chudao Nahi akrna signup", Toast.LENGTH_SHORT).show();
                startActivity( new Intent( LoginActivity.this, SignUP.class ) );
            }
        } );

    }


    public void requestMethod() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginInfoCheck, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w(TAG, "gvnvhmvjbk " + response );
                Toast.makeText(mAinActivity, response, Toast.LENGTH_SHORT).show();
                if(response.trim().startsWith("success")){


                    mAinActivity.menuItem.setTitle( "Logout" );
                    mAinActivity.welcomeTextView.setText( "Welcome "+response.split( "success" )[1]+"!" );
                    editor.putBoolean("loggedIn", true);
                    editor.apply();

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
                header.put("User-Agent", "QuoD");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }

        };
        MySingleton.getInstance(mAinActivity).addToRequestQueue(stringRequest);
    }
}
