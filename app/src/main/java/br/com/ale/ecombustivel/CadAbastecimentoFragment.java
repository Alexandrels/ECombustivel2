package br.com.ale.ecombustivel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.ale.ecombustivel.helper.CadAbastecimentoHelper;
import io.realm.Realm;


/**
 * @author alexandre
 */
public class CadAbastecimentoFragment extends Fragment implements View.OnClickListener {
    private Realm realm;
    private Button btSalvar;
    private CadAbastecimentoHelper helper;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_cad_abastecimento, container, false);
        helper = new CadAbastecimentoHelper(view);
        btSalvar = (Button) view.findViewById(R.id.bt_salvar);
        btSalvar.setOnClickListener(this);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_salvar) {
            helper.salvar(getActivity(),realm);

        }

    }

}
