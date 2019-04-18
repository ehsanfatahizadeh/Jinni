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
import ehsanfatahizadehgmail.com.jinni.models.CheckGet;

public class CheckGetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<CheckGet> list;
    private Context context;
    private static ClickListener clickListener;





    public CheckGetAdapter(Context context){

        this.context = context;
        list = new ArrayList<>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_check_get, parent, false);



        return new ViewHolder(view2);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
        holder.txt_tavasote.setText(list.get(position).getTavasote());
        holder.txt_mablagh.setText(list.get(position).getGheymat());
        holder.txt_tarikh.setText(list.get(position).getDate_shamsi());
        holder.txt_vaziat.setText(list.get(position).getVaziat());
        holder.txt_tozihat.setText(list.get(position).getTozihat());


//        holder.close.setVisibility(View.GONE);




    }

    @Override
    public int getItemCount() {
//        Log.d("--getItemCount " , String.valueOf(list.size()));
        return list.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_tavasote;
        TextView txt_mablagh;
        TextView txt_tarikh;
        TextView txt_vaziat;
        TextView txt_tozihat;

        ViewHolder(View itemView) {
            super(itemView);


            txt_tavasote = (TextView) itemView.findViewById(R.id.textview_tavasote_row_check_get);
            txt_mablagh = (TextView) itemView.findViewById(R.id.textview_mablagh_row_check_get);
            txt_tarikh = (TextView) itemView.findViewById(R.id.textview_tarikh_row_check_get);
            txt_vaziat = (TextView) itemView.findViewById(R.id.textview_vaziat_row_check_get);
            txt_tozihat = (TextView) itemView.findViewById(R.id.textview_tozihat_row_check_get);



//            itemView.setOnClickListener(this);





        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), list.get(getAdapterPosition()));
        }


    }

    public void add_list (List<CheckGet> list){
        this.list = list;
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
        void onItemClick(int position, CheckGet checkGet);
    }


}

