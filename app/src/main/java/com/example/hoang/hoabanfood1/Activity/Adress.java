package com.example.hoang.hoabanfood1.Activity;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Model.DirectionFinder;
import com.example.hoang.hoabanfood1.Model.DirectionFinderListener;
import com.example.hoang.hoabanfood1.Model.GPSTracker;
import com.example.hoang.hoabanfood1.Model.Route;
import com.example.hoang.hoabanfood1.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Adress extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks
        , CompoundButton.OnCheckedChangeListener
        , GoogleMap.OnMarkerClickListener {
    GoogleMap mMap;
    MapView mapview;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Button btndirectionfind;
    private boolean mLocationPermissionGranted;
    private int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    private Location mLastKnownLocation;
    private GoogleApiClient mGoogleApiClient;
    private CameraPosition mCameraPosition;
    private float DEFAULT_ZOOM = 15.0f;
    private LatLng mDefaultLocation = new LatLng(20.979904, 105.851686);
    private String TAG = "Demo";
    private static final String KEY_MAP_SAVED_STATE = "mapState";
    String kinhdo;
    String vido;
    String kinhdo1;
    String vido1;
    LatLng mylocation;
    LatLng Hoabanfood;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        mapview = (MapView) findViewById(R.id.mapview);
        Bundle mapState = (savedInstanceState != null)
                ? savedInstanceState.getBundle(KEY_MAP_SAVED_STATE) : null;
        mapview.onCreate(mapState);
        mapview.onResume();

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapview.getMapAsync(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btndirectionfind = (Button) findViewById(R.id.btn_directionfind);
        btndirectionfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
        //khởi tạo GoogleClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
        gpsTracker = new GPSTracker(getApplicationContext());
        kinhdo = String.valueOf(gpsTracker.getLatitude());
        vido = String.valueOf(gpsTracker.getLongitude());
    }

    //chi duong
    private void sendRequest() {
//        kinhdo1 = String.valueOf(mylocation.latitude);
//        vido1 = String.valueOf(mylocation.longitude);
        String origin = kinhdo + ", " + vido;
        String kinhdoch = String.valueOf(Hoabanfood.latitude);
        String vidoch = String.valueOf(Hoabanfood.longitude);
//        String origin = "Đại học công nghiệp hà nội";
        String destination = kinhdoch.toString() + "," + vidoch.toString();
        try {
            new DirectionFinder((DirectionFinderListener) this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapState = new Bundle();
        mapview.onSaveInstanceState(mapState);
        outState.putBundle(KEY_MAP_SAVED_STATE, mapState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        Hoabanfood = new LatLng(20.979904, 105.851686);
        mMap.addMarker(new MarkerOptions().position(Hoabanfood).title("Cửa hàng Hoabanfood").
                snippet("3 ngõ 258 phố Tân Mai, Tân Mai, Hoàng Mai, Hà Nội, Việt Nam"));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(Hoabanfood).zoom(16.0f).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //getDeviceLocation();
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }
//  hàm lấy vị trí hiện tại
//    private void getDeviceLocation() {
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//
//        // Set the map's camera position to the current location of the device.
//        if (mCameraPosition != null) {
//            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
//        } else if (mLastKnownLocation != null) {
//            //Đánh dấu vị trí hiện tại và di chuyển camera
//            mMap.addMarker(
//                    new MarkerOptions().position(mylocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())).title("You are here")
//            );
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, DEFAULT_ZOOM));
//
//        } else {
//            Log.d(TAG, "Current location is null. Using defaults.");
////            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//        }
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconloacation1))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconloacation1))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
