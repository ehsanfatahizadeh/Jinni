package ehsanfatahizadehgmail.com.jinni;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import ehsanfatahizadehgmail.com.jinni.adapters.CategoryListAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.ColorsAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PhotosProductAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PriceTedadAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.PropertyAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.SizesAdapter;
import ehsanfatahizadehgmail.com.jinni.amounts.Constants;
import ehsanfatahizadehgmail.com.jinni.libs.ImageOrientation;
import ehsanfatahizadehgmail.com.jinni.models.CategoriesList;
import ehsanfatahizadehgmail.com.jinni.rest.ApiInterface;
import ehsanfatahizadehgmail.com.jinni.rest.RetrofitSetting;
import retrofit2.Call;
import retrofit2.Response;


public class NewProductActivity extends AppCompatActivity {

    ProgressDialog dialog;
    ApiInterface apis;
    RetrofitSetting retrofit;

    EditText mahiat;
    EditText jensiat;
    EditText brand;
    EditText model;
    EditText special_property;
    EditText size_titr;

    TextView colors;
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


    CheckBox checkbox_haraji , checkbox_special;
    EditText old_price;



    Button btn_add_prices;
    EditText edt_az_tedad;
    EditText edt_ta_tedad;
    EditText edt_gheymat_tedad;
    RecyclerView recycler_tedad;
    PriceTedadAdapter adapter_tedad;


