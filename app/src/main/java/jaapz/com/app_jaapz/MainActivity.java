package jaapz.com.app_jaapz;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.pojos.Anio;
import jaapz.com.app_jaapz.pojos.AnioDAO;
import jaapz.com.app_jaapz.pojos.AperturaLectura;
import jaapz.com.app_jaapz.pojos.AperturaLecturaDAO;
import jaapz.com.app_jaapz.pojos.Barrio;
import jaapz.com.app_jaapz.pojos.BarrioDAO;
import jaapz.com.app_jaapz.pojos.Cliente;
import jaapz.com.app_jaapz.pojos.ClienteDAO;
import jaapz.com.app_jaapz.pojos.CuentaCliente;
import jaapz.com.app_jaapz.pojos.CuentaClienteDAO;
import jaapz.com.app_jaapz.pojos.Medidor;
import jaapz.com.app_jaapz.pojos.MedidorDAO;
import jaapz.com.app_jaapz.pojos.Mes;
import jaapz.com.app_jaapz.pojos.MesDAO;
import jaapz.com.app_jaapz.pojos.Planilla;
import jaapz.com.app_jaapz.pojos.PlanillaDAO;
import jaapz.com.app_jaapz.pojos.PlanillaDetalle;
import jaapz.com.app_jaapz.pojos.PlanillaDetalleDAO;
import jaapz.com.app_jaapz.pojos.ResponsableLectura;
import jaapz.com.app_jaapz.pojos.ResponsableLecturaDAO;

import jaapz.com.app_jaapz.pojos.SegPerfil;
import jaapz.com.app_jaapz.pojos.SegPerfilDAO;
import jaapz.com.app_jaapz.pojos.SegUsuario;
import jaapz.com.app_jaapz.pojos.SegUsuarioDAO;
import jaapz.com.app_jaapz.pojos.SegUsuarioPerfil;
import jaapz.com.app_jaapz.pojos.SegUsuarioPerfilDAO;
import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ControllerHelper;
import jaapz.com.app_jaapz.util.CuadroDialogo;

public class MainActivity extends AppCompatActivity implements CuadroDialogo.OnDialogListener,
        DialogoSeguridadLogin.FinalizoCuadroDialogo {
    Button btnLogin;

    ImageButton btnConfiguracion;
    EditText et_usuario;
    EditText et_clave;
    ControllerHelper helper = new ControllerHelper();

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getSupportActionBar().hide();
        if(getPreference()){
            Intent ListSong = new Intent(getApplicationContext(), PrincipalActivity.class);
            startActivity(ListSong);
            finish();
        }
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnConfiguracion = (ImageButton)findViewById(R.id.btn_configuracion);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_clave = (EditText) findViewById(R.id.et_clave);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irPantallaConfiguraciones();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

    }

    private void guardarEstadoSesion(Integer idUsuario){
        SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constantes.ID_SHARED_PREFERENCES_ESTADO,true);
        editor.putInt(Constantes.ID_SHARED_PREFERENCES_USUARIO,idUsuario);
        editor.commit();
    }
    private boolean getPreference(){
        SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
        return preferences.getBoolean(Constantes.ID_SHARED_PREFERENCES_ESTADO,false);
    }
    private void iniciarSesion(){
        try{
            String clave = helper.Encriptar(String.valueOf(et_clave.getText()));
            String usuario = helper.Encriptar(String.valueOf(et_usuario.getText()));

            guardarEstadoSesion(1);

            Intent ListSong = new Intent(getApplicationContext(), PrincipalActivity.class);
            startActivity(ListSong);
            finish();

        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //metodo del resultado de lo q retorna el cuadro de dialogo de inicio de sesion para las configuraciones
    @Override
    public void resultadoCuadroDialogo(Boolean sesionIniciada) {
        try{
            if(sesionIniciada == true){
                Intent intent = new Intent(this, ConfiguracionActivity.class);
                startActivity(intent);
            }else
                Toast.makeText(context, "Usuario o clave incorrecto!!!", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void irPantallaConfiguraciones(){
        try{
            //aqui va para iniciar secion e ir a la pantalla de configuraciones
            new DialogoSeguridadLogin(this,MainActivity.this);
        }catch(Exception ex){
        }
    }
    @Override
    public void OnPositiveButtonClicked() {

    }

    @Override
    public void OnNegativeButtonClicked() {
        try{
            //Toast.makeText(this, "Se ha cancelado la operación", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
