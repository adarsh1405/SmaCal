package com.adarsh.smartcalculator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView txt, result;
    ImageButton voice, img;
    String statement = " ";
    Button clear, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, badd, bsub, bmul, bdiv, bpoint, bequal, undo;
    ImageView imageView;
    Bitmap imageBitmap;
    Switch stxt;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        navigationView.setNavigationItemSelectedListener(this);
//        if(savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
//            navigationView.setCheckedItem(R.id.home);
//        }


//        stxt= findViewById(R.id.switchtxt);
        txt = findViewById(R.id.msg);
        voice = findViewById(R.id.voice);
        img = findViewById(R.id.scan);
        result = findViewById(R.id.res);
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statement = "";
                txt.setText("");
                result.setText("");
                imageView.setVisibility(View.INVISIBLE);
                b0.setVisibility(View.VISIBLE);
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);
                b4.setVisibility(View.VISIBLE);
                b5.setVisibility(View.VISIBLE);
                b6.setVisibility(View.VISIBLE);
                b7.setVisibility(View.VISIBLE);
                b8.setVisibility(View.VISIBLE);
                b9.setVisibility(View.VISIBLE);
                badd.setVisibility(View.VISIBLE);
                bsub.setVisibility(View.VISIBLE);
                bmul.setVisibility(View.VISIBLE);
                bdiv.setVisibility(View.VISIBLE);
                bpoint.setVisibility(View.VISIBLE);
                bequal.setVisibility(View.VISIBLE);
            }
        });
        b0 = findViewById(R.id.b0);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        badd = findViewById(R.id.add);
        bsub = findViewById(R.id.sub);
        bmul = findViewById(R.id.mul);
        bdiv = findViewById(R.id.div);
        bpoint = findViewById(R.id.point);
        bequal = findViewById(R.id.equal);
        imageView = findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                imageView.setVisibility(View.VISIBLE);
                b0.setVisibility(View.INVISIBLE);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                b4.setVisibility(View.INVISIBLE);
                b5.setVisibility(View.INVISIBLE);
                b6.setVisibility(View.INVISIBLE);
                b7.setVisibility(View.INVISIBLE);
                b8.setVisibility(View.INVISIBLE);
                b9.setVisibility(View.INVISIBLE);
                badd.setVisibility(View.INVISIBLE);
                bsub.setVisibility(View.INVISIBLE);
                bmul.setVisibility(View.INVISIBLE);
                bdiv.setVisibility(View.INVISIBLE);
                bpoint.setVisibility(View.INVISIBLE);
                bequal.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
                break;
            case R.id.instruction:
            case R.id.textSpeech:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new rules()).commit();
                break;
            case R.id.about:
            case R.id.dark:
            case R.id.code:
            case R.id.share:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

    }

    public void make_statement(View view) {
        Button b = (Button) view;

        statement = statement + b.getText().toString();
        txt.setText(statement);
    }

    public void evaluate(View view) throws Exception {
        Button b = (Button) view;
        calcspeech();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getSpeechText(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, 10);
        else
            Toast.makeText(this, "Your Device not Support Speech Input", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = null;
            if (data != null) {
                extras = data.getExtras();
            }
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
            }
            imageView.setImageBitmap(imageBitmap);
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
            FirebaseVision firebaseVision = FirebaseVision.getInstance();
            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
            Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
            task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    statement = " ";
                    statement = firebaseVisionText.getText();
                    try {
//                        txt.setText(statement);
                        calcspeech();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Cant find Math Expression", Toast.LENGTH_SHORT).show();
                }
            });
        }

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    statement = res.get(0);
//                    txt.setText(statement);
                    try {
                        calcspeech();
                    } catch (Exception e) {
                        Toast.makeText(this, "Action not found", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    public void calcspeech() throws Exception {
        statement = statement.replaceAll("plus", "+");
        statement = statement.replaceAll("add", "+");
        statement = statement.replaceAll("minus", "-");
        statement = statement.replaceAll("subtract", "-");
        statement = statement.replaceAll("multiply by", "*");
        statement = statement.replaceAll("x", "*");
        statement = statement.replaceAll("multiplied by", "*");
        statement = statement.replaceAll("into", "*");
        statement = statement.replaceAll("multiply", "*");
        statement = statement.replaceAll("divided by", "/");
        statement = statement.replaceAll("divide", "/");
        statement = statement.replaceAll("divided", "/");
        statement = statement.replaceAll("by", "/");
        statement = statement.replaceAll("mod", "%");
        statement = statement.replaceAll("remainder", "%");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        try {
            txt.setText(statement);
            String str = engine.eval(statement).toString();
            if (str.equalsIgnoreCase("Infinity"))
                result.setText("ERROR !");
            else {
                try {
                    double d = Double.parseDouble(str);
                    DecimalFormat df = new DecimalFormat("#.#####");
                    df.setRoundingMode(RoundingMode.CEILING);
                    str = df.format(d);
                    result.setText(str);
                } catch (NumberFormatException e) {
                    int i = Integer.parseInt(str);
                    result.setText(i);
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "Cant Evaluate", Toast.LENGTH_SHORT).show();
        }
    }


}