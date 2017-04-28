package br.com.ale.ecombustivel.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alexandre on 06/04/17.
 */

public class Abastecimento extends RealmObject {

    @PrimaryKey
    private Long id;
    private Double precoCombustivel;
    private Double valorPago;
    private Double quantidadeLitros;
    private Long data;


    public Double getQuantidadeLitros() {
        return quantidadeLitros;
    }

    public void setQuantidadeLitros(Double quantidadeLitros) {
        this.quantidadeLitros = quantidadeLitros;
    }

    public Double getPrecoCombustivel() {
        return precoCombustivel;
    }

    public void setPrecoCombustivel(Double precoCombustivel) {
        this.precoCombustivel = precoCombustivel;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }


    public static Long autoIncrementId() {
        Long key = 1L;
        Realm realm = Realm.getDefaultInstance();
        try {
            key = realm.where(Abastecimento.class).max("id").longValue() + 1;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
