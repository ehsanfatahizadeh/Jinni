package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.adapters.CategoriesHomeAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.ColorsAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.ColorsProductAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PriceTedadAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PropertyAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.SizesAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import ehsanfatahizadehgmail.com.jinni.models.Products;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {


    PagerAdapter pagerAdapter;
    ViewPager pager;
    TextView tw_titr_name;


    ApiInterface apis;
    RetrofitSetting retrofit;
    ProgressDialog dialog;


    RecyclerView recycler_colors;
    ColorsProductAdapter adapter_colors;


    TextView colors_titr;

    ImageView img_haraji , img_special;



    TextView tw_code;

    RecyclerView recycler_property;
    PropertyAdapter adapter_property;



    RecyclerView recycler_tedad;
    PriceTedadAdapter adapter_tedad;

    TextView tw_description;


    RecyclerView recycler_sizes;
    SizesAdapter adapter_sizes;

    TextView titr_size;


    TextView tw_category;



    ImageView img_back;


    LinearLayout linear_name_vahed , linear_gheymate_vahed , linear_vahede_farei , linear_zaribe_farei , linear_haraji;
    TextView tw_name_vahed , tw_gheymate_vahed , tw_vahede_farei , tw_zaribe_farei , tw_haraji;


    public static List<Products> list_p;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        list_p = new ArrayList<Products>();
        Button btn_edit = (Button) findViewById(R.id.button_edit_product_act);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_edit = new Intent(ProductActivity.this , EditProductActivity.class);
                startActivity(intent_edit);
            }
        });



        img_back = (ImageView) findViewById(R.id.activity_product_back_img);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        linear_name_vahed = (LinearLayout) findViewById(R.id.linearlayout_act_p_name_vahed);
        linear_gheymate_vahed = (LinearLayout) findViewById(R.id.linearlayout_act_p_gheymat_vahed);
        linear_vahede_farei = (LinearLayout) findViewById(R.id.linearlayout_act_p_vahede_farei);
        linear_zaribe_farei = (LinearLayout) findViewById(R.id.linearlayout_act_p_zaribe_vahede_farei);
        linear_haraji = (LinearLayout) findViewById(R.id.linearlayout_act_p_haraji);

        tw_name_vahed = (TextView) findViewById(R.id.textview_act_p_name_vahed);
        tw_gheymate_vahed = (TextView) findViewById(R.id.textview_act_p_gheymat_vahed);
        tw_vahede_farei = (TextView) findViewById(R.id.textview_act_p_vahede_farei);
        tw_zaribe_farei = (TextView) findViewById(R.id.textview_act_p_zaribe_vahede_farei);
        tw_haraji = (TextView) findViewById(R.id.textview_act_p_haraji);


        tw_category = (TextView) findViewById(R.id.category_product_activity);



        tw_titr_name = (TextView) findViewById(R.id.textview_titr_product);
        pager = (ViewPager) findViewById(R.id.pager_product);

        img_haraji = (ImageView) findViewById(R.id.imageview_haraji_product_act);
        img_special = (ImageView) findViewById(R.id.imageview_special_product_act);


        tw_code = (TextView) findViewById(R.id.textView_code_product_act);


        tw_description = (TextView) findViewById(R.id.description_product_act);

        recycler_property = (RecyclerView) findViewById(R.id.recyclerview_property_product_act);
        recycler_property.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        adapter_property = new PropertyAdapter(ProductActivity.this);
        recycler_property.setAdapter(adapter_property);


        recycler_tedad = (RecyclerView) findViewById(R.id.recyclerview_big_price_product_act);
        recycler_tedad.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        adapter_tedad = new PriceTedadAdapter(ProductActivity.this);
        recycler_tedad.setAdapter(adapter_tedad);



        recycler_sizes = (RecyclerView) findViewById(R.id.recyclerview_sizes_product_act);
        adapter_sizes = new SizesAdapter(ProductActivity.this , 2);
        recycler_sizes.setLayoutManager(new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recycler_sizes.setAdapter(adapter_sizes);

        titr_size = (TextView) findViewById(R.id.titr_sizes_product_activity);



        recycler_colors = (RecyclerView) findViewById(R.id.recyclerview_colors_product);
        recycler_colors.setLayoutManager(new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
        adapter_colors = new ColorsProductAdapter(ProductActivity.this);
        recycler_colors.setAdapter(adapter_colors);

        colors_titr = (TextView) findViewById(R.id.titr_colors_product_activity);


        String id = getIntent().getStringExtra("product_id");


        dialog = new ProgressDialog(ProductActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");


        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        final String mobile = sh.getString("mobile" , null);


        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();


        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());




        new thread_getProduct(mobile , id).execute();



    }






    List<String> urls;
    List<Products> result;
    public class thread_getProduct extends AsyncTask {


        String mobile;
        String product_id;
        public thread_getProduct(String mobile , String product_id ){
            this.mobile = mobile;
            this.product_id = product_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }



        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<Products>> call = apis.getProduct(mobile , product_id);

            try {
                Response<List<Products>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(ProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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

//                Toast.makeText(ProductActivity.this, result.get(0).getMahiat_titr(), Toast.LENGTH_SHORT).show();
                urls = new ArrayList<>();
                urls = result.get(0).getPhotos();
                pager.setAdapter(pagerAdapter);

                list_p = result;

                if (result.get(0).getColors().size()>0) {
                    adapter_colors.add_list(result.get(0).getColors());
                    colors_titr.setText("در " + result.get(0).getColors().size() + " رنگ:");
                }else {
                    recycler_colors.setVisibility(View.GONE);
                    colors_titr.setVisibility(View.GONE);
                }

                adapter_property.add_list(result.get(0).getProperty_name() , result.get(0).getProperty_description());

                adapter_tedad.add_list(result.get(0).getTedad_az() , result.get(0).getTedad_ta() , result.get(0).getTedad_gheymat() );

                tw_code.setText(result.get(0).getCode());

                tw_category.setText("دسته بندی : "+result.get(0).getCategory_name());


                if (result.get(0).getName_vahed().equals("")){
                    linear_name_vahed.setVisibility(View.GONE);
                }else{
                    tw_name_vahed.setText(result.get(0).getName_vahed());
                }

                if (result.get(0).getGheymate_vahed().equals("")){
                    linear_gheymate_vahed.setVisibility(View.GONE);
                }else{
                    tw_gheymate_vahed.setText(result.get(0).getGheymate_vahed() + " تومان");
                }

                if (result.get(0).getVahede_zaribe_sefaresh().equals("")){
                    linear_vahede_farei.setVisibility(View.GONE);
                }else{
                    tw_vahede_farei.setText(result.get(0).getVahede_zaribe_sefaresh());
                }

                if (result.get(0).getZaribe_sefaresh().equals("")){
                    linear_zaribe_farei.setVisibility(View.GONE);
                }else{
                    tw_zaribe_farei.setText(result.get(0).getZaribe_sefaresh());
                }

                if (result.get(0).getHaraji().equals("no")){
                    linear_haraji.setVisibility(View.GONE);
                }else{
                    tw_haraji.setText(result.get(0).getHaraji() + " تومان");
                }




                if (!result.get(0).getTozihat().equals("")) {
                    tw_description.setText("توضیحات \n" + result.get(0).getTozihat());
                }else {
                    tw_description.setVisibility(View.GONE);
                }


                if (result.get(0).getSizes().size()>0){
                    adapter_sizes.add_list(result.get(0).getSizes());
                    titr_size.setText( "در " + result.get(0).getSizes().size() + " سایز:");
                }else {
                    recycler_sizes.setVisibility(View.GONE);
                    titr_size.setVisibility(View.GONE);
                }





                String s_titr = "";
                if (!result.get(0).getMahiat_titr().equals(""))
                    s_titr = s_titr + " " + result.get(0).getMahiat_titr();
                if (!result.get(0).getMahiat_titr().equals(""))
                    s_titr = s_titr + " " + result.get(0).getJensiat_titr();
                if (!result.get(0).getMahiat_titr().equals(""))
                    s_titr = s_titr + " " + result.get(0).getBrand_titr();
                if (!result.get(0).getMahiat_titr().equals(""))
                    s_titr = s_titr + " " + result.get(0).getModel_titr();
                if (!result.get(0).getMahiat_titr().equals(""))
                    s_titr = s_titr + " " + result.get(0).getVizhegi_titr();
                if (!result.get(0).getMahiat_titr().equals(""))
                    s_titr = s_titr + " " + result.get(0).getSize_titr();

                tw_titr_name.setText(s_titr);


                if (!result.get(0).getHaraji().equals("no")){
                    img_haraji.setVisibility(View.VISIBLE);
                }

                if (result.get(0).getSpecial().equals("yes")){
                    img_special.setVisibility(View.VISIBLE);
                }



            }else {
                Toast.makeText(ProductActivity.this, "خطا در ارتباط با سرورد", Toast.LENGTH_SHORT).show();
            }
        }
    }






    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PictureFragment fragment = new PictureFragment();
            fragment.setUrl("http://beh-navaz.ir/jinni-api/"+urls.get(position));


//            recycler_p.smoothScrollToPosition((position == 0) ? 0: position-1);
            return fragment;
        }

        @Override
        public int getCount() {
            return urls.size();
        }
    }





}
