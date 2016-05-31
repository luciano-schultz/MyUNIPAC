package com.unipacto.luciano.unipac;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ModuloAluno extends Activity
{

    private WebView webViewModuloAluno;
    private static final String TAG = "ModuloAluno";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_aluno);

        this.webViewModuloAluno = (WebView) findViewById(R.id.webViewModuloAluno);
        pd = new ProgressDialog(ModuloAluno.this);
        pd.setMessage("Por favor, aguarde o carregamento...");
        pd.show();
        webViewModuloAluno.setWebViewClient(new MyWebViewClient());
        webViewModuloAluno.loadUrl("http://unipacto.no-ip.org:8080/unisys2/modulo_aluno_mobile/login/");
        webViewModuloAluno.getSettings().setJavaScriptEnabled(true);
    }

    public class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if(!pd.isShowing())
            {
                pd.show();
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if(pd.isShowing())
            {
                pd.dismiss();
            }
        }
    }
}
