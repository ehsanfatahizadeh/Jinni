package ehsanfatahizadehgmail.com.jinni;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.adapters.CheckGetAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.db.CheckGetDbHelper;
import ehsanfatahizadehgmail.com.jinni.models.CheckGet;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChecksDaryaftFragment extends Fragment {


    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    String mobile_sh;


    RecyclerView recyclerView;
    public static CheckGetAdapter adapter_check_get;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daryaft_checks , container , false);

        SharedPreferences sh = getActivity().getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        mobile_sh = sh.getString("mobile" , null);




        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("لطفا کمی صبر کنید");

        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_daryaft_check);
        adapter_check_get = new CheckGetAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_check_get);

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


        adapter_check_get.setOnItemClickListener(new CheckGetAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, final CheckGet checkGet) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("حذف چک")
                        .setMessage("آیا از حذف چک اطمینان دارید؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new thread_delete_check_get(checkGet.getMobile() , checkGet.getId()).execute();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("خیر", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });






        new thread_get_check_get(mobile_sh).execute();




        return view;
    }




    public class thread_get_check_get extends AsyncTask {

        String mobile;
        public thread_get_check_get(String mobile){
            this.mobile = mobile;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        List<CheckGet> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CheckGet>> call = apis.getCheckGet(mobile);

            try {
                Response<List<CheckGet>> response = call.execute();

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


                    adapter_check_get.add_list(result);

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


        List<CheckGet> result;
        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CheckGet>> call = apis.deleteCheckGet(mobile , check_id , "1");

            try {
                Response<List<CheckGet>> response = call.execute();

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

                Toast.makeText(getActivity(), "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                adapter_check_get.add_list(result);
                check_db_delete(check_id);


            }else {
                Toast.makeText(getActivity(), "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }






    public void check_db(List<CheckGet> list_cg){
        CheckGetDbHelper db = new CheckGetDbHelper(getActivity());






        for (int i=0 ; i < list_cg.size() ; i++){

            boolean exist = db.checkExist(list_cg.get(i));

            if (!exist){
                CheckGet checkGet = new CheckGet();
                checkGet = list_cg.get(i);
                checkGet.id = checkGet.getId();
                checkGet.mobile = checkGet.getMobile();
                checkGet.tavasote = checkGet.getTavasote();
                checkGet.shomare_check = checkGet.getShomare_check();
                checkGet.gheymat = checkGet.getGheymat();
                checkGet.tozihat = checkGet.getTozihat();
                checkGet.vaziat = checkGet.getVaziat();
                checkGet.year = checkGet.getYear();
                checkGet.month = checkGet.getMonth();
                checkGet.day = checkGet.getDay();
                checkGet.year_shamsi = checkGet.getYear_shamsi();
                checkGet.month_shamsi = checkGet.getMonth_shamsi();
                checkGet.day_shamsi = checkGet.getDay_shamsi();
                db.addCheck(checkGet);
            }

        }
        //reading
        List<CheckGet> list_db = db.getAllCheckGet();

        for (int i=0 ; i<list_db.size() ; i++) {
            Log.d("get_id", list_db.get(i).getId());
            Log.d("get_mobile", list_db.get(i).getMobile());
            Log.d("get_tavasote", list_db.get(i).getTavasote());
            Log.d("get_shomare_check", list_db.get(i).getShomare_check());
            Log.d("get_gheymat", list_db.get(i).getGheymat());
            Log.d("get_tozihat", list_db.get(i).getTozihat());
            Log.d("get_vaziat", list_db.get(i).getVaziat());
            Log.d("get_year", list_db.get(i).getYear());
            Log.d("get_month", list_db.get(i).getMonth());
            Log.d("get_day", list_db.get(i).getDay());
            Log.d("get_year_shamsi", list_db.get(i).getYear_shamsi());
            Log.d("get_month_shamsi", list_db.get(i).getMonth_shamsi());
            Log.d("get_day_shamsi", list_db.get(i).getDay_shamsi());
            Log.d("get_---", "---");
        }



    }









    public void check_db_delete(String check_id){
        CheckGetDbHelper db = new CheckGetDbHelper(getActivity());
        db.deleteCheckGet(check_id);



    }








}
