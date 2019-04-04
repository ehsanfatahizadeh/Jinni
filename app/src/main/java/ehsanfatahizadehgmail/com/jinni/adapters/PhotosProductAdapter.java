package ehsanfatahizadehgmail.com.jinni.adapters;



import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import ehsanfatahizadehgmail.com.jinni.R;



public class PhotosProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Uri> list_photos;
    private Context context;
    private static ClickListener clickListener;





    public PhotosProductAdapter(Context context  ){
        this.context = context;
        list_photos = new ArrayList<Uri>();

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

        holder.img.setImageURI(list_photos.get(position));


    }

    @Override
    public int getItemCount() {
        return list_photos.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        ViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imageview_row_photos_new_p);


            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
//            clickListener.onItemClick(getAdapterPosition(), list_properties.get(getAdapterPosition()) , list_descrip.get(getAdapterPosition()));
        }

    }


    public void add_row (Uri uri ){
        this.list_photos.add(uri);
        notifyDataSetChanged();
    }

    public void delete_row (String new_properties ,String new_descrip){
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position, Uri uri);
    }


}
