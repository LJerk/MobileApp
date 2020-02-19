package Helper.hand;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    public TextView number;
    public TextView user;
    public TextView information;
    private Button button;
    private EditText name;
    private EditText phone;
    private EditText description;
    GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initNavigation();

        user = findViewById(R.id.name);
        number = findViewById(R.id.surname);
        information = findViewById(R.id.info);

        name = findViewById(R.id.editText1);
        phone = findViewById(R.id.editText2);
        description = findViewById(R.id.editText3);

        button = findViewById(R.id.buttonSend);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setText(name.getText());
                number.setText(phone.getText());
                information.setText(description.getText());
            }
        });

        setInfo();
    }


    private void initNavigation() {
        drawerLayout = findViewById(R.id.draw);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_close , R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigate);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.support:
                        goToSupport();
                        break;
                    case R.id.cash:
                        goToCash();
                        break;
                    case R.id.map:
                        goToMap();
                        break;
                    case R.id.exit:
                        signOut();
                        break;
                }
                return true;
            }

        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Profile.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profile.this, LoginIN.class));
                        finish();
                    }
                });
    }

    private void setInfo() {
        user.setText("Вася Буйный");
        number.setText("+7(953)584 24 21");
        information.setText("Уважаемый человек с высоким достатком, любящий муж отец и космонавт, люблю рыбалку и прогулки под солнцем");
    }



    private void goToCash(){
        Intent intent = new Intent(Profile.this,Donate.class);
        startActivity(intent);
    }
    private void goToSupport(){
        Intent intent = new Intent(Profile.this, Support.class);
        startActivity(intent);
    }
    private void goToMap(){
        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
    }

}
