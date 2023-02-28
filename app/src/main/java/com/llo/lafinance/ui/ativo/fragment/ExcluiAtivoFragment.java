package com.llo.lafinance.ui.ativo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.llo.lafinance.R;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.ui.principal.PrincipalActivity;

public class ExcluiAtivoFragment extends DialogFragment {

    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        assert bundle != null;
        String idAtivo = bundle.getString("idAtivo");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fragment_titulo_exclui)
                .setPositiveButton(R.string.dialog_fragment_exclui, (dialog, id) -> {
                    Ativo ativo = new AtivoRepository(context).consultarAtivoPorId(Integer.parseInt(idAtivo));
                    boolean existeCompras = new CompraRepository(context).consultarComprasPorNomeAtivo(ativo.getNome());
                    if (existeCompras) {
                        Toast.makeText(context, "Existente compras de ações!", Toast.LENGTH_LONG).show();
                    } else {
                        new AtivoRepository(context).deletar(Integer.parseInt(idAtivo));
                        Intent intent = new Intent(context, PrincipalActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.dialog_fragment_cancela, (dialog, id) -> dismiss());
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismiss();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }
}