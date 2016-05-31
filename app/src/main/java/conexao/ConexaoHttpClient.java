package conexao;

import android.util.Log;

import java.util.ArrayList;
import java.io.*;
import java.net.URI;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public class ConexaoHttpClient
{
    public static final int HTTP_TIMEOUT = 30 * 1000;
    private static HttpClient httpClient;

    private static HttpClient getHttpClient()
    {
        if(httpClient == null)
        {
            httpClient = new DefaultHttpClient();
            final HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(httpParams,HTTP_TIMEOUT);
        }
        return httpClient;
    }

    public static String executaHttpPost(String url,ArrayList<NameValuePair> paramentrosPost) throws Exception
    {

        BufferedReader bufferedReader = null;
        try
        {
            HttpClient client = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramentrosPost);
            httpPost.setEntity(formEntity);
            HttpResponse httpResponse = client.execute(httpPost);
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LS = System.getProperty("line.separator");
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line + LS);
            }
            bufferedReader.close();
            String resultado =  stringBuffer.toString();
            return resultado;
        }finally
        {
            if(bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String executaHttpGet(String url) throws Exception
    {
        BufferedReader bufferedReader = null;
        try
        {

            HttpClient client = getHttpClient();
            HttpGet httpGet = new HttpGet(url);

            httpGet.setURI(new URI(url));
            HttpResponse httpResponse = client.execute(httpGet);
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LS = System.getProperty("line.separator");
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line + LS);
            }
            bufferedReader.close();
            String resultado =  stringBuffer.toString();
            return resultado;
        }finally
        {
            if(bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static HttpResponse teste()
    {
        try {
            HttpClient httpclient = getHttpClient();
        HttpPost httppost = new HttpPost("http://softwaregenerate.hol.es/script.php");

        // Depends on your web service
        httppost.setHeader("Content-type", "application/json");



            HttpResponse response = httpclient.execute(httppost);
            Log.i("gravar", "vai entrar no try");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
