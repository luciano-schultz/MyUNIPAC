package com.unipacto.luciano.unipac;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.imanoweb.calendarview.CustomCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;

public class Agenda extends AppCompatActivity
{
    //CalendarView calendarView;
    CustomCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        calendarView = (CustomCalendarView) findViewById(R.id.calendar_view);

        //inicia calendario com data
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Mostra segunda como data da primeira semana
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        //show / hide dias estouro de um mes
        calendarView.setShowOverflowDate(false);

        //chama refreshcalendar para ayualizar calendario da vista
        //calendarView.refreshCalendar(currentCalendar);
        //List decorator = new ArrayList();
        //decorator.add(new ColorDrawable());
        //calendarView.setDecorators(decorator);
        //calendarView.refreshCalendar(currentCalendar);

    }
}
