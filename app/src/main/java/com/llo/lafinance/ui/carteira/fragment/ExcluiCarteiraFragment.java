package com.llo.lafinance.ui.carteira.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.llo.lafinance.R;
import com.llo.lafinance.domain.HomeService;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.VendaRepository;
import com.llo.lafinance.ui.principal.PrincipalActivity;

public class ExcluiCarteiraFragment extends DialogFragment {

    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fragment_titulo_exclui)
                .setPositiveButton(R.string.dialog_fragment_exclui, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Compra compra = new CompraRepository(context).consultarCompraPorId(id);
                        new CompraRepository(context).deletar(compra.getId());
                        new HomeService(new CompraRepository(context), new VendaRepository(context), new CarteiraRepository(context)).atualizarCarteira();
                        Intent intent = new Intent(context, PrincipalActivity.class);
                        startActivity(intent);
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