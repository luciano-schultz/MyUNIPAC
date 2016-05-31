package com.unipacto.luciano.unipac;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends AppCompatActivity implements Runnable
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);

    }

    public void run()
    {
        if(verificaConexao())
        {
            startActivity(new Intent(this, Home.class));
            finish();
        }else{
            Context contexto = getApplicationContext();
            String texto = "Verifique sua conexão com a internet!";
            int duracao = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(contexto,texto,duracao);
            toast.show();
            finish();
        }

    }

    public boolean verificaConexao()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
