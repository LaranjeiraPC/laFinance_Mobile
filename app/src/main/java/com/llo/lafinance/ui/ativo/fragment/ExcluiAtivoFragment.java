package com.llo.lafinance.ui.ativo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.llo.lafinance.R;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.ui.principal.PrincipalActivity;

import java.time.LocalDate;

public class ExcluiAtivoFragment extends DialogFragment {

    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String idAtivo = bundle.getString("idAtivo");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fragment_titulo_exclui)
                .setPositiveButton(R.string.dialog_fragment_exclui, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Ativo ativo = new AtivoRepository(context).consultarAtivoPorId(Integer.parseInt(idAtivo));
                        boolean existeCompras = new CompraRepository(context).consultarComprasPorNomeAtivo(ativo.getNome());
                        if (existeCompras) {
                            Toast.makeText(context, "Existente compras de ações!", Toast.LENGTH_LONG);
                        } else {
                            new AtivoRepository(context).deletar(Integer.parseInt(idAtivo));
                            Intent intent = new Intent(context, PrincipalActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_fragment_cancela, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private Ativo definirAtivo(Ativo ativo, EditText nomeAtivo, EditText descricaoAtivo) {
        ativo.setNome(nomeAtivo.getText().toString());
        ativo.setDescricao(descricaoAtivo.getText().toString());
        ativo.setDataAtualizacao(LocalDate.now());
        return ativo;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}