package br.com.ale.ecombustivel.Utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.com.ale.ecombustivel.teste.firebase.Posto;

/**
 * Created by ale on 23/08/2017.
 */

public class CalculaPontosProximos {

    private static final Double DISTANCIA_MINIMA = 0.500;
    private final int EARTH_RADIUS_KM = 6371;
    private Posto pontoReferencia;
    private List<Posto> pontosComparados;
    private DatabaseReference databaseReference;

    Double latitudeUm = -25.424226;
    Double longitudeUm = -49.204718;
    Double latitudeDois = -25.432483;
    Double longitudeDois = -49.192873;

    public CalculaPontosProximos(Posto pontoReferencia, DatabaseReference databaseReference, List<Posto> pontosComparados) {
        this.pontoReferencia = pontoReferencia;
        this.databaseReference = databaseReference;
        this.pontosComparados = pontosComparados;
    }

    public void removePontosProximosAoPontoReferencia() {
        for (Posto b : pontosComparados) {
            Double distancia = distanciaEntreOPontoReferenciaEOPontoComparado(pontoReferencia, b);
            if (distancia <= DISTANCIA_MINIMA) {
                databaseReference.child(b.getUid()).removeValue();
            }
        }
    }

    public Double distanciaEntreOPontoReferenciaEOPontoComparado(Posto a, Posto b) {
        // Conversão de graus pra radianos das latitudes
        double firstLatToRad = Math.toRadians(Double.parseDouble(a.getLatitude()));
        double secondLatToRad = Math.toRadians(Double.parseDouble(b.getLatitude()));

        // Diferença das longitudes
        double deltaLongitudeInRad = Math.toRadians((Double.parseDouble(b.getLongitude())) - (Double.parseDouble(a.getLongitude())));

        // Cálcula da distância entre os pontos
        Double valor = Math.acos(Math.sin(firstLatToRad) * Math.sin(secondLatToRad)
                + Math.cos(firstLatToRad) * Math.cos(secondLatToRad) * Math.cos(deltaLongitudeInRad))
                * EARTH_RADIUS_KM;
        return valor;
    }

}
