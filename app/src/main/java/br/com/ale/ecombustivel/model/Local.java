package br.com.ale.ecombustivel.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alexandre on 06/04/17.
 */

public class Local extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String descricao;
    private String latitude;
    private String longtude;
    private RealmList<Abastecimento> abastecimentos;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public RealmList<Abastecimento> getAbastecimentos() {
        return abastecimentos;
    }

    public void setAbastecimentos(RealmList<Abastecimento> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }
}
