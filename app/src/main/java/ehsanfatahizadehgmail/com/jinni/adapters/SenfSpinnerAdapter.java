package ehsanfatahizadehgmail.com.jinni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ehsanfatahizadehgmail.com.jinni.R;
import ehsanfatahizadehgmail.com.jinni.SignUpActivity;


public class SenfSpinnerAdapter extends ArrayAdapter {


    private Context context;
    private String[] itemList;


    public SenfSpinnerAdapter( Context context, int resource,  String[] itemList) {
        super(context, resource, itemList);
        this.context=context;
        this.itemList=itemList;
    }


    @Override
    public boolean isEnabled(int position) {
        if(position == 0) {
            return false;
        }else {
            return true;
        }
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        return initView(position , convertView , parent);
    }

    @Override
    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
        return initView(position , convertView , parent);
    }


    @SuppressLint("ResourceAsColor")
    private View initView (int position , View convertView , ViewGroup parent){


        convertView = LayoutInflater.from(context).inflate(R.layout.row_spinner , parent , false);
        TextView tw = (TextView) convertView.findViewById(R.id.textview_row_senf);
        tw.setText(itemList[position]);
        if (position >= 1 && position <= 7) {
            tw.setBackgroundColor(0Xffcccccc);
        }else if (position == 8  ) {
            tw.setBackgroundColor(0Xffeeeeee);
        }else if (position == 9  ) {
            tw.setBackgroundColor(0Xffcccccc);
        }else if (position == 10 ) {
            tw.setBackgroundColor(0Xffeeeeee);
        }else if (position >= 11 && position <= 15 ) {
            tw.setBackgroundColor(0Xffcccccc);
        }else if (position == 16  ) {
            tw.setBackgroundColor(0Xffeeeeee);
        }else if (position >= 17 && position <= 22 ) {
            tw.setBackgroundColor(0Xffcccccc);
        }else if (position >= 23 && position <= 25 ) {
            tw.setBackgroundColor(0Xffeeeeee);
        }else if (position >= 26 && position <= 28 ) {
            tw.setBackgroundColor(0Xffcccccc);
        }else if (position == 29  ) {
            tw.setBackgroundColor(0Xffeeeeee);
        }else if (position >= 30 && position <= 31 ) {
            tw.setBackgroundColor(0Xffcccccc);
        }else if (position == 32 ) {
            tw.setBackgroundColor(0Xffeeeeee);
        }
//        else if (position >= 11 && position <= 16) {
//            tw.setBackgroundColor(R.color.colorAccent);
//        }else if (position >= 17 && position <= 26) {
//            tw.setBackgroundColor(R.color.background1spinner);
//        }else if (position >= 27 && position <= 32) {
//            tw.setBackgroundColor(R.color.colorAccent);
//        }

        if (position == 0) {
            // Set the hint text color gray
            tw.setTextColor(Color.GRAY);
        } else {
            tw.setTextColor(Color.BLACK);
        }


        return convertView;

    }



}
