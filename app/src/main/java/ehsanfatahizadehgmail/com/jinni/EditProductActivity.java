package ehsanfatahizadehgmail.com.jinni;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ehsanfatahizadehgmail.com.jinni.adapters.CategoryListAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.ColorsAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PropertyAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.SizesAdapter;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import ehsanfatahizadehgmail.com.jinni.models.Products;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {

    ProgressDialog dialog;
    ApiInterface apis;
    RetrofitSetting retrofit;


    List<Products> list_p;


    EditText edt_code , edt_mahiat , edt_jensiat , edt_brand , edt_model , edt_vizhegi_titr , edt_size_titr , edt_tozihat;


    List<String> final_color_list;
    TextView tw_colors;
    ColorsAdapter adapter;


    Button btn_add_size;
    EditText edt_new_size;
    RecyclerView recycler_sizes;
    SizesAdapter adapter_size;

    TextView tw_choose_category;
    CategoryListAdapter adapter_categories;



    Button btn_add_property;
    EditText edt_property_descrip , edt_property_properties;
    RecyclerView recycler_property;
    PropertyAdapter adapter_property;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        list_p = new ArrayList<>();
        list_p = ProductActivity.list_p;



        dialog = new ProgressDialog(EditProductActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");
        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();




        edt_code = (EditText) findViewById(R.id.edittext_code_new_p_edit_p);
        edt_mahiat = (EditText) findViewById(R.id.mahiat_edittext_new_p_edit_p);
        edt_jensiat = (EditText) findViewById(R.id.jensiat_edittext_new_p_edit_p);
        edt_brand = (EditText) findViewById(R.id.brand_edittext_new_p_edit_p);
        edt_model = (EditText) findViewById(R.id.model_edittext_new_p_edit_p);
        edt_vizhegi_titr = (EditText) findViewById(R.id.special_property_edittext_new_p_edit_p);
        edt_size_titr = (EditText) findViewById(R.id.size_titr_edittext_new_p_edit_p);
        edt_tozihat = (EditText) findViewById(R.id.description_edittext_new_p_edit_p);


        tw_colors = (TextView) findViewById(R.id.colors_new_p_edit_p);
        adapter = new ColorsAdapter(EditProductActivity.this);


        tw_choose_category = (TextView) findViewById(R.id.add_to_category_edittext_new_p_edit_p);



        btn_add_property = (Button) findViewById(R.id.button_add_property_edit_p);
        edt_property_properties = (EditText) findViewById(R.id.edt_table_properties_edit_p);
        edt_property_descrip = (EditText) findViewById(R.id.edt_table_description_edit_p);
        recycler_property = (RecyclerView) findViewById(R.id.recyclerview_property__table_edit_p);
        recycler_property.setLayoutManager(new LinearLayoutManager(EditProductActivity.this));
        adapter_property = new PropertyAdapter(EditProductActivity.this);
        recycler_property.setAdapter(adapter_property);





        btn_add_size = (Button) findViewById(R.id.button_add_size_new_p_edit_p);
        edt_new_size = (EditText) findViewById(R.id.edittext_size_new_p_edit_p);
        recycler_sizes = (RecyclerView) findViewById(R.id.recycler_size_new_p_edit_p);
        adapter_size = new SizesAdapter(EditProductActivity.this , 1);
        recycler_sizes.setLayoutManager(new LinearLayoutManager(EditProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
        recycler_sizes.setAdapter(adapter_size);

        adapter_size.setOnItemClickListener(new SizesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String size) {
                adapter_size.delete_size(position);
            }
        });
        btn_add_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = edt_new_size.getText().toString().trim();
                if (!size.equals("")){
                    adapter_size.add_size(size);
                }
            }
        });
        tw_choose_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_choose_category, null);
                dialogBuilder.setView(dialogView);

                RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recyclerview_row_choose_category);
                TextView tw = (TextView) dialogView.findViewById(R.id.path_row_choose_category);
                adapter_categories = new CategoryListAdapter(EditProductActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditProductActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter_categories);

                new getCategories(list_p.get(0).getMobile() , "0").execute();

                final AlertDialog alertDialog = dialogBuilder.create();

                adapter_categories.setOnItemClickListener(new CategoryListAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, CategoriesList categoriesList) {

                        if (categoriesList.num_of_subs.equals("0")){
                            tw_choose_category.setText(categoriesList.getName());
                            tw_choose_category.setTag(categoriesList.getId());
                            alertDialog.dismiss();
                        }else {
                            new getCategories(list_p.get(0).getMobile(), categoriesList.getId()).execute();
                        }
//                        s_title = categoriesList.getName();
//                        path_father.add(categoriesList.getParent_category());
//                        path__current_names.add(categoriesList.getName());
//                        path_current.add(categoriesList.getId());
                    }
                });



