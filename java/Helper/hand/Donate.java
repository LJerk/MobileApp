package Helper.hand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class Donate extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        initNavigation();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
                    case R.id.profile:
                        goToProfile();
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
                        Toast.makeText(Donate.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Donate.this, LoginIN.class));
                        finish();
                    }
                });
    }

    private void goToProfile(){
        Intent intent = new Intent(Donate.this,Profile.class);
        startActivity(intent);
    }
    private void goToSupport(){
        Intent intent = new Intent(Donate.this, Support.class);
        startActivity(intent);
    }
    private void goToMap(){
        Intent intent = new Intent(Donate.this, MainActivity.class);
        startActivity(intent);
    }
}
