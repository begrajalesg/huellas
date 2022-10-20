package com.example.huellas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText tx_usuario, tx_password;
    String str_usuario, str_clave;
    String url = "http://172.20.10.12/huella/validar.php";
    Button btn_login, btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tx_usuario = findViewById(R.id.usuario);
        tx_password = findViewById(R.id.clave);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener((View view) -> {
            Intent intent = new Intent(Login.this, Fundaciones.class);
            login();
            startActivity(intent);
        });
    }


    public void login() {
        if (tx_usuario.getText().toString().equals("")) {
            Toast.makeText(this, "Ingrese usuario!", Toast.LENGTH_SHORT).show();
        } else if (tx_password.getText().toString().equals("")) {
            Toast.makeText(this, "Ingrese clave!", Toast.LENGTH_SHORT).show();
        } else {
            //final ProgressDialog progressDialog = new ProgressDialog(this);
            //progressDialog.setMessage("Por favor espera");
            //progressDialog.dismiss();

            str_usuario = tx_usuario.getText().toString().trim();
            str_clave = tx_password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //progressDialog.dismiss();
                    if (response.equalsIgnoreCase("Ingreso correctamente")) {
                        tx_usuario.setText("");
                        tx_password.setText("");

                        //(new Intent(getApplicationContext(), Fundaciones.class));
                    } else {
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //progressDialog.dismiss();
                    Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("usuario", str_usuario);
                    params.put("clave", str_clave);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);
        }
    }

    public void resgistro(View view) {
        startActivity(new Intent(getApplicationContext(), Formulario.class));
        finish();
    }
}