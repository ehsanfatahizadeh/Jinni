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

import ehsanfatahizadehgmail.com.jinni.R;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;

public class CategoriesHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<CategoriesList> list;
    private Context context;
    private static ClickListener clickListener;





    public CategoriesHomeAdapter(Context context  ){
        this.context = context;
        list = new ArrayList<CategoriesList>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_home_categories, parent, false);


        return new ViewHolder(view2);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
        CategoriesList categoriesList = list.get(position);


        holder.tw.setText(categoriesList.getName());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tw;
        ViewHolder(View itemView) {
            super(itemView);


            tw = (TextView) itemView.findViewById(R.id.row_home_categories_category);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), list.get(getAdapterPosition()));
        }

    }


    public void add_list (List<CategoriesList> list){
        this.list = list;
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(int position, CategoriesList categoriesList);
    }


}
