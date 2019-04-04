package ehsanfatahizadehgmail.com.jinni.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import ehsanfatahizadehgmail.com.jinni.R;
import ehsanfatahizadehgmail.com.jinni.models.Products;


public class NewestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Products> products;
//    public List<String> list_properties;
    private Context context;
    private static ClickListener clickListener;

    int WHAT_FRAGMENT;




    public NewestAdapter(Context context , int WHAT_FRAGMENT  ){
        this.context = context;
        this.WHAT_FRAGMENT = WHAT_FRAGMENT;
        products = new ArrayList<Products>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_newest, parent, false);


        return new ViewHolder(view2);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
//        CategoriesList categoriesList = list.get(position);


        String s_titr = "";
        if (!products.get(position).getMahiat_titr().equals(""))
            s_titr = s_titr + " " + products.get(position).getMahiat_titr();
        if (!products.get(position).getMahiat_titr().equals(""))
            s_titr = s_titr + " " + products.get(position).getJensiat_titr();
        if (!products.get(position).getMahiat_titr().equals(""))
            s_titr = s_titr + " " + products.get(position).getBrand_titr();
        if (!products.get(position).getMahiat_titr().equals(""))
            s_titr = s_titr + " " + products.get(position).getModel_titr();
        if (!products.get(position).getMahiat_titr().equals(""))
            s_titr = s_titr + " " + products.get(position).getVizhegi_titr();
        if (!products.get(position).getMahiat_titr().equals(""))
            s_titr = s_titr + " " + products.get(position).getSize_titr();


        if (!products.get(position).getGheymate_vahed().equals("")){
            String s_price = "هر ";
            s_price = s_price + products.get(position).getName_vahed();
            s_price = s_price + " " + products.get(position).getGheymate_vahed() + " تومان" ;
            holder.price.setText(s_price);
        }

        if (WHAT_FRAGMENT == 3 && !products.get(position).getHaraji().equals("no")){
            holder.haraji.setVisibility(View.VISIBLE);
            String s_haraji = products.get(position).getHaraji();
            s_haraji = s_haraji + " تومان";
            holder.haraji.setText(s_haraji);
            holder.haraji.setPaintFlags(holder.haraji.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.haraji.setVisibility(View.GONE);
        }




        holder.titr.setText(s_titr);
        Glide.with(context).load(products.get(position).getPhotos().size()!=0?
                "http://beh-navaz.ir/jinni-api/"+products.get(position).getPhotos().get(0):"https://www.freeiconspng.com/uploads/no-image-icon-32.png")
                .centerCrop()
                .into(holder.img);
//        holder.tw_descrip.setText(list_descrip.get(position));



//        if (Integer.parseInt(categoriesList.getNum_of_subs()) == 0){
//            holder.img.setVisibility(View.GONE);
//        }else {
//            holder.img.setVisibility(View.VISIBLE);
//        }


    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titr , price , haraji;
        ImageView img;
//        TextView tw_descrip;
        //        ImageView img;
        ViewHolder(View itemView) {
            super(itemView);


            img = (ImageView) itemView.findViewById(R.id.row_newest_image);
            titr = (TextView) itemView.findViewById(R.id.row_newest_titr);
            price = (TextView) itemView.findViewById(R.id.row_newest_price);
            haraji = (TextView) itemView.findViewById(R.id.row_newest_cheap);
//            tw_descrip = (TextView) itemView.findViewById(R.id.textview_description_row_property);
//            img = (ImageView) itemView.findViewById(R.id.imageview_arrow_row_category);


            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), products.get(getAdapterPosition()));
        }

    }


    public void addList (List<Products> products ){
        this.products = products;
        notifyDataSetChanged();
    }
//
//    public void delete_row (String new_properties ,String new_descrip){
////        list_properties.add(new_properties);
////        list_descrip.add(new_descrip);
//        notifyDataSetChanged();
//    }



    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position, Products products);
    }


}