    RecyclerView recycler_photos;
    Button btn_add_photo;
    Intent GalIntent;;
    PhotosProductAdapter adapter_photos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);


        tw_choose_category = (TextView) findViewById(R.id.add_to_category_edittext_new_p);
        btn_add_size = (Button) findViewById(R.id.button_add_size_new_p);
        edt_new_size = (EditText) findViewById(R.id.edittext_size_new_p);
        recycler_sizes = (RecyclerView) findViewById(R.id.recycler_size_new_p);



        recycler_photos = (RecyclerView) findViewById(R.id.recycler_photos_new_p);
        btn_add_photo = (Button) findViewById(R.id.button_add_pictures);
        recycler_photos.setLayoutManager(new LinearLayoutManager(NewProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
        adapter_photos = new PhotosProductAdapter(NewProductActivity.this);
        recycler_photos.setAdapter(adapter_photos);


        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(NewProductActivity.this);
                ab.setMessage("انتخاب عکس");
                ab.setPositiveButton("دوربین", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CameraOpen();
                    }
                });
                ab.setNegativeButton("گالری", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GalleryOpen();
                    }
                });
                ab.show();
            }
        });



        btn_add_prices = (Button) findViewById(R.id.button_add_row_price_table);
        edt_az_tedad = (EditText) findViewById(R.id.edt_new_p_az_row);
        edt_ta_tedad = (EditText) findViewById(R.id.edt_new_p_ta_row);
        edt_gheymat_tedad = (EditText) findViewById(R.id.edt_new_p_price_row);
        recycler_tedad = (RecyclerView) findViewById(R.id.recyclerview_big_price);
        recycler_tedad.setLayoutManager(new LinearLayoutManager(NewProductActivity.this));
        adapter_tedad = new PriceTedadAdapter(NewProductActivity.this);
        recycler_tedad.setAdapter(adapter_tedad);


        btn_add_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String az = edt_az_tedad.getText().toString().trim();
                String ta = edt_ta_tedad.getText().toString().trim();
                String prc = edt_gheymat_tedad.getText().toString().trim();

                if (!az.equals("")  && !prc.equals("")){

                    if (ta.equals("")){
                        ta = "به بالا";
                    }

                    adapter_tedad.add_row(az , ta , prc);

                }



            }
        });




        checkbox_haraji = (CheckBox) findViewById(R.id.checkbox_cheap);
        checkbox_special = (CheckBox) findViewById(R.id.checkbox_special);
        old_price = (EditText) findViewById(R.id.old_price_new_p);



        checkbox_haraji.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    old_price.setVisibility(View.VISIBLE);
                }else{
                    old_price.setVisibility(View.GONE);
                }
            }
        });


        btn_add_property = (Button) findViewById(R.id.button_add_property);
        edt_property_properties = (EditText) findViewById(R.id.edt_table_properties);
        edt_property_descrip = (EditText) findViewById(R.id.edt_table_description);
        recycler_property = (RecyclerView) findViewById(R.id.recyclerview_property__table);
        recycler_property.setLayoutManager(new LinearLayoutManager(NewProductActivity.this));
        adapter_property = new PropertyAdapter(NewProductActivity.this);
        recycler_property.setAdapter(adapter_property);




        dialog = new ProgressDialog(NewProductActivity.this);
        dialog.setMessage("لطفا کمی صبر کنید");
        retrofit = new RetrofitSetting();
        apis = retrofit.getApiInterface();


        SharedPreferences sh = getSharedPreferences(Constants.SHARED_USER , MODE_PRIVATE);
        final String mobile = sh.getString("mobile" , null);




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




        adapter_size = new SizesAdapter(NewProductActivity.this);
        recycler_sizes.setLayoutManager(new LinearLayoutManager(NewProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
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



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_choose_category, null);
                dialogBuilder.setView(dialogView);

                RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recyclerview_row_choose_category);
                TextView tw = (TextView) dialogView.findViewById(R.id.path_row_choose_category);
                adapter_categories = new CategoryListAdapter(NewProductActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewProductActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter_categories);

                new getCategories(mobile , "0").execute();

                final AlertDialog alertDialog = dialogBuilder.create();

                adapter_categories.setOnItemClickListener(new CategoryListAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, CategoriesList categoriesList) {

                        if (categoriesList.num_of_subs.equals("0")){
                            tw_choose_category.setText(categoriesList.getName());
                            alertDialog.dismiss();
                        }else {
                            new getCategories(mobile, categoriesList.getId()).execute();
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







        colors = (TextView) findViewById(R.id.colors_new_p);

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewProductActivity.this);
                LayoutInflater inflater =getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_colors, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();

//                List<String> colors_list = new ArrayList<>();
                RecyclerView recycler_colors = (RecyclerView) dialogView.findViewById(R.id.recycler_colors);
                adapter = new ColorsAdapter(NewProductActivity.this);
                recycler_colors.setLayoutManager(new LinearLayoutManager(NewProductActivity.this , LinearLayoutManager.HORIZONTAL , false));
                recycler_colors.setAdapter(adapter);






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


    List<CategoriesList> list;

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
                    Toast.makeText(NewProductActivity.this, "errrror", Toast.LENGTH_SHORT).show();
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
            Log.d("size of list " , String.valueOf(list.size()));
            adapter_categories.add_list(list);
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
























    Uri imageUri;
    private void CameraOpen() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.INT_CAMERA);
            }
        }
        else {
            dispatchTakePictureIntent();
        }
    }

    private void GalleryOpen() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){

                GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);

            } else {

                ActivityCompat.requestPermissions(NewProductActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.INT_PICK_GALLERY);
            }
        }
        else {
            GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.INT_CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                dispatchTakePictureIntent();

            } else {
                Toast.makeText(NewProductActivity.this, "دسترسی داده نشد", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constants.INT_PICK_GALLERY) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),Constants.INT_PICK_GALLERY);


            } else {

                Toast.makeText(NewProductActivity.this, "دسترسی خواندن عکس از حافظه داده نشد!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    String encoded_image;
    Uri final_image;
    Uri selectedImage;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.INT_CAMERA && resultCode == RESULT_OK){


            getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getContentResolver();
            int orientation = 0;
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(photoFile.getPath());
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
            } catch(Exception e) {
                Log.e("logtagAddAdv", e.toString());
            }
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    bitmap = bitmap;
            }

            try{
                Intent CropIntent;
                CropIntent = new Intent("com.android.camera.action.CROP");
                CropIntent.setDataAndType(getImageUri(bitmap),"image/*");
                CropIntent.putExtra("crop","true");
                CropIntent.putExtra("outputX",480);
                CropIntent.putExtra("outputY",480);
                CropIntent.putExtra("aspectX",8);
                CropIntent.putExtra("aspectY",8);
                CropIntent.putExtra("scaleUpIfNeeded",true);

                String rndm = getRandomString();
                File f = new File(Environment.getExternalStorageDirectory()+"/"+rndm+"/");
                final_image = Uri.fromFile(f);
                CropIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, final_image);
                startActivityForResult(CropIntent, Constants.INT_CROP);
            }
            catch (ActivityNotFoundException anfe) {
                String errorMessage = "Your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        else if (requestCode == Constants.INT_CROP && resultCode == RESULT_OK){
            adapter_photos.add_row(final_image);
//            img.setImageURI(final_image);
            encoded_image = getEncodedImage(final_image);
            Log.d("photo is  : " , " "+encoded_image);
        }
        else if (requestCode == Constants.INT_PICK_GALLERY && resultCode == RESULT_OK){

            try {
                Uri filePath = data.getData();
                //..First convert the Image to the allowable size so app do not throw Memory_Out_Bound Exception
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath), null, options);
                int resolution = 500;
                options.inSampleSize = calculateInSampleSize(options, resolution  , resolution);
                options.inJustDecodeBounds = false;
                Bitmap bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath), null, options);
                //...Now You have the 'bitmap' to rotate....
                //...Rotate the bitmap to its original Orientation...
                bitmap = ImageOrientation.modifyOrientation(getApplicationContext(),bitmap1,filePath);
                try{
                    Intent CropIntent;
                    CropIntent = new Intent("com.android.camera.action.CROP");
                    CropIntent.setDataAndType(getImageUri(bitmap),"image/*");
                    CropIntent.putExtra("crop","true");
                    CropIntent.putExtra("outputX",480);
                    CropIntent.putExtra("outputY",480);
                    CropIntent.putExtra("aspectX",8);
                    CropIntent.putExtra("aspectY",8);
                    CropIntent.putExtra("scaleUpIfNeeded",true);

                    String rndm = getRandomString();
                    File f = new File(Environment.getExternalStorageDirectory()+"/"+rndm+"/");
                    final_image = Uri.fromFile(f);
                    CropIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, final_image);
                    startActivityForResult(CropIntent, Constants.INT_CROP);


                }catch (ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                Log.d("Image_exception",e.toString());
            }
        }

    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    String currentPhotoPath;
    File image;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    File photoFile;
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(NewProductActivity.this.getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(NewProductActivity.this, "ehsanfatahizadehgmail.com.jinni.fileprovider", photoFile);
                    selectedImage = imageUri;
                } else {
                    imageUri = Uri.fromFile(photoFile);
                    selectedImage = imageUri;
                }
                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, Constants.INT_CAMERA);
            }
        }
    }

    Bitmap bitmap;

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    protected String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


    public String getEncodedImage(Uri uri){
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver() ,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }







}
