package com.example.michael.thegardenapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMapLongClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,AdapterView.OnItemSelectedListener {


    private GoogleApiClient mGoogleApiClient;
    private LocationSource.OnLocationChangedListener mListener;
    private LocationManager locationManager;
    private SupportMapFragment fragment;
    private Circle mCircle;

    private ArrayList<Marker> landMarkersToClear = new ArrayList<Marker>();
    ArrayList<Marker> TreeMarkersToClear = new ArrayList<Marker>();
    public static final String TAG = MapsActivity.class.getSimpleName();
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;


    private GoogleMap mMap;
    EditText addressEditText;
    String title12;
    LatLng position1;
    SharedPreferences sharedPreferences,sharedPreferences1;
    int locationCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting number of locations already stored
        locationCount = sharedPreferences.getInt("locationCount", 0);

        sharedPreferences1 =  getSharedPreferences("title", 0);


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        List categories = new ArrayList();
        categories.add("Trees");
        categories.add("Landmarks");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }

    //methods used to change the map type
    public void onNormalMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void onSatelliteMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void onTerrainMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void onHybridMap(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    //Opens the homemenu from the map activity
    public void returnHome(View view) {
        Intent intent = new Intent(MapsActivity.this, MapInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    //OnMapReadyCallback

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);

        // If locations are already saved
        if (locationCount != 0) {

            String lat = "";
            String lng = "";
            String title = "";

            // Iterating through all the locations stored
            for (int i = 0; i < locationCount; i++) {

                // Getting the latitude of the i-th location
                lat = sharedPreferences.getString("lat" + i, "0");

                // Getting the longitude of the i-th location
                lng = sharedPreferences.getString("lng" + i, "0");

               title = sharedPreferences.getString("title" + i, "0");


                //Toast.makeText(this, lat + "," + lng, Toast.LENGTH_LONG).show();

                double lat3 = Double.valueOf(lat).doubleValue();
                double lng3 = Double.valueOf(lng).doubleValue();
                String title1 = Double.valueOf(title).toString();

                position1 = new LatLng(lat3, lng3);

                drawMarker(position1,title1);
            }

        }


        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
        //    @Override
        //   public void onMapLongClick(LatLng latLng) {
        //      Intent edit = new Intent(MapsActivity.this, EditActivity.class);
        //      edit.putExtra("location", latLng);
        //      MapsActivity.this.startActivityForResult(edit, EDIT_REQUEST);
        // }
        //});


        mMap.getUiSettings().setCompassEnabled(true);
        //initially setting the map to Normal
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        if (location != null) {
            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
            //       new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    //
                    .target(new LatLng(51.596071, -4.002886))
                    .zoom(13)
                    .tilt(67.5f)
                    .bearing(314)
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            //Methods used to add markers to the map with a title and description
            /**   mMap.addMarker(new MarkerOptions()
             .position(new LatLng(51.596071, -4.002886))
             .title("Clyne Gardens")
             .snippet("Welcome to the garden")
             .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

             mMap.addMarker(new MarkerOptions()
             .position(new LatLng(51.595816, -4.001575))
             .title("The Tower")
             .snippet("Generals old tower")
             .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

             mMap.addMarker(new MarkerOptions()
             .position(new LatLng(51.595283, -4.005319))
             .title("Maple Tree")
             .snippet("187 year old tree.")
             .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

             **/
            Log.d(TAG, "onMapReady() called with");
            mMap = googleMap;
            MapsInitializer.initialize(this);
            addCustomMarker();
        }
    }

    /**
     * @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
     * super.onActivityResult(requestCode, resultCode, data);
     * switch(requestCode) {
     * case (EDIT_REQUEST) : {
     * if (resultCode == Activity.RESULT_OK) {
     * MarkerOptions markerOptions = data.getParcelableExtra("marker");
     * mMap.addMarker(markerOptions);
     * }
     * break;
     * }
     * }
     * }
     **/

    private void addCustomMarker() {
        Log.d(TAG, "addCustomMarker()");
        if (mMap == null) {
            return;
        }
        // adding a marker on map with image from  drawable
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(MapsActivity.this, MainViewPageView.class);
        startActivity(intent);
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "You have selected : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();

        String selectedItem = parent.getItemAtPosition(position).toString();
        if (selectedItem.equals("Landmarks")) {
            landMarkMarkers();
        } else if (selectedItem.equals("Trees")) {
            TreeMarkers();
        }
    }

    private void landMarkMarkers() {

        if (mMap != null) {

        } else {
            Log.d("Maps: :", "mMap is null");
        }


        Marker Marker1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59757, -4.00102))
                .title("Entrance 1")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59679, -4.00678))
                .title("Entrance 2")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59409, -4.00737))
                .title("Entrance 3")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.5949, -4.00272))
                .title("Entrance 4")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59606, -3.99766))
                .title("Entrance 5 Main")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59608, -3.99806))
                .title("Sign Post")
                .snippet("Beach entrance sign post")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.addCircle(new CircleOptions()
                .center(new LatLng(51.59608, -3.99806)).radius(4)
                .fillColor(Color.parseColor("#0E9231")));


        Marker Marker7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59698, -4.00113))
                .title("Old man face tree")
                .snippet("Seating with tree beard.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        Marker Marker8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59701, -4.00103))
                .title("Animal Seating")
                .snippet("Fun animal seating for the young ones.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        Marker Marker9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59688, -4.00166))
                .title("Stone Bridge")
                .snippet("a stone bridge.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59688, -4.00166))
                .title("The Gazebo")
                .snippet("Used to view incoming ships entering swansea bay.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59714, -4.00443))
                .title("The Gazebo")
                .snippet("Used to view incoming ships entering swansea bay.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59622, -4.0068))
                .title("Dog Graves")
                .snippet("Small dog graves to commemorate admirals family pets.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59495, -4.00739))
                .title("The joy cottage")
                .snippet("Miniature cottage used for relaxation and education.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59455, -4.00716))
                .title("Japanese Bridge ")
                .snippet("A lush bridge build to show off the admirals wealth. ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59462, -4.00704))
                .title("Japanese Pond ")
                .snippet("A lush japanese pond. ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59462, -4.00704))
                .title("Japanese Pond ")
                .snippet("A lush japanese pond ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.5961, -4.00069))
                .title("The Tower ")
                .snippet("Admirals Tower")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59631, -4.00134))
                .title("Brock Hole Bridge ")
                .snippet("a brock hole bridge")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59631, -4.00134))
                .title("Brock Hole Bridge ")
                .snippet("a brock hole bridge")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59687, -4.00223))
                .title("The Castle")
                .snippet("The Admirals Castle")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59597, -4.00011))
                .title("Waterfall Lake")
                .snippet("Waterfall lake located in the centre of Bog Garden")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.5958, -4.00047))
                .title("Admiral Bridge ")
                .snippet("Admirals Bridge")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59624, -3.99886))
                .title("Friends Bridge")
                .snippet("Friends bridge")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59639, -4.00036))
                .title("Tower Bridge")
                .snippet("Another one of the many bridges located in the garden.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59608, -3.99766))
                .title("The Lodge")
                .snippet("Another one of the many bridges located in the garden.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Marker Marker26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59633, -3.99766))
                .title("Mill Bridge")
                .snippet("Another one of the many bridges located in the garden.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        landMarkersToClear.add(Marker1);
        landMarkersToClear.add(Marker2);
        landMarkersToClear.add(Marker3);
        landMarkersToClear.add(Marker4);
        landMarkersToClear.add(Marker5);
        landMarkersToClear.add(Marker6);
        landMarkersToClear.add(Marker7);
        landMarkersToClear.add(Marker8);
        landMarkersToClear.add(Marker9);
        landMarkersToClear.add(Marker10);
        landMarkersToClear.add(Marker11);
        landMarkersToClear.add(Marker12);
        landMarkersToClear.add(Marker13);
        landMarkersToClear.add(Marker14);
        landMarkersToClear.add(Marker15);
        landMarkersToClear.add(Marker16);
        landMarkersToClear.add(Marker17);
        landMarkersToClear.add(Marker18);
        landMarkersToClear.add(Marker19);
        landMarkersToClear.add(Marker20);
        landMarkersToClear.add(Marker21);
        landMarkersToClear.add(Marker22);
        landMarkersToClear.add(Marker23);
        landMarkersToClear.add(Marker24);
        landMarkersToClear.add(Marker25);
        landMarkersToClear.add(Marker26);


    }

    private void TreeMarkers() {

        if (mMap != null) {

        } else {
            Log.d("Maps: :", "mMap is null");
        }

        Marker Marker27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59757, -4.00102))
                .title("Entrance 1")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59679, -4.00678))
                .title("Entrance 2")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker29 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59409, -4.00737))
                .title("Entrance 3")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker30 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.5949, -4.00272))
                .title("Entrance 4")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker31 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59606, -3.99766))
                .title("Entrance 5 Main")
                .snippet("Entrance to the park.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        Marker Marker32 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59626, -3.99834))
                .title("Star Magnolia")
                .snippet("find fact on this tree.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker33 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59627, -3.99831))
                .title("Chinese Holly Grape")
                .snippet("find fact on the tree")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker34 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59707, -4.00077))
                .title("Waterers bird cherry")
                .snippet("find fact on the tree")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker35 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59685, -4.00169))
                .title("Indian Horse Chestnut")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker36 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59693, -4.00222))
                .title("Monterey Cypress")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker37 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59655, -4.00354))
                .title("Common Lime Tree")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker38 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59667, -4.00586))
                .title("Turkey Oak")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker39 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59561, -4.00683))
                .title("Silver Birch")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59561, -4.00683))
                .title("Silver Birch")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker40 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59455, -4.00716))
                .title("Hanker chief")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker41 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59514, -4.00442))
                .title("Western Red Cedar")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker42 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59576, -4.00095))
                .title("Japanese Red Cedar")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker43 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59643, -4.399985))
                .title("Sessile Oak")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker44 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.59629, -3.99903))
                .title("Blue Atlas Cedar")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Marker Marker45 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.5965, -3.9997))
                .title("Tulip Tree")
                .snippet("Tree information here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


        TreeMarkersToClear.add(Marker27);
        TreeMarkersToClear.add(Marker28);
        TreeMarkersToClear.add(Marker29);
        TreeMarkersToClear.add(Marker30);
        TreeMarkersToClear.add(Marker31);
        TreeMarkersToClear.add(Marker32);
        TreeMarkersToClear.add(Marker33);
        TreeMarkersToClear.add(Marker34);
        TreeMarkersToClear.add(Marker35);
        TreeMarkersToClear.add(Marker36);
        TreeMarkersToClear.add(Marker37);
        TreeMarkersToClear.add(Marker38);
        TreeMarkersToClear.add(Marker39);
        TreeMarkersToClear.add(Marker40);
        TreeMarkersToClear.add(Marker41);
        TreeMarkersToClear.add(Marker42);
        TreeMarkersToClear.add(Marker43);
        TreeMarkersToClear.add(Marker44);
        TreeMarkersToClear.add(Marker45);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        addressEditText = (EditText) findViewById(R.id.editTextAddMarker);
        title12 = addressEditText.getText().toString();

        if (title12.length() > 2) {

            MarkerOptions markerOpt1 = new MarkerOptions()
                    .title(title12)
                    .anchor(0.5f, 0.5f);
            markerOpt1.position(latLng);
            mMap.addMarker(markerOpt1);
            Toast.makeText(this, "Marker Added", Toast.LENGTH_LONG).show();

         //   addressEditText.setText("");

            locationCount++;

            /** Opening the editor object to write data to sharedPreferences */
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Storing the latitude for the i-th location
            editor.putString("lat" + Integer.toString((locationCount - 1)), Double.toString(latLng.latitude));

            // Storing the longitude for the i-th location
            editor.putString("lng" + Integer.toString((locationCount - 1)), Double.toString(latLng.longitude));

            //storing title
            editor.putString("title", addressEditText.getText().toString());


            // Storing the count of locations or marker count
            editor.putInt("locationCount", locationCount);


            /** Saving the values stored in the shared preferences */
            editor.commit();

        } else if (title12.length() < 1) {
            Toast.makeText(this, "Enter Reminder", Toast.LENGTH_LONG).show();
        }


    }
    private void drawMarker(LatLng point, String title){
        addressEditText = (EditText) findViewById(R.id.editTextAddMarker);
        title = addressEditText.getText().toString();
        // Creating an instance of MarkerOptions

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);
        //draw title for marker
        markerOptions.title(title);


        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);


    }

    public void clearMarker(View view) {
        // Opening the editor object to delete data from sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clearing the editor
        editor.clear();

        // Committing the changes
        editor.commit();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        clearMarker();
    }

    private void clearMarker() {
        // Opening the editor object to delete data from sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clearing the editor
        editor.clear();

        // Committing the changes
        editor.commit();
    }
}




