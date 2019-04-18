package ehsanfatahizadehgmail.com.jinni.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.R;

public class DateDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<String> list;
    private Context context;
    private static ClickListener clickListener;





    public DateDayAdapter(Context context){

        this.context = context;
        list = new ArrayList<>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_date, parent, false);



        return new ViewHolder(view2);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
        holder.txt.setText(list.get(position));


//        holder.close.setVisibility(View.GONE);




    }

    @Override
    public int getItemCount() {
        Log.d("--getItemCount " , String.valueOf(list.size()));
        return list.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt;
//        View close;

        ViewHolder(View itemView) {
            super(itemView);


            txt = (TextView) itemView.findViewById(R.id.textview_row_date);



//            itemView.setOnClickListener(this);





        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), list.get(getAdapterPosition()));
        }


    }

    public void add_list (List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }



    public void add_size (String c){
        Log.d("--added string " , c);
        list.add(c);
        notifyDataSetChanged();
    }


    public void delete_size (int c){
        list.remove(c);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position, String day);
    }


}
