package br.com.ale.ecombustivel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.ale.ecombustivel.R;
import br.com.ale.ecombustivel.teste.firebase.Posto;

public class CadLocaisAbastecimento extends Fragment {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference postoReferencia = databaseReference.child("abastecimentos");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //não esquecer de trocar o inflate para apontar para o fragmente certo
        View view = inflater.inflate(R.layout.fragment_cad_abastecimento, container, false);

        Posto posto = new Posto();
        posto.setNome("Rubi");
        posto.setLatitude("000102");
        posto.setLongitude("0010203");


        //teste firebase
        postoReferencia.child("001").setValue(posto);


        return view;
    }
}
