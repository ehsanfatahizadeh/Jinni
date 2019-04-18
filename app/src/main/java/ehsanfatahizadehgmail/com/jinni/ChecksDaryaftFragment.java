package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
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
    CheckGetAdapter adapter_check_get;




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

//                    Constants.REFRESH_CHECKS = true;


                }
            }else {
                Toast.makeText(getActivity(), "مشکل در ارتباط با سرور \n دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }





}
