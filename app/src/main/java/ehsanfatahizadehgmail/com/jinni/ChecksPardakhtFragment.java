package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.adapters.CheckGiveAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.db.CheckGetDbHelper;
import ehsanfatahizadehgmail.com.jinni.db.CheckGiveDbHelper;
import ehsanfatahizadehgmail.com.jinni.models.CheckGet;
import ehsanfatahizadehgmail.com.jinni.models.CheckGive;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChecksPardakhtFragment extends Fragment {

    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;

    RecyclerView recyclerView;
    public static CheckGiveAdapter adapter;

    String mobile_sh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pardakht_checks , container , false);


        SharedPreferences sh = getActivity().getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        mobile_sh = sh.getString("mobile" , null);


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("لطفا کمی صبر کنید");

        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();



        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_pardakht_check);
        adapter = new CheckGiveAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    ChecksActivity.hidden_fab();
                }else if(dy < 0){
                    ChecksActivity.show_fab();
                }



            }
        });


        adapter.setOnItemClickListener(new CheckGiveAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, final CheckGive checkGive) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("حذف چک")
                        .setMessage("آیا از حذف چک اطمینان دارید؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new thread_delete_check_get(checkGive.getMobile() , checkGive.getId()).execute();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("خیر", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });




        new thread_get_check_give(mobile_sh).execute();


        return view;
    }





    public class thread_get_check_give extends AsyncTask {

        String mobile;
        public thread_get_check_give(String mobile){
            this.mobile = mobile;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<CheckGive> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CheckGive>> call = apis.getCheckGive(mobile);

            try {
                Response<List<CheckGive>> response = call.execute();

                if(!response.isSuccessful()) {
                    return null;
                }
                result = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            dialog.dismiss();

            if (result != null) {
                if (result.size() > 0) {


                    adapter.add_list(result);
                    check_db(result);



                }
            }else {
                Toast.makeText(getActivity(), "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }








    public class thread_delete_check_get extends AsyncTask {

        String mobile;
        String check_id;
        public thread_delete_check_get(String mobile , String check_id){
            this.mobile = mobile;
            this.check_id = check_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<CheckGive> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CheckGive>> call = apis.deleteCheckGive(mobile , check_id , "2");

            try {
                Response<List<CheckGive>> response = call.execute();

                if(!response.isSuccessful()) {
                    return null;
                }
                result = response.body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            dialog.dismiss();

            if (result != null) {

                adapter.add_list(result);
                check_db_delete(check_id);


            }else {
                Toast.makeText(getActivity(), "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }












    public void check_db(List<CheckGive> list_cg){
        CheckGiveDbHelper db = new CheckGiveDbHelper(getActivity());






        for (int i=0 ; i < list_cg.size() ; i++){

            boolean exist = db.checkExist(list_cg.get(i));

            if (!exist){
                CheckGive checkGive = new CheckGive();
                checkGive = list_cg.get(i);
                checkGive.id = checkGive.getId();
                checkGive.mobile = checkGive.getMobile();
                checkGive.dar_vajhe = checkGive.getDar_vajhe();
                checkGive.az_hesabe = checkGive.getAz_hesabe();
                checkGive.name_bank = checkGive.getName_bank();
                checkGive.shomare_check = checkGive.getShomare_check();
                checkGive.mablagh = checkGive.getMablagh();
                checkGive.tozihat = checkGive.getTozihat();
                checkGive.vaziat = checkGive.getVaziat();
                checkGive.year = checkGive.getYear();
                checkGive.month = checkGive.getMonth();
                checkGive.day = checkGive.getDay();
                checkGive.year_shamsi = checkGive.getYear_shamsi();
                checkGive.month_shamsi = checkGive.getMonth_shamsi();
                checkGive.day_shamsi = checkGive.getDay_shamsi();
                db.addCheck(checkGive);
            }

        }
        //reading
        List<CheckGive> list_db = db.getAllCheckGet();

        for (int i=0 ; i<list_db.size() ; i++) {
            Log.d("give_id", list_db.get(i).getId());
            Log.d("give_mobile", list_db.get(i).getMobile());
            Log.d("give_dar_vajhe", list_db.get(i).getDar_vajhe());
            Log.d("give_az_hesab", list_db.get(i).getAz_hesabe());
            Log.d("give_name_bank", list_db.get(i).getName_bank());
            Log.d("give_shomare_check", list_db.get(i).getShomare_check());
            Log.d("give_mablagh", list_db.get(i).getMablagh());
            Log.d("give_tozihat", list_db.get(i).getTozihat());
            Log.d("give_vaziat", list_db.get(i).getVaziat());
            Log.d("give_year", list_db.get(i).getYear());
            Log.d("give_month", list_db.get(i).getMonth());
            Log.d("give_day", list_db.get(i).getDay());
            Log.d("give_year_shamsi", list_db.get(i).getYear_shamsi());
            Log.d("give_month_shamsi", list_db.get(i).getMonth_shamsi());
            Log.d("give_day_shamsi", list_db.get(i).getDay_shamsi());
            Log.d("give_---", "---");
        }



    }









    public void check_db_delete(String check_id){
        CheckGiveDbHelper db = new CheckGiveDbHelper(getActivity());
        db.deleteCheckGet(check_id);



    }








}
