package com.client.appfp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.client.appfp.Android.AppSignatureHelper;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Codigo HASH de la app es: g3Mji1k3j7Q

    private static final String TAG = "MenuInicial";
    private static final int RESOLVE_HINT = 200;       //Codigo de respuesta correcto para obtener el número de telefono
    private String numTel, numSaneado;
    private Button entrada , bCont;
    private ProgressBar pBar;
    private EditText textoMovil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppBP);
        setContentView(R.layout.activity_telefono);
        //Esconder la barra superior de la APP
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //Enlazar views
        entrada = (Button) findViewById(R.id.botonSelTel);
        bCont = (Button) findViewById(R.id.buttonContinuar);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.setVisibility(View.INVISIBLE);
        textoMovil = (EditText) findViewById(R.id.NumTel);
        entrada.setOnClickListener(this);       //Asignar el evento al botón

        // Inside Main Activity
    /*
        AppSignatureHelper appSignatureHashHelper = new AppSignatureHelper(MainActivity.this);
        Log.d(TAG, "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0));
    */

    }

    // Construct a request for phone numbers and show the picker
    private void requestHint() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    // Obtener y usar el número de telefono
    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            switch (resultCode) {    //RESULT_OK == 1--> Si selecciona correctamente, 0 --> Si pulsa fuera
                // 1002 (EMULADOR) --> NO SIM CARD detectada
                case RESULT_CANCELED:
                    Toast.makeText(this, "Se ha cancelado la selección", Toast.LENGTH_LONG).show();
                    break;
                case CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE:
                    Toast.makeText(this, "El dispositivo no contiene un número de telefono válido", Toast.LENGTH_LONG).show();
                    break;
                case RESULT_OK:
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    numTel=credential.getId();  //<-- obtenemos el string correspondiente al numbero de telefono seleccionado
                    numSaneado= numTel.substring(0,3)+" "+numTel.substring(3);   //Saneamos la salida en formato más legible
                    textoMovil.setText(numSaneado); ///Ponemos el texto en el EditText
                    break;
                default:
                    Toast.makeText(this, "Caso no contemplado", Toast.LENGTH_LONG).show();
            }
            Log.d(TAG,"Llega al resultado de la actividad de obtener el número de telefono: Codigo:"+resultCode);
        }
    }

    public void onContinuar(View v) {
        pBar.setVisibility(View.VISIBLE);
        Intent otpIntent = new Intent(MainActivity.this,OtpActivity.class); //Mover de la Clase A a la B
        otpIntent.putExtra("tel",numTel);
        Toast.makeText(this, "Num tel es: "+numTel, Toast.LENGTH_LONG).show();
        //Pasamos el num de Telefono
        startActivity(otpIntent);
        //Código necesario para obtener el codigo hash de la app
        //AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        //Log.d(TAG,"El código hash de la app es: "+appSignatureHelper.getAppSignatures().get(0));
        //Codigo HASH de la app es: g3Mji1k3j7Q
    }

    @Override
    public void onClick(View v) {
        requestHint();
    }

}
