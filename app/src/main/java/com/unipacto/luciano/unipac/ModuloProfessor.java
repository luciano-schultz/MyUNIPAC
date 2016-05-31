package com.unipacto.luciano.unipac;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ModuloProfessor extends Activity
{

    private WebView webViewModuloProfessor;
    private static final String TAG = "ModuloProfessor";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_professor);

        this.webViewModuloProfessor = (WebView) findViewById(R.id.webViewModuloProfessor);
        pd = new ProgressDialog(ModuloProfessor.this);
        pd.setMessage("Por favor, aguarde o carregamento...");
        pd.show();
        webViewModuloProfessor.setWebViewClient(new MyWebViewClient());
        webViewModuloProfessor.loadUrl("http://unipacto.no-ip.org:67/unisys2/modulo_professor_mobile/login/");
        webViewModuloProfessor.getSettings().setJavaScriptEnabled(true);

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
