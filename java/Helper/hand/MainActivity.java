package Helper.hand;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.geojson.GeoJson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity   {

    private MapView mapView;
    private DrawerLayout drawerLayout;

    private double lng;
    private double lat;
    private MapboxMap mapboxMap;
    private FloatingActionButton button;
    private FloatingActionButton button2;
    private FloatingActionButton button3;
    private LinearLayout layout;
    private EditText name;
    private EditText phone;
    private EditText cost;
    private EditText description;
    List<Marker> markers= new ArrayList<Marker>();
    private Marker marker = null;
    private Marker marker2 = null;
    private boolean flag = false;
    private String i;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);


        //SOME MAP STAFF)))))
        Mapbox.getInstance(this, "pk.eyJ1IjoibGplcmtqIiwiYSI6ImNrNWZvN3p0ZDBzdzAzZm1jOHF1eWN3Mm4ifQ.ztCHIPyU0n57sZOARTGFTA");
        setContentView(R.layout.activity_main);
        initNavigation();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        name = findViewById(R.id.editText1);
        phone = findViewById(R.id.editText2);
        cost = findViewById(R.id.editText3);
        description = findViewById(R.id.editText4);

        layout = findViewById(R.id.layout);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        MarkerOptions newmarker = new MarkerOptions();
                        MarkerOptions options = new MarkerOptions();
                       // options.icon(Icon int);
                        options.title("Вася Буйный" + "\n"+ "+7(953)584 24 21" + "\n"+ "Продам холодильник" + "\n"+ "500р");
                        options.position(new LatLng(55.345582, 86.119922));
                        mapboxMap.addMarker(options);

                        options.title("Саша Трезвый" + "\n"+ "+7(934)532 42 87" + "\n"+ "Куплю козу" + "\n"+ "500р");
                        options.position(new LatLng(55.348335, 86.087025));
                        mapboxMap.addMarker(options);

                        options.title("Галина Семёновна" + "\n"+ "+7(951)583 22 53" + "\n"+ "Уберите снег" + "\n"+ "500р");
                        options.position(new LatLng(55.351533, 86.079717));
                        mapboxMap.addMarker(options);
                        mapboxMap.addMarker(options);



                        mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                            @Override
                            public boolean onMapClick(@NonNull LatLng point) {

                                   if (marker != null) marker.remove();
                                   newmarker.title("я плавающая метка");
                                   marker = mapboxMap.addMarker(newmarker.position(point));

                                if(flag) {
                                    newmarker.title(name.getText().toString() + "\n" + phone.getText().toString() + "\n" + description.getText().toString() + "\n" + cost.getText().toString());
                                    newmarker.position(new LatLng(lat, lng));
                                    marker2 = mapboxMap.addMarker(newmarker.position(point));
                                    flag = false;
                                }

                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        layout.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Выберите местоположение", Toast.LENGTH_LONG).show();
                                        flag = true;

                                    }
                                });

                                return true;
                            }
                        });
                    }

                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.INVISIBLE);
            }
        });

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
                    case R.id.profile:
                        goToProfile();
                        break;
                    case R.id.cash:
                        goToCash();
                        break;
                    case R.id.support:
                        goToSupport();
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
                        Toast.makeText(MainActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginIN.class));
                        finish();
                    }
                });
    }

    private void goToCash(){
        Intent intent = new Intent(MainActivity.this,Donate.class);
        startActivity(intent);
    }
    private void goToProfile(){
        Intent intent = new Intent(MainActivity.this, Profile.class);
        startActivity(intent);
    }
    private void goToSupport(){
        Intent intent = new Intent(MainActivity.this, Support.class);
        startActivity(intent);
    }




    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
