package br.com.ale.ecombustivel;

import android.*;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.ale.ecombustivel.Utils.Localizador;

/**
 * Created by alexandre on 18/04/17.
 */

public class GmapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int MINHAS_PERMISSOES_REQUEST_FINE_LOCATION = 101;
    private AlertDialog.Builder dialog;
    private int aux = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //SupportMapFragment fragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        MapFragment fragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //teste click
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MINHAS_PERMISSOES_REQUEST_FINE_LOCATION);
            return;
        }
        //click longo para cadastrar ponto
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(getActivity(), "Clique longo" + latLng.latitude + ", " + latLng.longitude, Toast.LENGTH_SHORT).show();

                final LatLng pontoDoMarker = latLng;
                //criar o dialog
                dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Titulo");
                dialog.setMessage("Messagem do dialog");
                dialog.setIcon(android.R.drawable.ic_dialog_alert);


                //botao nao
                dialog.setNegativeButton("Não",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Pressionado Botão NÃO", Toast.LENGTH_SHORT).show();
                            }
                        });
                //botao sim
                dialog.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Pressionado Botão SIM", Toast.LENGTH_SHORT).show();
                                MarkerOptions marcador = new MarkerOptions().position(pontoDoMarker).title("Posto " + aux++).snippet("Gasolina R$3,1" + aux + " Alcool " +
                                        "R$2,6" + aux);
                                mMap.addMarker(marcador);
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });

        Localizador localizador = new Localizador(getActivity());
        LatLng local = localizador.getCoordenada("Av. São Gabriel, 2557");
        centralizaNo(local);

        LatLng coordenada = localizador.getCoordenada("Av. São Gabriel, 2557");
        if (coordenada != null) {
            MarkerOptions marcador = new MarkerOptions().position(coordenada).title("Posto Ipiranga").snippet("Gasolina R$3,19 Alcool " +
                    "R$2,69");
            mMap.addMarker(marcador);
        }
        // Add a marker in Sydney, Australia, and move the camera.
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    /*@Override
    public void onResume() {
        super.onResume();
        Localizador localizador = new Localizador(getActivity());
        LatLng local = localizador.getCoordenada("Rua Ines Canha Machioski, 301");
        centralizaNo(local);

        LatLng coordenada = localizador.getCoordenada("Rua Rui Puppi, 524");
        if (coordenada != null) {
            MarkerOptions marcador = new MarkerOptions().position(coordenada).title("Casa Ale").snippet("(41)99221257");
            mMap.addMarker(marcador);
        }
    }*/

    public void centralizaNo(LatLng local) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 17));
    }

}
