package ehsanfatahizadehgmail.com.jinni;


import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import ehsanfatahizadehgmail.com.jinni.adapters.ColorsAdapter;
import ehsanfatahizadehgmail.com.jinni.adapters.SizesAdapter;


public class NewProductActivity extends AppCompatActivity {



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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);


        btn_add_size = (Button) findViewById(R.id.button_add_size_new_p);
        edt_new_size = (EditText) findViewById(R.id.edittext_size_new_p);
        recycler_sizes = (RecyclerView) findViewById(R.id.recycler_size_new_p);



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



    public void delete_color_from_adapter(int c){
        adapter.delete_color(c);
    }


}
