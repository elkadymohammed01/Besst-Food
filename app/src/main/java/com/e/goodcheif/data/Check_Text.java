package com.e.goodcheif.data;

import android.content.Context;
import android.widget.EditText;

import com.e.goodcheif.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Check_Text {

   private Context context;

    public Check_Text(Context context) {
        this.context = context;
    }

    public boolean is_empty(String s){
        if(s.split(" ").length>0)
            return false;
        return true;
    }
    public boolean is_empty(EditText S){
        String s=S.getText().toString();
        if(s.split(" ").length>0&&!s.equals("")){
            return false;}
        S.setText("");
        S.setHintTextColor(context.getResources().getColor(R.color.red));
        S.setBackgroundResource(R.drawable.error_border);
        S.setHint("Empty field");
        return true;
    }
    public boolean is_Integer(String s){
        for(char c:s.toCharArray())
            if(c>'9'||c<'0')
                return false;
            return true;
    }
    public String getTime(){

   return time_now();
    }

    private ArrayList<String> date =new ArrayList<>();
    private String time_now(){
        //Time Now is
        apply_date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String cond=formatter.format(date);
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
        int x=Integer.parseInt(cond.charAt(3)+""+cond.charAt(4));
        return
                ((cond.charAt(0)=='0'?"":cond.charAt(0))+""+cond.charAt(1)+""+this.date.get(x))+simpleDateformat.format(date);
        //Time block end

    }
    void apply_date(){
        date.add("op");
        date.add("Jan ");
        date.add("Feb ");
        date.add("Mar ");
        date.add("Apr ");
        date.add("May ");
        date.add("Jun ");
        date.add("Jul ");
        date.add("Aug ");
        date.add("Sep ");
        date.add("Oct ");
        date.add("Nov ");
        date.add("Dec ");
    }

}
