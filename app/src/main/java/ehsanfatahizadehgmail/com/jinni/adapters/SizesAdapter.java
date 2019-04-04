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


public class SizesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<String> sizes;
    private Context context;
    private static ClickListener clickListener;


    int WHAT_ACTIVITY;


    public SizesAdapter(Context context , int WHAT_ACTIVITY ){
        this.WHAT_ACTIVITY = WHAT_ACTIVITY;
        this.context = context;
        sizes = new ArrayList<>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_sizes, parent, false);



        return new ViewHolder(view2);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
        holder.txt_size.setText(sizes.get(position));

        if (WHAT_ACTIVITY==2){
            holder.close.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        Log.d("--getItemCount " , String.valueOf(sizes.size()));
        return sizes.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_size;
        View close;

        ViewHolder(View itemView) {
            super(itemView);


            txt_size = (TextView) itemView.findViewById(R.id.textview_row_size);
            close = (View) itemView.findViewById(R.id.row_size_close);

            if (WHAT_ACTIVITY==1){
                itemView.setOnClickListener(this);
            }




        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), sizes.get(getAdapterPosition()));
        }


    }

    public void add_list (List<String> list){
        sizes = list;
        notifyDataSetChanged();
    }



    public void add_size (String c){
        Log.d("--added string " , c);
        sizes.add(c);
        notifyDataSetChanged();
    }


    public void delete_size (int c){
        sizes.remove(c);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position, String size);
    }


}
