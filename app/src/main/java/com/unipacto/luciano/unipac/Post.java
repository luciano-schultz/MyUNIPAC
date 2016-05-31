package com.unipacto.luciano.unipac;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import conexao.ConexaoHttpClient;


public class Post extends Activity implements AdapterView.OnItemClickListener
{

    String myJSON;
    ProgressBar progressBar;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "titulo";
    //private static final String TAG_ADD ="telefone";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        list = (ListView) findViewById(R.id.PostListView);
        personList = new ArrayList<HashMap<String,String>>();
        getData();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Log.i("selecionado", "slc" + id);
        HashMap<String,String> map = personList.get(position);
        String T = (String) map.get("TAG_ID");
        Log.i("selecionado", "TAG_ID" + T);
        Intent intent = new Intent(this,MsgPost.class);
        intent.putExtra("id",T);
        startActivity(intent);
        //startActivity(new Intent(this, MsgPost.class));
    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                //String address = c.getString(TAG_ADD);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_NAME,name);
                //persons.put(TAG_ADD,address);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Post.this, personList, R.layout.lista_post,
                    new String[]{TAG_ID,TAG_NAME},
                    new int[]{R.id.PostData, R.id.PostTitulo}

            );
            Log.i("Post", "dentro ListAdapter");

            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Here I want the category_id
                    Log.i("selecionado", "slc" + id);
                    HashMap<String, String> map = personList.get(position);
                    String T = (String) map.get("titulo");
                    String ids = (String) map.get("id");
                    Log.i("selecionado", "id" + T);
                    Intent intent = new Intent(Post.this,MsgPost.class);
                    intent.putExtra("id",ids);
                    intent.putExtra("titulo",T);
                    startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("erro", "///"+e);
        }

    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream inputStream = null;
                String result = null;
                String Url = "http://softwaregenerate.hol.es/listarUsuarios.php";
                String UrlGet = "";
                String respostaRetornada = null;
                try {
                //DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                //HttpPost httppost = new HttpPost("http://softwaregenerate.hol.es/script.php");

                // Depends on your web service
                //httppost.setHeader("Content-type", "application/json");



                    HttpResponse response = ConexaoHttpClient.teste();
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    Log.i("Pos", "vai entrar no while");
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    Log.i("Pos", "vai entrar no while");
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
                progressBar.setVisibility(View.GONE);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

}
