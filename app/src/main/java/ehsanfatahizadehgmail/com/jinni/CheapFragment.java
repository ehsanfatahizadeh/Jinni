package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
import android.content.Context;
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

import ehsanfatahizadehgmail.com.jinni.adapters.NewestAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.models.Products;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CheapFragment extends Fragment {




    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    RecyclerView recycler_newest;
    NewestAdapter adapter_newest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cheap , container , false);


        recycler_newest = (RecyclerView) view.findViewById(R.id.recycler_cheap);
        adapter_newest = new NewestAdapter(getActivity() , 3);
        recycler_newest.setLayoutManager(new LinearLayoutManager(getActivity()));

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("لطفا کمی صبر کنید");


        SharedPreferences sh = getActivity().getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        String mobile = sh.getString("mobile" , null);


        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();



        new thread_get_haraji(mobile).execute();




        return view;
    }




    List<Products> result;
    public class thread_get_haraji extends AsyncTask {


        String mobile;
        public thread_get_haraji(String mobile){
            this.mobile = mobile;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }



        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<Products>> call = apis.getCheaps(mobile);

            try {
                Response<List<Products>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "errrror2", Toast.LENGTH_SHORT).show();
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

            recycler_newest.setAdapter(adapter_newest);
            adapter_newest.addList(result);



//            Log.d("result is :" , result.get(0).getColors().get(0));
//            if (result_code != null) {
//                if (result_code.equals("1")) {
//
//
//                }
//            } else {
//                Toast.makeText(TellActivity.this, "ارتباط با سرور برقرار نشد!", Toast.LENGTH_SHORT).show();
//            }

        }

    }











}