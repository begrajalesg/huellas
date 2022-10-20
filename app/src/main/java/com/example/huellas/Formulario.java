package com.example.huellas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Formulario extends AppCompatActivity {

    EditText t_id, t_nombre, t_apellido, t_usuario, t_clave;
    Button b_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        t_id = findViewById(R.id.txt_id);
        t_nombre = findViewById(R.id.txt_nombre);
        t_apellido = findViewById(R.id.txt_apellido);
        t_usuario = findViewById(R.id.txt_usuario);
        t_clave = findViewById(R.id.txt_clave);

        b_guardar = findViewById(R.id.btn_guardar);

        b_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos();
            }
        });
    }

    private void insertarDatos() {
        final String id = t_id.getText().toString().trim();
        final String nombre = t_nombre.getText().toString().trim();
        final String apellido = t_apellido.getText().toString().trim();
        final String usuario = t_usuario.getText().toString().trim();
        final String clave = t_clave.getText().toString().trim();

        //final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Cargando");

        if(id.isEmpty()){
            t_id.setError("Complete los campos");
        }else if (nombre.isEmpty()){
            t_nombre.setError("Complete los campos");
        }else if (apellido.isEmpty()){
            t_apellido.setError("Complete los campos");
        }else if (usuario.isEmpty()){
            t_usuario.setError("Complete los campos");
        }else if (clave.isEmpty()){
            t_clave.setError("Complete los campos");
        }else {
            //progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://172.20.10.12/huella/insertar.php", new Response.Listener<String>() {
            //StringRequest request = new StringRequest(Request.Method.POST, "http://localhost/huella/insertar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Registro correcto")) {
                        Toast.makeText(Formulario.this, "Datos insertados", Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();

                        Intent intent = new Intent(Formulario.this, Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Formulario.this, response, Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();
                        Toast.makeText(Formulario.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Formulario.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String>params = new HashMap<>();
                    params.put("id", id);
                    params.put("nombre", nombre);
                    params.put("apellido", apellido);
                    params.put("usuario", usuario);
                    params.put("clave", clave);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Formulario.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void login(View view){
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}