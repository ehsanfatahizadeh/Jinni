package ehsanfatahizadehgmail.com.jinni.adapters;



import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import ehsanfatahizadehgmail.com.jinni.R;



public class PhotosProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<String> list_urls;
    private Context context;
    private static ClickListener clickListener;





    public PhotosProductAdapter(Context context  ){
        this.context = context;
        list_urls = new ArrayList<String>();

    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_photos_new_product, parent, false);


        return new ViewHolder(view2);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;



        Glide.with(context).load("http://beh-navaz.ir/jinni-api/" + list_urls.get(position))
                .centerCrop()
                .into(holder.img);





    }

    @Override
    public int getItemCount() {
        return list_urls.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        ImageView img_close;

        ViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imageview_row_photos_new_p);
            img_close = (ImageView) itemView.findViewById(R.id.imageview_close_row_photos);

            img_close.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition());
        }

    }


    public void add_list (List<String> urls ){
        list_urls = urls;
        notifyDataSetChanged();
    }



    public void delete_row_url (int position){
        list_urls.remove(position);
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public interface ClickListener {
        void onItemClick(int position);
    }


}
