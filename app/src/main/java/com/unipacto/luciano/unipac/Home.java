package com.unipacto.luciano.unipac;

import android.app.TabActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class Home extends TabActivity
{

    TabHost TabHostWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TabHost tabHost = getTabHost();
        this.setNewTab(this, tabHost, "tab1", R.string.textTabTitle1, R.mipmap.img_post, R.id.tab1);
        this.setNewTab(this,tabHost,"tab2",R.string.textTabTitle2,R.mipmap.img_calendar,R.id.tab2);
        this.setNewTab(this, tabHost, "tab3", R.string.textTabTitle3, R.mipmap.img_aluno, R.id.tab3);
        this.setNewTab(this, tabHost, "tab4", R.string.textTabTitle4, R.mipmap.img_professor, R.id.tab4);

    }

    private void setNewTab(Context context,TabHost tabHost,String tag,int title,int icon,int contentID)
    {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(tabHost.getContext(),title,icon));

        if(contentID == R.id.tab1)
        {
            tabSpec.setContent(new Intent(this, Post.class));
        }else if(contentID == R.id.tab2)
        {
            tabSpec.setContent(new Intent(this, Agenda.class));
        }else if(contentID == R.id.tab3)
        {
            tabSpec.setContent(new Intent(this, ModuloAluno.class));
        }else if(contentID == R.id.tab4)
        {
            tabSpec.setContent(new Intent(this, ModuloProfessor.class));
        }else
        {

        }

        tabHost.addTab(tabSpec);
    }

    private View getTabIndicator(Context context,int title,int icon)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setTextSize(12);
        tv.setText(title);
        return view;
    }

}
