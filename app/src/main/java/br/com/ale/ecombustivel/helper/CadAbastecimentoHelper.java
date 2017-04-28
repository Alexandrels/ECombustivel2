package br.com.ale.ecombustivel.helper;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.ale.ecombustivel.R;
import br.com.ale.ecombustivel.Utils.FormatarData;
import br.com.ale.ecombustivel.Utils.createDatePicker;
import br.com.ale.ecombustivel.model.Abastecimento;
import io.realm.Realm;

/**
 * Created by alexandre on 14/04/17.
 */

public class CadAbastecimentoHelper {
    private TextInputLayout lbPreco;
    private TextInputLayout lbValor;
    private TextInputLayout lbData;
    private EditText edPreco;
    private EditText edValor;
    private EditText edDataBastecimento;
    private Abastecimento abastecimento;
    private Activity activity;
    private String dataHoje;


    public CadAbastecimentoHelper(View view) {
        try {
            lbPreco = (TextInputLayout) view.findViewById(R.id.lb_preco);
            lbValor = (TextInputLayout) view.findViewById(R.id.lb_total_abastecido);
            lbData = (TextInputLayout) view.findViewById(R.id.lb_data_abastecimento);
            edPreco = (EditText) view.findViewById(R.id.ed_preco);
            edValor = (EditText) view.findViewById(R.id.ed_total_abastecido);
            edDataBastecimento = (EditText) view.findViewById(R.id.ed_data_abastecimento);
            LocalDate localDate = LocalDate.now();//For reference
            dataHoje = DateTimeFormat.forPattern("dd/MM/yyyy").print(localDate);
            edDataBastecimento.setText(dataHoje.toString());

            createDatePicker dtIni = new createDatePicker(edDataBastecimento, view.getContext(), "Data do Abastecimento", new LocalDate(1900,
                    1, 1).toDate().getTime());
        } catch (Exception e) {
            Log.e("ERRO_CAD_HELPER", "Erro iniciar o HELPER CAD ABASTECIMENTO ");
            e.printStackTrace();
        }
    }

    public void salvar(Activity activity, Realm realm) {
        try {
            this.activity = activity;
            if (!validarCampos()) {
                return;
            }
            realm.beginTransaction();
            abastecimento = realm.createObject(Abastecimento.class);
            abastecimento.setId(Abastecimento.autoIncrementId());

            pegaFormularioAbastecimento();
            limpaCamposEsconeTeclado();

            realm.commitTransaction();
            // valor.setFocusable(false);//aqui perdeu o foco
            Toast.makeText(activity, "Abastecimento salvo com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("SALVA_ABASTC", "ERRO AO CADASTRAR ABASTECIMENTO " + e.getStackTrace());
            Toast.makeText(activity, "ERRO AO CADASTRAR ABASTECIMENTO", Toast.LENGTH_SHORT).show();
        }

    }

    public void pegaFormularioAbastecimento() throws ParseException {
        String precoStr = edPreco.getText().toString();
        abastecimento.setPrecoCombustivel(new Double(precoStr));

        String valorStr = edValor.getText().toString();
        abastecimento.setValorPago(new Double(valorStr));

        Date dtabastcimento = FormatarData.getFormato().parse(edDataBastecimento.getText().toString());
        abastecimento.setData(dtabastcimento.getTime());

        abastecimento.setQuantidadeLitros(abastecimento.getValorPago() / abastecimento.getPrecoCombustivel());


    }

    public void limpaCamposEsconeTeclado() {
        edPreco.setText("");
        edValor.setText("");
        //esconde o teclado
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(edPreco.getWindowToken(), 0);
    }
    /*
    *
    * @param data no formato (dd/MM/yyyy)
    * return dia da semana inteiro. 0 Sunday, 1 monday etc..
    * */

    private int diaDaSemana(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        int diaDaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
        return diaDaSemana;
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (edPreco == null || edPreco.getText().toString().isEmpty()) {
            lbPreco.setError("Informe um preço");
            requestFocus(edPreco);
            retorno = false;
        } else {
            lbPreco.setErrorEnabled(false);
        }
        if (edValor == null || edValor.getText().toString().isEmpty()) {
            lbValor.setError("Informe o valor abastecido");
            //se retorono ainda estiver true é pq preço fio informado, posso mandar o foco pra ele
            if (retorno) {
                requestFocus(edValor);
            }
            retorno = false;
        } else {
            lbValor.setErrorEnabled(false);
        }
        if (edDataBastecimento == null || edDataBastecimento.getText().toString().isEmpty()) {
            edDataBastecimento.setText(dataHoje.toString());
        }
        return retorno;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        }

    }

}
