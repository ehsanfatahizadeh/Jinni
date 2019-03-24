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





    public SizesAdapter(Context context ){
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
        Log.d("--setText " , sizes.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("--getItemCount " , String.valueOf(sizes.size()));
        return sizes.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_size;

        ViewHolder(View itemView) {
            super(itemView);


            txt_size = (TextView) itemView.findViewById(R.id.textview_row_size);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), sizes.get(getAdapterPosition()));
        }


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
