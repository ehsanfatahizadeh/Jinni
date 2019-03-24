package ehsanfatahizadehgmail.com.jinni.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import ehsanfatahizadehgmail.com.jinni.R;

public class ColorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<String> colors;
    private Context context;
    private static ClickListener clickListener;





    public ColorsAdapter(Context context ){
        this.context = context;
        colors = new ArrayList<>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_color, parent, false);


        return new ViewHolder (view2);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

            ViewHolder holder = (ViewHolder) holderMain;
            holder.img.setBackgroundColor(Color.parseColor(colors.get(position)));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img;

        ViewHolder(View itemView) {
            super(itemView);


            img = (CircleImageView) itemView.findViewById(R.id.circle_image_row_color);
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//                }
//            });
//            img.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), colors.get(getAdapterPosition()));
        }

//        @Override
//        public void onClick(View v) {
//
//            Log.d("Click1 is " , String.valueOf(getAdapterPosition()));
//            mOnClickListener.onClick(getAdapterPosition(), colors.get(getAdapterPosition()));
//
//        }


    }


//    public void clear(){
//        talarList.clear();
//        notifyDataSetChanged();
//    }


    public void add_color (String c){
        colors.add(c);
        notifyDataSetChanged();
    }


    public void delete_color (int c){
        colors.remove(c);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position, String color);
    }


}

