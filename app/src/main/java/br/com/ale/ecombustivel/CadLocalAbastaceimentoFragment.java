package br.com.ale.ecombustivel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import br.com.ale.ecombustivel.helper.CadAbastecimentoHelper;
import br.com.ale.ecombustivel.teste.firebase.Posto;
import io.realm.Realm;


/**
 * @author alexandre
 */
public class CadLocalAbastaceimentoFragment extends Fragment implements View.OnClickListener {
    private Button btSalvar;
    private EditText edPrecoAlcool;
    private EditText edPrecoGasolina;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference postoReferencia = databaseReference.child("locaisAbastecimentos");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_local_abastecimento, container, false);

        edPrecoAlcool = (EditText) view.findViewById(R.id.ed_preco_alcool);
        edPrecoGasolina = (EditText) view.findViewById(R.id.ed_preco_gasolina);
        btSalvar = (Button) view.findViewById(R.id.bt_salvar);
        btSalvar.setOnClickListener(this);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_salvar) {
            Posto posto = new Posto();
            String chaveId = "latitudeLongitude";
            posto.setNome("Posto Teste");
            posto.setPrecoAlcool(Double.parseDouble(edPrecoAlcool.getText().toString()));
            posto.setPrecoGasolina(Double.parseDouble(edPrecoGasolina.getText().toString()));
            posto.setLatitude("0001");
            posto.setLongitude("0002");
            chaveId += posto.getLatitude() + posto.getLongitude();

            postoReferencia.child(chaveId).setValue(posto);
        }

    }

}
