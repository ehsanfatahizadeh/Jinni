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

import ehsanfatahizadehgmail.com.jinni.libs.BaseActivity;

public class HomeActivity extends BaseActivity {



    DrawerLayout drawerLayout;
    TextView txt_signUp;
    PrintView printView_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt_signUp = (TextView) findViewById(R.id.sign_up_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        PrintView printView_drawer = (PrintView) findViewById(R.id.printview_home_drawer);


        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this , SignUpActivity.class));
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
