package br.com.ale.ecombustivel.UI;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.ale.ecombustivel.CadAbastecimentoFragment;
import br.com.ale.ecombustivel.ClickRecyclerViewInterface;
import br.com.ale.ecombustivel.R;
import br.com.ale.ecombustivel.adapter.RecyclerAdapterAbastecimentos;
import br.com.ale.ecombustivel.model.Abastecimento;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author alexandre
 */
public class ListaAbastecimentosFragment extends Fragment implements ClickRecyclerViewInterface {
    RecyclerAdapterAbastecimentos adapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Abastecimento> abastecimentos = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View abastecimentoView = inflater.inflate(R.layout.fragment_lista_abastecimentos, container, false);
        realm = Realm.getDefaultInstance();
        //e avisa o adapter que o conteúdo da lista foi alterado
        //  adapter.notifyDataSetChanged();
        setaRecyclerView(abastecimentoView);
        setaButtons(abastecimentoView);
        listenersButtons();

        return abastecimentoView;
    }

    public void setaButtons(View activity) {

        floatingActionButton = (FloatingActionButton) activity.findViewById(R.id.fab_fabteste);

    }

    public void setaRecyclerView(View activity) {

        //Aqui é instanciado o Recyclerview
        mRecyclerView = (RecyclerView) activity.findViewById(R.id.recycler_abastecimentos);
        mLayoutManager = new LinearLayoutManager(activity.getContext());
        // mLayoutManager = new GridLayoutManager(activity.getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        abastecimentos = listar();

        adapter = new RecyclerAdapterAbastecimentos(activity.getContext(), abastecimentos, this);
        mRecyclerView.setAdapter(adapter);
    }

    public List<Abastecimento> listar() {
        try {
            RealmResults<Abastecimento> tasks =
                    realm.where(Abastecimento.class).findAll().sort("id");
            for (Abastecimento tabelaModelo : tasks) {
                Log.d("Agendas", "Preço:: " + tabelaModelo.getPrecoCombustivel());
            }
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCustomClick(Object object) {

    }

    /**
     * Chama os listeners para os botões
     */
    public void listenersButtons() {
        final FragmentManager fragmentManager = getFragmentManager();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.container, new CadAbastecimentoFragment()).commit();

            }
        });
    }
}
