package br.com.ale.ecombustivel.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
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

import br.com.ale.ecombustivel.R;
import br.com.ale.ecombustivel.RuntimePermission;

/**
 * @author alexandre
 */
public class GeolocalizacaoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = GeolocalizacaoFragment.class.getSimpleName();
    private static final int MINHAS_PERMISSOES_REQUEST_FINE_LOCATION = 101;
    private static final int MINHAS_PERMISSOES_REQUEST_COARSE_LOCATION = 102;
    private boolean permissionIsGranted = false;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private RuntimePermission runtimePermission;
    private TextView tvCoordenadas;
//    private GoogleMap mGoogleMap;
//    private MapView mMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        // mGoogleApiClient.connect();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);

        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // requestLocationUpdates();
        View rootView = inflater.inflate(R.layout.fragment_geolocalizacao, container, false);
        tvCoordenadas = (TextView) rootView.findViewById(R.id.tv_last_location);

//        mMapView = (MapView) rootView.findViewById(R.id.mapView);
//        mMapView.onCreate(savedInstanceState);
//        mMapView.onResume();

        //new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, R.string.msg, REQUEST_LOCATION

        Log.d(TAG, "onCreateView");
        return rootView;
    }

    // @Override
    // public void onStart() {
    //     super.onStart();
    //     mGoogleApiClient.connect();
    // }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        //aqui vai um metodo que verifica se tem a permissao e o chama
//        mMapView.onResume();
//        setUpMap();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        // if (permissionIsGranted) {
        //     LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        // }
        Log.d(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mMapView.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mMapView.onLowMemory();

        Log.d(TAG, "onLowMemory");
    }

    @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();
        Log.d(TAG, "onConnected");
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MINHAS_PERMISSOES_REQUEST_FINE_LOCATION);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null)
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        else
            handleNewLocation(location);
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

    private void setUpMap() {
//        if (mGoogleMap == null)
//            mMapView.getMapAsync(new OnMapReadyCallback() {
//
//                @Override
//                public void onMapReady(GoogleMap googleMap) {
//                    mGoogleMap = googleMap;
//                }
//            });
    }

    private void handleNewLocation(Location location) {
        // LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        tvCoordenadas.setText(Html.fromHtml("Localização: " + location.getLatitude() + "<br/>" + location.getLongitude()));
//
//        MarkerOptions options = new MarkerOptions()
//                .position(latLng)
//                .title("I am here!");
//        mGoogleMap.addMarker(options);
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private boolean hasPermission(String permission) {

        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
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
