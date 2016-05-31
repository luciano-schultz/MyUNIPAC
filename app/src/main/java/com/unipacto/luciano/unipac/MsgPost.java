package com.unipacto.luciano.unipac;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.ConexaoHttpClient;

public class MsgPost extends Activity
{
    TextView textViewTitulo;
    TextView textViewMsg;
    ProgressBar progressBar;
    String T;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_post);

        Intent intent = getIntent();
        T = intent.getStringExtra("id");
        id = intent.getStringExtra("titulo");
        //id = T.toString();

        Log.i("MSG", "MSG" + T);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        textViewTitulo = (TextView) findViewById(R.id.textViewTitulo);
        textViewTitulo.setText(id);
        HttpAsync httpAsync = new HttpAsync(this);
        httpAsync.execute();

    }

    private class HttpAsync extends AsyncTask<String,Void,String>
    {
        MsgPost msgPost;
        String  listaMsg; //= new String[100];
        public HttpAsync(MsgPost msgPost)
        {
            //recupera a instacia da nossa Activity
            this.msgPost = msgPost;
        }
        String MSG; //= new String[100];
        int posicao = 0;
        //public List<Map<String,Object>> postMap;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            textViewMsg.setText(MSG);
            //Log.i("MSG", "MSG[" + 0 + "] " + MSG[0]);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params)
        {
            textViewTitulo = (TextView) findViewById(R.id.textViewTitulo);
            textViewMsg = (TextView) findViewById(R.id.textViewMsg);
            MSG = Listar();
            return null;
        }

        public String Listar()
        {
            String UrlPost = null;

            UrlPost = "http://softwaregenerate.hol.es/listarMsg.php";
            ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
                parametrosPost.add(new BasicNameValuePair("id",T));


            String respostaRetornada = null;
            char teste = 0;
            Log.i("MSG", "vai entrar no try");
            try {
                respostaRetornada = ConexaoHttpClient.executaHttpPost(UrlPost, parametrosPost);
                String resposta = respostaRetornada.toString();
                Log.i("MSG", "vai entrar no try "+resposta);
                return resposta;

            } catch (Exception erro) {
                Log.i("MSG", "erro: " + erro);
                //Toast.makeText(Logar.this,"Erro.: "+erro,Toast.LENGTH_LONG);
            }
            return listaMsg;
        }
    }

}
