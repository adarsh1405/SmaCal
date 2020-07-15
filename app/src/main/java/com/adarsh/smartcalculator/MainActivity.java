package com.adarsh.smartcalculator;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity {
    TextView txt,result;
    ImageButton voice,img;
    String statement;
    Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.msg);
        voice=findViewById(R.id.voice);
        img=findViewById(R.id.scan);
        result=findViewById(R.id.res);
        clear=findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statement="";
                txt.setText("");
                result.setText("");
            }
        });
        }

        public void make_statement(View view)
        {
            Button b = (Button)view;
            statement  =statement + b.getText().toString();
            txt.setText(statement);
        }
        public void evaluate(View view)throws  Exception{
            Button b = (Button)view;
            calcspeech();
        }



    public void getSpeechText(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager())!=null)
        startActivityForResult(intent,10);
        else
            Toast.makeText(this, "Your Device not Support Speech Input", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data!= null){
                   ArrayList<String> res= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    statement=res.get(0).toString();
//                    txt.setText(statement);
                    try {
                        calcspeech();
                    } catch (Exception e) {
                        Toast.makeText(this, "Action not found", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
    public void calcspeech() throws Exception{
        statement = statement.replaceAll ("plus", "+");
        statement = statement.replaceAll ("add", "+");
        statement = statement.replaceAll ("minus", "-");
        statement = statement.replaceAll ("subtract", "-");
        statement = statement.replaceAll ("multiply by", "*");
        statement = statement.replaceAll ("into", "*");
        statement = statement.replaceAll ("multiply", "*");
        statement = statement.replaceAll ("divided by", "/");
        statement = statement.replaceAll ("divide", "/");
        statement = statement.replaceAll ("divided", "/");
        statement = statement.replaceAll ("by", "/");
        statement = statement.replaceAll ("mod", "%");
        statement = statement.replaceAll ("remainder", "%");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        try
        {
            txt.setText(statement);
            result.setText(engine.eval(statement).toString());
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Cant Evaluate", Toast.LENGTH_SHORT).show();
        }
    }

}