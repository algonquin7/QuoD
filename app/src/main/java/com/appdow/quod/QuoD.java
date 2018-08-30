package com.appdow.quod;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.appdow.quod.Urls.getRowCount;
import static com.appdow.quod.Urls.returnOneRow;

public class QuoD extends AppCompatActivity {
    private static String TAG = "QuoD";
    public static QuoD mAinActivity;
    private Button copy, share, randomButton;
    private TextView quoteTextView;
    public static int random = 0;
    public static ClipboardManager clipboard;
    CoordinatorLayout coordinatorLayout;
    ArrayList<Integer> list;
    public int numberOfRowsinMsqlTable = 0;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_quo_d);

        mAinActivity = this;

        requestNumberOfRowsAndCreateShuffledList();

        declareAllViews();//allViewDeclaration

        swipeListernerMethod(quoteTextView);//swipelisterner

        allClickListerners();//allClicks

        navigationDrwawerDeclare();

    }//onCreate


    public void navigationDrwawerDeclare() {


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nvView);
        imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.dpImageView);
        imageView.setBackgroundResource(R.drawable.quod1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(mAinActivity, "My Account", Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.parse("https://www.instagram.com/devashah7/?hl=de");
                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                        likeIng.setPackage("com.instagram.android");
                        try {
                            startActivity(likeIng);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.instagram.com/devashah7/?hl=de")));
                        }
                        return true;
                    case R.id.login:
                        Toast.makeText(mAinActivity, "Settings", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(QuoD.this, LoginActivity.class));
                        return true;
                    case R.id.mycart:
                        Toast.makeText(mAinActivity, "My Cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    public void shuffleList(int numberOfRows) {

        list = new ArrayList<Integer>();
        for (int i = 1; i <= numberOfRows; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

    }


    public void declareAllViews() {

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);//{essential For Clipboard}

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.mainLayout);
        coordinatorLayout.setBackgroundColor(Color.BLACK);

        copy = (Button) findViewById(R.id.copy);
        copy.setVisibility(View.GONE);
        share = (Button) findViewById(R.id.share);
        share.setVisibility(View.GONE);
        randomButton = (Button) findViewById(R.id.random);
        quoteTextView = (TextView) findViewById(R.id.quoteView);
        quoteTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    public void makeCopyShareVisible() {
        copy.setVisibility(View.VISIBLE);
        share.setVisibility(View.VISIBLE);

    }


    public void allClickListerners() {

        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //requestMethod(Integer.toString(randomMethod()));
                if ((list.size() - 1) != random) {
                    requestMethod(Integer.toString(list.get(random)));
                    random++;
                } else {
                    random = 0;
                    Collections.shuffle(list);
                    requestMethod(Integer.toString(list.get(random)));
                    random++;
                }
                makeCopyShareVisible();
            }
        });//randomButtonClickListener

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clip = ClipData.newPlainText("quoteCopies", quoteTextView.getText().toString() + "\nFor More quotes checkout QuoD App on Playstore.");
                clipboard.setPrimaryClip(clip);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Copied!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });//copyClickListerner


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareButton(quoteTextView.getText().toString());

            }
        });//shareClickListerner

    }


    public void swipeListernerMethod(View v) {

        v.setOnTouchListener(new OnSwipeTouchListener(mAinActivity) {

            public void onSwipeTop() {
                //Toast.makeText( mAinActivity, "top", Toast.LENGTH_SHORT ).show();
            }

            public void onSwipeRight() {

                if (random != 1 && random != 0) {
                    --random;
                    requestMethod(Integer.toString(random));
                    makeCopyShareVisible();
                }
            }

            public void onSwipeLeft() {

                if (random != numberOfRowsinMsqlTable) {
                    random++;
                    requestMethod(Integer.toString(random));
                    makeCopyShareVisible();
                }
            }

            public void onSwipeBottom() {

            }
        });

    }


    public void filessss() {

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("filename.txt"), "utf-8"));
            writer.write("Something");
        } catch (IOException ex) {
            // Report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }

    }

    public void requestNumberOfRowsAndCreateShuffledList() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getRowCount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w(TAG, "gvnvhmvjbk" + response.toString());
                numberOfRowsinMsqlTable = Integer.parseInt(response.trim());
                shuffleList(numberOfRowsinMsqlTable);//listshuffle

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.w(TAG, "gvnvhmvjbk" + error);

            }
        });

        MySingleton.getInstance(mAinActivity).addToRequestQueue(stringRequest);


    }

    public void requestMethod(final String number) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, returnOneRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w(TAG, "gvnvhmvjbk" + response.toString() + random);
                try {
                    JSONArray quoteResponse = new JSONArray(response);
                    JSONObject quoteReponseObject = quoteResponse.getJSONObject(0);
                    quoteTextView.setText(quoteReponseObject.getString("quote"));
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("entry", number);
                return params;
            }

        };
        MySingleton.getInstance(mAinActivity).addToRequestQueue(stringRequest);
    }//requestMethod


    public void shareButton(String shareQuote) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = shareQuote + "\nFor More quotes checkout QuoD App on Playstore.";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

}
