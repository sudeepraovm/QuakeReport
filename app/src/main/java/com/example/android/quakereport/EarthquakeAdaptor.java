package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdaptor extends ArrayAdapter<Earthquake> {
    public EarthquakeAdaptor(Context context, List<Earthquake> earthquakes) {
        super(context,R.layout.list_view, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView= LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view,parent,false
            );
        }
        Earthquake currentQuake = getItem(position);

        TextView Mag= (TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat formatter = new DecimalFormat("0.0");
        Mag.setText(currentQuake.getMag());

        TextView Loc0= (TextView) listItemView.findViewById(R.id.location0);
        TextView Loc= (TextView) listItemView.findViewById(R.id.location);
        String t=currentQuake.getLoc(); int i;
        if(t.contains("of")) {
            for (i = 0; t.charAt(i) != 'f'; i++) ;
            String q= t.substring(0,i+1);
            String w= t.substring(i+1,t.length());
            Loc0.setText(q);
            Loc.setText(w);
        }
        else {
            Loc0.setText("Near the");
            Loc.setText(t);
        }

        TextView d= (TextView) listItemView.findViewById(R.id.date);
        Date x = new Date(currentQuake.getDate());
        SimpleDateFormat fo= new SimpleDateFormat("LLL dd,yyyy");
        d.setText(fo.format(x));

        TextView time =(TextView) listItemView.findViewById(R.id.time);
        SimpleDateFormat f= new SimpleDateFormat("h:mm a");
        time.setText(f.format(x));
        GradientDrawable magCircle = (GradientDrawable) Mag.getBackground();

        magCircle.setColor(getMagColor(currentQuake.getMag()));
        return listItemView;
    }
    private int getMagColor(String m){
        double d=Double.parseDouble(m);
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(d);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}









