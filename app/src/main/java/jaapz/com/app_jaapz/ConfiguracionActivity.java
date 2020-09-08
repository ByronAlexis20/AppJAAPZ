package jaapz.com.app_jaapz;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.ConexionSQLiteC;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;

public class ConfiguracionActivity extends AppCompatActivity {
    Integer _id = 0;
    EditText et_ip_servidor;

    Button btn_grabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        et_ip_servidor = (EditText)findViewById(R.id.et_ip_servidor);
        btn_grabar = (Button)findViewById(R.id.btn_grabar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cargarDatos();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void cargarDatos(){
        SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
        et_ip_servidor.setText(preferences.getString(Constantes.ID_SHARED_PREFERENCES_IP_SERVODPR,""));
    }
    public void update(){
        try{
            SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constantes.ID_SHARED_PREFERENCES_IP_SERVODPR,et_ip_servidor.getText().toString());
            editor.commit();
            Intent ListSong = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(ListSong);
            finish();
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void grabar(View view){
        try{
            //Toast.makeText(this, _id, Toast.LENGTH_SHORT).show();
            update();
        }catch(Exception ex){

        }
    }
}

