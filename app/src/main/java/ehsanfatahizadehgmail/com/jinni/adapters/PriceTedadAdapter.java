package ehsanfatahizadehgmail.com.jinni.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.ProductActivity;
import ehsanfatahizadehgmail.com.jinni.R;

public class PriceTedadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<String> list_az;
    public List<String> list_ta;
    public List<String> list_price;
    private Context context;
    private static ClickListener clickListener;





    public PriceTedadAdapter(Context context  ){
        this.context = context;
        list_az = new ArrayList<String>();
        list_ta = new ArrayList<String>();
        list_price = new ArrayList<String>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_price_tedad, parent, false);


        return new ViewHolder(view2);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
//        CategoriesList categoriesList = list.get(position);



        holder.tw_az.setText(list_az.get(position));
        holder.tw_ta.setText(list_ta.get(position));
        holder.tw_price.setText(list_price.get(position));



        if (context instanceof ProductActivity){
            holder.img.setVisibility(View.GONE);
        }else {
            holder.img.setVisibility(View.VISIBLE);
        }


//        if (Integer.parseInt(categoriesList.getNum_of_subs()) == 0){
//            holder.img.setVisibility(View.GONE);
//        }else {
//            holder.img.setVisibility(View.VISIBLE);
//        }


    }

    @Override
    public int getItemCount() {
        return list_az.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tw_price;
        TextView tw_az;
        TextView tw_ta;
        ImageView img;
        ViewHolder(View itemView) {
            super(itemView);


            tw_price = (TextView) itemView.findViewById(R.id.row_price_tedad_price);
            tw_az = (TextView) itemView.findViewById(R.id.row_price_az_tedad);
            tw_ta = (TextView) itemView.findViewById(R.id.row_price_ta_tedad);
            img = (ImageView) itemView.findViewById(R.id.row_price_imageview_close);




            img.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition());
        }

    }


    public void add_list (List<String> list_az ,List<String> list_ta , List<String> list_price){
        this.list_az=list_az;
        this.list_ta=list_ta;
        this.list_price=list_price;
        notifyDataSetChanged();
    }



    public void add_row (String list_az ,String list_ta , String list_price){
        this.list_az.add(list_az);
        this.list_ta.add(list_ta);
        this.list_price.add(list_price);
        notifyDataSetChanged();
    }

    public void delete_row (int position){
        list_az.remove(position);
        list_ta.remove(position);
        list_price.remove(position);
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position);
    }


}
