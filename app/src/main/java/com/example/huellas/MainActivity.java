package com.example.huellas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread inicio = new Thread(){
            public void run(){
                try{
                    int tiempo = 0;
                    while (tiempo < 5000){
                        sleep(100);
                        tiempo = tiempo + 100;
                    }
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    finish();
                }
            }
        };
        inicio.start();
    }
}