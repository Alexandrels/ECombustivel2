package br.com.ale.ecombustivel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ale.ecombustivel.model.Abastecimento;

/**
 * Created by alexandre on 16/03/17.
 */

public class RecyclerTesteAdapter extends RecyclerView.Adapter<RecyclerTesteAdapter.RecyclerTesteViewHolder> {

    public static ClickRecyclerViewInterface clickRecyclerViewInterface;
    Context mctx;
    private List<Abastecimento> mList;

    public RecyclerTesteAdapter(Context ctx, List<Abastecimento> list, ClickRecyclerViewInterface clickRecyclerViewInterface) {
        this.mctx = ctx;
        this.mList = list;
        this.clickRecyclerViewInterface = clickRecyclerViewInterface;
    }

    @Override
    public RecyclerTesteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_tabela_modelo, viewGroup, false);
        return new RecyclerTesteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerTesteViewHolder viewHolder, int i) {
        Abastecimento tabelaModelo = mList.get(i);

        viewHolder.preco.setText(tabelaModelo.getPrecoCombustivel().toString());
        viewHolder.valor.setText(tabelaModelo.getValorPago().toString());
        viewHolder.litros.setText(tabelaModelo.getQuantidadeLitros().toString());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    protected class RecyclerTesteViewHolder extends RecyclerView.ViewHolder {

        protected TextView preco;
        protected TextView valor;
        protected TextView litros;

        public RecyclerTesteViewHolder(final View itemView) {
            super(itemView);

            valor = (TextView) itemView.findViewById(R.id.valor);
            preco = (TextView) itemView.findViewById(R.id.preco);
            litros = (TextView) itemView.findViewById(R.id.litros);

            //Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickRecyclerViewInterface.onCustomClick(mList.get(getLayoutPosition()));

                }
            });
        }
    }
}