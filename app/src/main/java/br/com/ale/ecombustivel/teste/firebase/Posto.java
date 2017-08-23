package br.com.ale.ecombustivel.teste.firebase;

/**
 * Created by ale on 08/06/2017.
 */

public class Posto {


    public String uid;
    private String nome;
    private String latitude;
    private String longitude;
    private Double precoAlcool;
    private Double precoGasolina;

    public Posto() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Double getPrecoAlcool() {
        return precoAlcool;
    }

    public void setPrecoAlcool(Double precoAlcool) {
        this.precoAlcool = precoAlcool;
    }

    public Double getPrecoGasolina() {
        return precoGasolina;
    }

    public void setPrecoGasolina(Double precoGasolina) {
        this.precoGasolina = precoGasolina;
    }
}
