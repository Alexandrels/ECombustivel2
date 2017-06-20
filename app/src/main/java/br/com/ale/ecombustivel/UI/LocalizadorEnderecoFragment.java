package br.com.ale.ecombustivel.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import br.com.ale.ecombustivel.R;
import br.com.ale.ecombustivel.Utils.DetectaConexao;
import br.com.ale.ecombustivel.domain.MessageEB;
import br.com.ale.ecombustivel.service.LocationIntentService;
import de.greenrobot.event.EventBus;

/**
 * Created by ale on 12/05/2017.
 */

public class LocalizadorEnderecoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LocationListener {
    public static final String LOCATION = "location";
    public static final String TYPE = "type";
    public static final String ADDRESS = "address";
    private static final String TAG = GeolocalizacaoFragment.class.getSimpleName();
    private static final int MINHAS_PERMISSOES_REQUEST_FINE_LOCATION = 101;
    private static final int MINHAS_PERMISSOES_REQUEST_COARSE_LOCATION = 102;
    private boolean permissionIsGranted = false;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private EditText etAddress;
    private TextView tvAddressLocation;
    private Button btNameToCoord;
    private Button btCoordToName;
    private Button btCoordOffline;
    private LocationRequest mLocationRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localizador_endereco, container, false);

        etAddress = (EditText) view.findViewById(R.id.et_address);
        tvAddressLocation = (TextView) view.findViewById(R.id.tv_address_location);
        btNameToCoord = (Button) view.findViewById(R.id.bt_name_to_coord);
        btNameToCoord.setOnClickListener(this);
        btCoordToName = (Button) view.findViewById(R.id.bt_coord_to_name);
        btCoordToName.setOnClickListener(this);
        btCoordOffline = (Button) view.findViewById(R.id.bt_coord_sem_net);
        btCoordOffline.setOnClickListener(this);

        callConnection();
        return view;
    }

    private synchronized void callConnection() {
        Log.i("LOG", "AddressLocationActivity.callConnection()");

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void callIntentService(int type, String address) {
        Intent it = new Intent(getActivity(), LocationIntentService.class);
        it.putExtra(TYPE, type);
        it.putExtra(ADDRESS, address);
        it.putExtra(LOCATION, mLastLocation);
        getActivity().startService(it);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MINHAS_PERMISSOES_REQUEST_FINE_LOCATION);
//            return;
//        }
//        Location l = LocationServices
//                .FusedLocationApi
//                .getLastLocation(mGoogleApiClient);
//
//        if (l != null) {
//            mLastLocation = l;
//            btNameToCoord.setEnabled(true);
//            btCoordToName.setEnabled(true);
//        }
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MINHAS_PERMISSOES_REQUEST_FINE_LOCATION);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            btCoordOffline.setEnabled(true);
            mLastLocation = location;
            if (new DetectaConexao(getActivity()).existeConexao()) {
                btNameToCoord.setEnabled(true);
                btCoordToName.setEnabled(true);
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "AddressLocationActivity.onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("LOG", "AddressLocationActivity.onConnectionFailed(" + connectionResult + ")");
    }

    public void onEvent(final MessageEB m) {
        handleNewLocation(m);
        //verificar se o cara tem internet se tiver habilito o botão senao uso o Fused
//        runOnUiThread(new Runnable(){
//            @Override
//            public void run() {
//                Log.i("LOG", m.getResultMessage());
//                tvAddressLocation.setText("Data: "+m.getResultMessage());
//            }
//        });
    }

    private void handleNewLocation(final MessageEB m) {
        Log.i("LOG", m.getResultMessage());
        tvAddressLocation.setText(Html.fromHtml("End: " + m.getResultMessage()));
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
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permitiu
                    permissionIsGranted = true;
                } else {
                    //nao tem permissao
                    permissionIsGranted = false;
                    Toast.makeText(getActivity().getApplicationContext(), "Esta app quer a porra da permissao GPS", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        getLocationListener(v);
    }

    public void getLocationListener(View view) {
        int type;
        String address = null;

        if (view.getId() == R.id.bt_coord_sem_net) {
            String retorno = "Sem Coordendas";
            if (mLastLocation != null) {
                retorno = Html.fromHtml("Localização: " + mLastLocation.getLatitude() + "<br/>" + mLastLocation.getLongitude()).toString();
            }
            MessageEB messageEB = new MessageEB();
            messageEB.setResultMessage(retorno);
            handleNewLocation(messageEB);
            return;
        }
        if (view.getId() == R.id.bt_name_to_coord) {
            type = 1;
            address = etAddress.getText().toString();
        } else {
            type = 2;
        }

        callIntentService(type, address);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("LOG", String.format("Latitude: %f Longitude: %f", location.getLatitude(), location.getLongitude()));
        mLastLocation = location;
    }
}
