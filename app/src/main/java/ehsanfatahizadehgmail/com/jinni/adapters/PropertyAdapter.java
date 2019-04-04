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


public class PropertyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<String> list_descrip;
    public List<String> list_properties;
    private Context context;
    private static ClickListener clickListener;





    public PropertyAdapter(Context context  ){
        this.context = context;
        list_descrip = new ArrayList<String>();
        list_properties = new ArrayList<String>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_property, parent, false);


        return new ViewHolder(view2);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
//        CategoriesList categoriesList = list.get(position);



        holder.tw_properties.setText(list_properties.get(position));
        holder.tw_descrip.setText(list_descrip.get(position));


        if (context instanceof ProductActivity){
            holder.img_close.setVisibility(View.GONE);
        }

//        if (Integer.parseInt(categoriesList.getNum_of_subs()) == 0){
//            holder.img.setVisibility(View.GONE);
//        }else {
//            holder.img.setVisibility(View.VISIBLE);
//        }


    }

    @Override
    public int getItemCount() {
        return list_descrip.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tw_properties;
        TextView tw_descrip;
        ImageView img_close;
        ViewHolder(View itemView) {
            super(itemView);


            tw_properties = (TextView) itemView.findViewById(R.id.textview_properties_row_property);
            tw_descrip = (TextView) itemView.findViewById(R.id.textview_description_row_property);
            img_close = (ImageView) itemView.findViewById(R.id.imageview_close_row_property);



            img_close.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), list_properties.get(getAdapterPosition()) , list_descrip.get(getAdapterPosition()));
        }

    }


    public void add_list (List<String> properties ,List<String> descrip){
        list_properties = properties;
        list_descrip = descrip;
        notifyDataSetChanged();
    }



    public void add_row (String new_properties ,String new_descrip){
        list_properties.add(new_properties);
        list_descrip.add(new_descrip);
        notifyDataSetChanged();
    }

    public void delete_row (int position){
        list_properties.remove(position);
        list_descrip.remove(position);
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position, String properties , String descrip);
    }


}
