package br.com.ale.ecombustivel.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alexandre on 06/04/17.
 */

public class Usuario extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String nome;
    private RealmList<Abastecimento> abastecimentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RealmList<Abastecimento> getAbastecimentos() {
        return abastecimentos;
    }

    public void setAbastecimentos(RealmList<Abastecimento> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }
}
