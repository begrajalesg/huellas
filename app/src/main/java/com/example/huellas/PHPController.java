package com.example.huellas;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PHPController {

    Context c;
    RequestQueue requestQueue;

    private static String IP = "192.168.20.83";

    //usuario
    private static String URLREGISTER = "http://"+IP+"/huellas/datos/insertar.php";
    private static String URLLOGIN = "http://"+IP+"/huellas/datos/login.php?";
    private static String URLREADALLUSER = "http://"+IP+"/huellas/datos/buscar.php";
    private static String URLEDITUSER = "http://"+IP+"/huellas/datos/editar.php";
    private static String URLDELETEUSER = "http://"+IP+"/huellas/datos/eliminar.php";

    //Perfil URL
    private static String ULRCREATEPERFIL = "http://"+IP+"/coollection/perfiles/create.php";
    private static String URLREADALLPERFIL = "http://"+IP+"/coollection/perfiles/readAll.php";
    private static String URLEDITPERFIL = "http://"+IP+"/coollection/perfiles/update.php";
    private static String URLDELETEPERFIL = "http://"+IP+"/coollection/perfiles/delete.php";

    //constructor
    public PHPController(Context context){
        c = context;
        requestQueue = Volley.newRequestQueue(c);
    }

    //Metodos
    //usuarios Resitrar
    public void  Registrar(String id, String nom, String ape, String user, String pass){
        final String identf = id;
        final String nombre = nom;
        final String apellido = ape;
        final String username = user;
        final String password = pass;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLREGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c, "¡USUARIO REGISTRADO CON EXITO!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, "Error al crear al usuario...", Toast.LENGTH_SHORT).show();
                        System.out.println("--------------------------");
                        System.out.println("ERROR: " + error.getMessage());
                        System.out.println("---------------------------");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", identf);
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("usuario", username);
                params.put("clave", password);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Login
    public void Login(String usu, String pass){
        String username = "usuario="+usu;
        String password = "&clave="+pass;
        String urllogin = URLLOGIN + username + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urllogin,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(c, "INICIO DE SESION EXITOSA", Toast.LENGTH_SHORT).show();

                        String id, nombre, apellido, username, password;
                        try {
                            id = response.getString("id");
                            nombre = response.getString("nombre");
                            apellido = response.getString("apellido");
                            username = response.getString("usuario");
                            password = response.getString("password");
                            String nombreCompleto = nombre + " " + apellido;

                            if (id.equals("1")) {
                                Intent i = new Intent(c, Administrador.class);
                                i.putExtra("nombre", nombreCompleto);
                                i.putExtra("usuario", username);
                                i.putExtra("clave", password);
                                c.startActivity(i);
                            } else {
                                crearPerfil(id);
                                Intent i = new Intent(c, Fundaciones.class);
                                i.putExtra("idusuario", id);
                                i.putExtra("usuario", nombreCompleto);
                                i.putExtra("usuUsername", username);
                                i.putExtra("usuPassword", password);
                                c.startActivity(i);
                            }
                        } catch (JSONException e) {
                            System.out.println("ERROR: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, "ERROR AL LOGEARSE...", Toast.LENGTH_SHORT).show();
                        System.out.println("----------------------------");
                        System.out.println("ERROR: " + error.getMessage());
                        System.out.println("----------------------------");
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    //Buscar todos


    //Editar
    public void EditUser(String id, String nom, String ape, String per, String pass){
        final String idUser = id;
        final String nombreUser = id;
        final String apellidoUser = id;
        final String usernameUser = id;
        final String passwordUser = id;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLEDITUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c, "Modificacion exitosa!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, "Error al modificar", Toast.LENGTH_SHORT).show();
                        System.out.println("Error al modificar: "+error.getMessage());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("id", idUser);
                params.put("nombre", nombreUser);
                params.put("apellido", apellidoUser);
                params.put("usuario", usernameUser);
                params.put("clave", passwordUser);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Eliminar
    public void DeleteUser(String id){
        final String idUser = id;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLDELETEUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c, "Eliminacion exitosa!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, "Error de eliminacion", Toast.LENGTH_SHORT).show();
                        System.out.println("Error de eliminacion: " + error.getMessage());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("id", idUser);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    //Crear perfil
    private void crearPerfil(String id){
        final String id_perfil = id;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ULRCREATEPERFIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("-----------------------");
                        System.out.println("PERFIL CREADO CON EXITO");
                        System.out.println("-----------------------");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("-----------------------");
                        System.out.println("ERROR CREANDO EL PERFIL");
                        System.out.println("Err: " + error.getMessage());
                        System.out.println("-----------------------");
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_perfil", id_perfil);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    //Leer todos los perfiles

}
