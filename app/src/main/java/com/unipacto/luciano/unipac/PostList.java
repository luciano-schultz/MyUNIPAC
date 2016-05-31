package com.unipacto.luciano.unipac;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.ConexaoHttpClient;

public class PostList extends Activity implements AdapterView.OnItemClickListener
{

int x = 0;

    List<String> post;
    ArrayAdapter<String> adaptador;
    ListView postList;
    ProgressBar progressBar;
    public List<Map<String,Object>> postMap;

private ListView listViewUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        HttpAsync httpAsync = new HttpAsync(this);
        httpAsync.execute();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Log.i("selecionado", "slc" + id);
        Map<String, Object> map = postMap.get(position);
        String T = (String) map.get("titulo");
        Log.i("selecionado", "titulo" + T);
        Intent intent = new Intent(this,MsgPost.class);
        intent.putExtra("titulo",T);
        startActivity(intent);
        //startActivity(new Intent(this, MsgPost.class));
    }

    private class HttpAsync extends AsyncTask<String,Void,String>
    {
        PostList listaActivity;
        public HttpAsync(PostList listaActivity)
        {
            //recupera a instacia da nossa Activity
            this.listaActivity = listaActivity;
        }

        String[] de ={"imagem","titulo","data","hora"};
        int[] para = {R.id.tipoPost,R.id.PostTitulo,R.id.PostData,R.id.PostHora};
        String [] listaUsuarios; //= new String[100];
        int posicao = 0;
        //public List<Map<String,Object>> postMap;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            SimpleAdapter adaptador = new SimpleAdapter(PostList.this,Listar(),R.layout.lista_post,de,para);
            postList.setAdapter(adaptador);
            progressBar.setVisibility(View.GONE);
            postList.setOnItemClickListener(PostList.this);
        }

        @Override
        protected String doInBackground(String... params)
        {
            postList = (ListView) findViewById(R.id.PostListView);
            Listar();
            return null;
        }

        public List<Map<String,Object>> Listar()
        {

            String Url = "http://softwaregenerate.hol.es/listarUsuarios.php";
            String UrlGet = "";
            String respostaRetornada = null;
            Log.i("gravar", "vai entrar no try");
            try {
                respostaRetornada = ConexaoHttpClient.executaHttpGet(Url);
                //respostaRetornada = ConexaoHttpClient.executaHttpGet("http://softwaregenerate.hol.es/logar.php?usuario="+editTextUsuario.getText().toString()+"&&senha="+editTextSenha.getText().toString());
                String resposta = respostaRetornada.toString();
                Log.i("listar", "listar " + resposta);

                char separador='#';
                int contaUsuarios = 0;
                for(int i = 0;i<resposta.length();i++)
                {
                    if(separador == resposta.charAt(i))
                        contaUsuarios++;
                }

                listaUsuarios = new String[contaUsuarios];
                postMap = new ArrayList<Map<String,Object>>();


                char caracter_lido = resposta.charAt(0);
                String nomeUsuario = "";
                for(int i = 0;caracter_lido != '^';i++)
                {
                    caracter_lido = resposta.charAt(i);
                    Log.i("listar", "caracter_lido " + caracter_lido);
                    if(caracter_lido != '#')
                        nomeUsuario += (char) caracter_lido;
                    else
                    {
                        listaUsuarios[posicao] = ""+nomeUsuario;
                        Log.i("listar", "Lista[" + posicao + "] " + listaUsuarios[posicao]);
                        Map<String,Object> item = new HashMap<String,Object>();
                        item.put("imagem", R.mipmap.announcement);
                        item.put("titulo", listaUsuarios[posicao]);
                        postMap.add(item);
                        item = null;
                        posicao++;
                        nomeUsuario = "";
                    }
                }
                return postMap;

            } catch (Exception erro) {
                Log.i("listar", "erro: " + erro);
                //Toast.makeText(Logar.this,"Erro.: "+erro,Toast.LENGTH_LONG);
            }
            return postMap;
        }
    }
}