//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();
            }
        });

        btn_add_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String properties = edt_property_properties.getText().toString().trim();
                String description = edt_property_descrip.getText().toString().trim();

                if (!description.equals("") && !properties.equals("")){
                    adapter_property.add_row(properties , description);
                }

            }
        });

        adapter_property.setOnItemClickListener(new PropertyAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String properties, String descrip) {
                adapter_property.delete_row(position);
            }
        });








        adapter_property.add_list(list_p.get(0).getProperty_name() , list_p.get(0).getProperty_description());

        tw_choose_category.setText(list_p.get(0).getCategory_name());
        adapter_size.add_list(list_p.get(0).getSizes());

        adapter.add_list(list_p.get(0).getColors());

        edt_code.setText(list_p.get(0).getCode());
        edt_mahiat.setText(list_p.get(0).getMahiat_titr());
        edt_jensiat.setText(list_p.get(0).getJensiat_titr());
        edt_brand.setText(list_p.get(0).getBrand_titr());
        edt_model.setText(list_p.get(0).getModel_titr());
        edt_vizhegi_titr.setText(list_p.get(0).getVizhegi_titr());
        edt_size_titr.setText(list_p.get(0).getSize_titr());
        edt_tozihat.setText(list_p.get(0).getTozihat());


















        tw_colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_colors, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();

