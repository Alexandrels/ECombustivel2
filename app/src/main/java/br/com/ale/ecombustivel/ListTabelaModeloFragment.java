package br.com.ale.ecombustivel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.ale.ecombustivel.model.Abastecimento;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by alexandre on 24/02/17.
 */

public class ListTabelaModeloFragment extends Fragment implements ClickRecyclerViewInterface {
    RecyclerTesteAdapter adapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Abastecimento> pessoasListas = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private Realm realm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View agendaView = inflater.inflate(R.layout.fragment_list_tabela_modelo, container, false);
        //RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getContext())
        //      .deleteRealmIfMigrationNeeded()
        //    .build();
        // realm = Realm.getInstance(realmConfiguration);
        realm = Realm.getDefaultInstance();

        setaRecyclerView(agendaView);

        setaButtons(agendaView);
        listenersButtons();

        return agendaView;
    }

    /**
     * Aqui é o método onde trata o clique em um item da lista
     */
    @Override
    public void onCustomClick(Object object) {
        //aqui você poderá fazer algo com este objeto como por exemplo, enviar os dados para
        //outra activity e chamá-la.

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    public void setaRecyclerView(View activity) {

        //Aqui é instanciado o Recyclerview
        mRecyclerView = (RecyclerView) activity.findViewById(R.id.recycler_recyclerteste);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        // mLayoutManager = new LinearLayoutManager(activity.getContext());
        mLayoutManager = new GridLayoutManager(activity.getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pessoasListas = listar();

        adapter = new RecyclerTesteAdapter(activity.getContext(), pessoasListas, this);
        mRecyclerView.setAdapter(adapter);
    }

    public void setaButtons(View activity) {

        floatingActionButton = (FloatingActionButton) activity.findViewById(R.id.fab_fabteste);

    }

    /**
     * Chama os listeners para os botões
     */
    public void listenersButtons() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Cria uma nova pessoa chamada Renan Teles
                // Pessoa pessoa1 = new Pessoa();
                // pessoa1.setNome("Renan Teles");

                //Adiciona a pessoa1 e avisa o adapter que o conteúdo
                //da lista foi alterado
                //   pessoasListas.add(pessoa1);
                adapter.notifyDataSetChanged();

            }
        });
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
    public void onResume() {
        super.onResume();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
