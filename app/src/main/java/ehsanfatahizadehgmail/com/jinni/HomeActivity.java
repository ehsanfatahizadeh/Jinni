package ehsanfatahizadehgmail.com.jinni;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;

import org.w3c.dom.Text;

import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;

public class HomeActivity extends BaseActivity {



    DrawerLayout drawerLayout;
    TextView txt_signUp;
    PrintView printView_drawer;
    TextView list_categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView new_product = (TextView) findViewById(R.id.new_product_home);
        txt_signUp = (TextView) findViewById(R.id.sign_up_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        PrintView printView_drawer = (PrintView) findViewById(R.id.printview_home_drawer);

        list_categories = (TextView) findViewById(R.id.new_product_list_of_categories);




        list_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , CategoriesListActivity.class));
            }
        });

        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , SignUpActivity.class));
            }
        });

        new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(HomeActivity.this , NewProductActivity.class));
            }
        });




        printView_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_close_drawer();
            }
        });







    }




    public void open_close_drawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawers();
        }else {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }


}