//                List<String> colors_list = new ArrayList<>();
                RecyclerView recycler_colors = (RecyclerView) dialogView.findViewById(R.id.recycler_colors);
                Button submit_colors = (Button)dialogView.findViewById(R.id.submit_colors_alert);

                recycler_colors.setLayoutManager(new LinearLayoutManager(EditProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
                recycler_colors.setAdapter(adapter);




                submit_colors.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                adapter.setOnItemClickListener(new ColorsAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, String color) {
                        adapter.delete_color(position);
                    }
                });











                final CircleImageView c1_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_1);
                final CircleImageView c1_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_2);
                final CircleImageView c1_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_3);
                final CircleImageView c1_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_4);
                final CircleImageView c1_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_5);
                final CircleImageView c1_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_6);
                final CircleImageView c1_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_7);
                final CircleImageView c1_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_8);
                final CircleImageView c1_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_9);
                final CircleImageView c1_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_10);
                final CircleImageView c1_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_11);
                final CircleImageView c1_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_12);
                final CircleImageView c1_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_1_13);
                final CircleImageView c2_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_1);
                final CircleImageView c2_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_2);
                final CircleImageView c2_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_3);
                final CircleImageView c2_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_4);
                final CircleImageView c2_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_5);
                final CircleImageView c2_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_6);
                final CircleImageView c2_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_7);
                final CircleImageView c2_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_8);
                final CircleImageView c2_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_9);
                final CircleImageView c2_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_10);
                final CircleImageView c2_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_11);
                final CircleImageView c2_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_12);
                final CircleImageView c2_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_2_13);
                final CircleImageView c3_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_1);
                final CircleImageView c3_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_2);
                final CircleImageView c3_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_3);
                final CircleImageView c3_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_4);
                final CircleImageView c3_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_5);
                final CircleImageView c3_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_6);
                final CircleImageView c3_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_7);
                final CircleImageView c3_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_8);
                final CircleImageView c3_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_9);
                final CircleImageView c3_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_10);
                final CircleImageView c3_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_11);
                final CircleImageView c3_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_12);
                final CircleImageView c3_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_3_13);
                final CircleImageView c4_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_1);
                final CircleImageView c4_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_2);
                final CircleImageView c4_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_3);
                final CircleImageView c4_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_4);
                final CircleImageView c4_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_5);
                final CircleImageView c4_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_6);
                final CircleImageView c4_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_7);
                final CircleImageView c4_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_8);
                final CircleImageView c4_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_9);
                final CircleImageView c4_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_10);
                final CircleImageView c4_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_11);
                final CircleImageView c4_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_12);
                final CircleImageView c4_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_4_13);
                final CircleImageView c5_1 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_1);
                final CircleImageView c5_2 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_2);
                final CircleImageView c5_3 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_3);
                final CircleImageView c5_4 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_4);
                final CircleImageView c5_5 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_5);
                final CircleImageView c5_6 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_6);
                final CircleImageView c5_7 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_7);
                final CircleImageView c5_8 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_8);
                final CircleImageView c5_9 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_9);
                final CircleImageView c5_10 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_10);
                final CircleImageView c5_11 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_11);
                final CircleImageView c5_12 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_12);
                final CircleImageView c5_13 = (CircleImageView)dialogView.findViewById(R.id.ccolor_5_13);





                c1_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_1.getTag().toString());
                    }
                });

                c1_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_2.getTag().toString());
                    }
                });
                c1_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_3.getTag().toString());
                    }
                });
                c1_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_4.getTag().toString());
                    }
                });
                c1_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_5.getTag().toString());
                    }
                });
                c1_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_6.getTag().toString());
                    }
                });
                c1_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_7.getTag().toString());
                    }
                });
                c1_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_8.getTag().toString());
                    }
                });

                c1_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_9.getTag().toString());
                    }
                });
                c1_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_10.getTag().toString());
                    }
                });
                c1_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_11.getTag().toString());
                    }
                });
                c1_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_12.getTag().toString());
                    }
                });
                c1_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c1_13.getTag().toString());
                    }
                });
                c2_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_1.getTag().toString());
                    }
                });
                c2_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_2.getTag().toString());
                    }
                });

                c2_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_3.getTag().toString());
                    }
                });
                c2_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_4.getTag().toString());
                    }
                });
                c2_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_5.getTag().toString());
                    }
                });
                c2_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_6.getTag().toString());
                    }
                });
                c2_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_7.getTag().toString());
                    }
                });
                c2_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_8.getTag().toString());
                    }
                });
                c2_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_9.getTag().toString());
                    }
                });

                c2_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_10.getTag().toString());
                    }
                });
                c2_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_11.getTag().toString());
                    }
                });
                c2_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_12.getTag().toString());
                    }
                });
                c2_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c2_13.getTag().toString());
                    }
                });
                c3_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_1.getTag().toString());
                    }
                });
                c3_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_2.getTag().toString());
                    }
                });
                c3_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_3.getTag().toString());
                    }
                });

                c3_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_4.getTag().toString());
                    }
                });
                c3_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_5.getTag().toString());
                    }
                });
                c3_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_6.getTag().toString());
                    }
                });
                c3_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_7.getTag().toString());
                    }
                });
                c3_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_8.getTag().toString());
                    }
                });
                c3_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_9.getTag().toString());
                    }
                });
                c3_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_10.getTag().toString());
                    }
                });

                c3_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_11.getTag().toString());
                    }
                });
                c3_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_12.getTag().toString());
                    }
                });
                c3_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c3_13.getTag().toString());
                    }
                });
                c4_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_1.getTag().toString());
                    }
                });
                c4_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_2.getTag().toString());
                    }
                });
                c4_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_3.getTag().toString());
                    }
                });
                c4_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_4.getTag().toString());
                    }
                });

                c4_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_5.getTag().toString());
                    }
                });
                c4_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_6.getTag().toString());
                    }
                });
                c4_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_7.getTag().toString());
                    }
                });
                c4_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_8.getTag().toString());
                    }
                });
                c4_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_9.getTag().toString());
                    }
                });
                c4_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_10.getTag().toString());
                    }
                });
                c4_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_11.getTag().toString());
                    }
                });

                c4_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_12.getTag().toString());
                    }
                });
                c4_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c4_13.getTag().toString());
                    }
                });
                c5_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_1.getTag().toString());
                    }
                });
                c5_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_2.getTag().toString());
                    }
                });
                c5_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_3.getTag().toString());
                    }
                });
                c5_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_4.getTag().toString());
                    }
                });
                c5_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_5.getTag().toString());
                    }
                });

                c5_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_6.getTag().toString());
                    }
                });
                c5_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_7.getTag().toString());
                    }
                });
                c5_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_8.getTag().toString());
                    }
                });
                c5_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_9.getTag().toString());
                    }
                });
                c5_10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_10.getTag().toString());
                    }
                });
                c5_11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_11.getTag().toString());
                    }
                });
                c5_12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_12.getTag().toString());
                    }
                });

                c5_13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.add_color(c5_13.getTag().toString());
                    }
                });





                alertDialog.show();
            }
        });







    }






    List<CategoriesList> list = new ArrayList<CategoriesList>();

    public class getCategories extends AsyncTask {

        String mobile;
        String parent_category;
        public getCategories(String mobile , String parent_category){
            this.mobile = mobile;
            this.parent_category = parent_category;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Call<List<CategoriesList>> call = apis.getCategories(mobile , parent_category);

            try {
                Response<List<CategoriesList>> response = call.execute();

                if(!response.isSuccessful()) {
                    Toast.makeText(EditProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
                    return null;
                }
                list = response.body();






            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

//            int num = path_father.size();
//            Log.d("size of list " , String.valueOf(list.size()));
            if (!list.isEmpty()) {
                adapter_categories.add_list(list);
            }
            dialog.dismiss();

//            if (num != 0){
//                title.setText(path__current_names.get(path__current_names.size()-1));
//                back_cate.setVisibility(View.VISIBLE);
//            }else {
//                title.setText("دسته بندی اصلی");
//                back_cate.setVisibility(View.GONE);
//            }


        }
    }

















}
