package com.shamscorp.numbertrivia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;

import static android.graphics.Color.WHITE;

public class GeneratorActivity extends AppCompatActivity {

    private String type;
    private DownloadTask task;
    private EditText editText;
    private TextView resultTextView;
    private TextView topicTextView;
    private TextView numberTextView;
    private EditText dateEditText;
    private String yeet;
    private Random rand;
    private static int counter = 0;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        Intent intent = getIntent();
        String message = intent.getStringExtra("Yung");
        rand = new Random();
        MobileAds.initialize(this, "ca-app-pub-8569084124971795~4430085863");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8569084124971795/9667876775");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        if (message.equals("random")) {
            type = "trivia";

        } else {
            type = message;
        }
        editText = (EditText) findViewById(R.id.editText);
        resultTextView = findViewById(R.id.resultTextView);
        topicTextView = findViewById(R.id.topicTextView);
        numberTextView = findViewById(R.id.digitsTextView);
        dateEditText = findViewById(R.id.dateEditText);

        if (type.equals("date")) {
            resultTextView.setText("Please enter a date in the format of MONTH/DAY (eg. 2/29, 1/09, 04/1), or click Generate Random Date");
            editText.setVisibility(View.INVISIBLE);
            dateEditText.setVisibility(View.VISIBLE);

        } else if (type.equals("year")) {
            resultTextView.setText("Please enter a year below or generate a random year");
        }
        topicTextView.setText("Current Topic: " + type);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });


    }

    public void onSearch(View view) {
        String result;
        counter++;
        if (counter%8 == 0) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        if (type.equals("date")) {
            yeet = dateEditText.getText().toString();
            String fullString = "Date: " + yeet;
            numberTextView.setText(fullString);
            task = new DownloadTask();

            try {
                if (type.equals("trivia")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/trivia").get();
                    topicTextView.setText("Current Topic: Random");
                } else if (type.equals("math")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/math").get();
                    topicTextView.setText("Current Topic: Math");
                } else if (type.equals("date")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/date").get();
                    topicTextView.setText("Current Topic: Date");
                } else if (type.equals("year")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/year").get();
                    topicTextView.setText("Current Topic: Year");
                } else {
                    result = "Not Found";
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = "Not Found";
            }
        } else {
            yeet = editText.getText().toString();
            String fullString = "Number: " + yeet;
            numberTextView.setText(fullString);
            task = new DownloadTask();
            try {
                if (type.equals("trivia")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/trivia").get();
                    topicTextView.setText("Current Topic: Random");
                } else if (type.equals("math")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/math").get();
                    topicTextView.setText("Current Topic: Math");
                } else if (type.equals("date")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/date").get();
                    topicTextView.setText("Current Topic: Date");
                } else if (type.equals("year")) {
                    result = task.execute("http://numbersapi.com/" + yeet + "/year").get();
                    topicTextView.setText("Current Topic: Year");
                } else {
                    result = "Not Found";
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = "Not Found";
            }
        }
        //Log.i("result: ", result);
        resultTextView.setText(result);
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void generateRandom(View view) {
        int pickedNum;
        String result;
        task = new DownloadTask();
        counter++;
        if (counter%8 == 0) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        try {
            if (type.equals("trivia")) {
                pickedNum = rand.nextInt(200) + 1;
                result = task.execute("http://numbersapi.com/" + pickedNum + "/trivia").get();
                String fullString = "Number: " + pickedNum;
                numberTextView.setText(fullString);

            } else if (type.equals("math")) {
                pickedNum = rand.nextInt(1000) + 1;
                result = task.execute("http://numbersapi.com/" + pickedNum + "/math").get();
                String fullString = "Number: " + pickedNum;
                numberTextView.setText(fullString);

            } else if (type.equals("date")) {
                pickedNum = rand.nextInt(365) + 1;
                String fullString = "Date: " + pickedNum;
                numberTextView.setText(fullString);
                result = task.execute("http://numbersapi.com/" + pickedNum + "/date").get();

            } else if (type.equals("year")) {
                pickedNum = rand.nextInt(2050) + 1;
                result = task.execute("http://numbersapi.com/" + pickedNum + "/year").get();
                String fullString = "Year: " + pickedNum;
                numberTextView.setText(fullString);

            } else {
                pickedNum = rand.nextInt(2050) + 1;
                result = task.execute("http://numbersapi.com/" + pickedNum + "/trivia").get();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "Please enter a new number";
        }
        resultTextView.setText(result);
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }


    public void newTopicChooser(View view) {
        finish();
        counter++;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "FAILED ERROR";
            }

        }
    }


}
