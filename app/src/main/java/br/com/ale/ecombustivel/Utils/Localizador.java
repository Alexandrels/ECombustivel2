package br.com.ale.ecombustivel.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by alexandre on 20/04/17.
 */


/**
 * Created by alexandre on 12/02/16.
 */
public class Localizador {

    private final Geocoder geocoder;

    public Localizador(Context context) {
        geocoder = new Geocoder(context);
    }

    public LatLng getCoordenada(String endereco) {
        try {
            List<Address> listaDeEnderecos = geocoder.getFromLocationName(endereco, 1);
            if (!listaDeEnderecos.isEmpty()) {
                Address address = listaDeEnderecos.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
