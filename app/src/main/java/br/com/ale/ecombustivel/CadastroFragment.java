package br.com.ale.ecombustivel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * @author alexandre
 */
public class CadastroFragment extends Fragment implements View.OnClickListener {
    private Button btSalvar;
    private EditText preco;
    private EditText valor;
    private Realm realm;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
        View cadastroView = inflater.inflate(R.layout.fragment_cadastro, container, false);

        preco = (EditText) cadastroView.findViewById(R.id.ed_preco);
        valor = (EditText) cadastroView.findViewById(R.id.ed_total_abastecido);
        btSalvar = (Button) cadastroView.findViewById(R.id.bt_salvar);
        btSalvar.setOnClickListener(this);


        // Inflate the layout for this fragment
        return cadastroView;
    }

    public void salvar() {
        try {
            realm.beginTransaction();
            TabelaModelo cadastro = new TabelaModelo();

            String id = UUID.randomUUID().toString();

            cadastro.setId(id);
            String precoStr = preco.getText().toString();
            cadastro.setPreco(new Double(precoStr));

            String valorStr = valor.getText().toString();
            cadastro.setValorPago(new Double(valorStr));

            cadastro.setLitros(cadastro.getValorPago() / cadastro.getPreco());

            realm.copyToRealm(cadastro);
            realm.commitTransaction();

            preco.setText("");
            valor.setText("");
            Toast.makeText(getActivity(), "Abastecimento salvo com sucesso!", Toast.LENGTH_SHORT);

        } catch (Exception e) {
            Log.d("SALVA_ABASTC", "ERRO AO CADASTRAR ABASTECIMENTO " + e.getStackTrace());
        }


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_salvar) {
            salvar();

        }

    }
}
