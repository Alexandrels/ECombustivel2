package br.com.ale.ecombustivel.teste.firebase;

/**
 * Created by ale on 08/06/2017.
 */

public class Posto {

    private String nome;
    private String latitude;
    private String longitude;

    public Posto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
