package br.com.ale.ecombustivel.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;

import br.com.ale.ecombustivel.R;

/**
 * @author alexandre
 */
public class GeolocalizacaoFragment2 extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = GeolocalizacaoFragment2.class.getSimpleName();
    private static final int MINHAS_PERMISSOES_REQUEST_FINE_LOCATION = 101;
    private static final int MINHAS_PERMISSOES_REQUEST_COARSE_LOCATION = 102;
    private boolean permissionIsGranted = false;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private TextView tvCoordenadas;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        initLocationRequest();
        Log.d(TAG, "onCreate");
    }

    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdate() {
        initLocationRequest();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MINHAS_PERMISSOES_REQUEST_FINE_LOCATION);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null)
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        else
            handleNewLocation(location);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_geolocalizacao2, container, false);
        initLocationRequest();
        tvCoordenadas = (TextView) rootView.findViewById(R.id.tv_last_location);

        Log.d(TAG, "onCreateView");
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationUpdate();
        }
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            stopLocationUpdate();
        }
        Log.d(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.d(TAG, "onLowMemory");
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdate();
        Log.d(TAG, "onConnected");
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);

        Log.d(TAG, "onLocationChanged");
    }

    private void handleNewLocation(Location location) {
        tvCoordenadas.setText(Html.fromHtml("Latitude: " + location.getLatitude() + "<br />" +
                "Longetude: " + location.getLongitude() + "<br />" +
                "Bearing: " + location.getBearing() + "<br />" +
                "Altitude: " + location.getAltitude() + "<br />" +
                "Speed: " + location.getSpeed() + "<br />" +
                "Provider: " + location.getProvider() + "<br />" +
                "Accuracy: " + location.getAccuracy() + "<br />" +
                "Data: " + java.text.DateFormat.getTimeInstance().format(new Date()) + "<br />"));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MINHAS_PERMISSOES_REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permitiu
                    permissionIsGranted = true;
                } else {
                    //nao tem permissao
                    permissionIsGranted = false;
                    Toast.makeText(getActivity().getApplicationContext(), "Esta app quer a porra da permissao master", Toast.LENGTH_SHORT).show();

                }
                break;
            case MINHAS_PERMISSOES_REQUEST_COARSE_LOCATION:
                break;
        }
    }
}
