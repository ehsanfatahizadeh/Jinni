package ehsanfatahizadehgmail.com.jinni.adapters;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import ehsanfatahizadehgmail.com.jinni.R;
import ehsanfatahizadehgmail.com.jinni.models.CheckGive;



public class CheckGiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<CheckGive> list;
    private Context context;
    private static ClickListener clickListener;





    public CheckGiveAdapter(Context context){

        this.context = context;
        list = new ArrayList<>();
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view2 = inflater.inflate(R.layout.row_check_give, parent, false);



        return new ViewHolder(view2);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderMain, int position) {

        ViewHolder holder = (ViewHolder) holderMain;
        holder.txt_dar_vajhe.setText(list.get(position).getDar_vajhe());
        holder.txt_mablagh.setText(list.get(position).getMablagh());
        holder.txt_tarikh.setText(list.get(position).getDate_shamsi());
        holder.txt_vaziat.setText(list.get(position).getVaziat());
        holder.txt_az_hesabe.setText(list.get(position).getAz_hesabe());
        holder.txt_name_bank.setText(list.get(position).getName_bank());
        holder.txt_shomare_check.setText(list.get(position).getShomare_check());
        holder.txt_tozihat.setText(list.get(position).getTozihat());


//        holder.close.setVisibility(View.GONE);




    }

    @Override
    public int getItemCount() {
//        Log.d("--getItemCount " , String.valueOf(list.size()));
        return list.size();
    }



    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_dar_vajhe;
        TextView txt_mablagh;
        TextView txt_tarikh;
        TextView txt_vaziat;
        TextView txt_az_hesabe;
        TextView txt_name_bank;
        TextView txt_shomare_check;
        TextView txt_tozihat;

        ViewHolder(View itemView) {
            super(itemView);


            txt_dar_vajhe = (TextView) itemView.findViewById(R.id.dar_vajhe_row_check_give);
            txt_mablagh = (TextView) itemView.findViewById(R.id.mablagh_row_check_give);
            txt_tarikh = (TextView) itemView.findViewById(R.id.tarikh_row_check_give);
            txt_vaziat = (TextView) itemView.findViewById(R.id.vaziat_row_check_give);
            txt_az_hesabe = (TextView) itemView.findViewById(R.id.az_hesabe_row_check_give);
            txt_name_bank = (TextView) itemView.findViewById(R.id.name_bank_row_check_give);
            txt_shomare_check = (TextView) itemView.findViewById(R.id.shomare_check_row_check_give);
            txt_tozihat = (TextView) itemView.findViewById(R.id.tozihat_row_check_give);



//            itemView.setOnClickListener(this);





        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), list.get(getAdapterPosition()));
        }


    }

    public void add_list (List<CheckGive> list){
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
        void onItemClick(int position, CheckGive checkGive);
    }


}
