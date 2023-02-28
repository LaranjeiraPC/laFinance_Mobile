package com.llo.lafinance.ui.carteira.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.llo.lafinance.R;
import com.llo.lafinance.domain.service.HomeService;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.VendaRepository;
import com.llo.lafinance.ui.principal.PrincipalActivity;

public class ExcluiCarteiraFragment extends DialogFragment {

    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        assert bundle != null;
        String idCompra = bundle.getString("idCompra");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fragment_titulo_exclui)
                .setPositiveButton(R.string.dialog_fragment_exclui, (dialog, id) -> {
                    Compra compra = new CompraRepository(this.context).consultarCompraPorId(Integer.parseInt(idCompra));
                    new CompraRepository(this.context).deletar(compra.getId());
                    new HomeService(new CompraRepository(this.context), new VendaRepository(this.context), new CarteiraRepository(this.context)).atualizarCarteira();
                    Intent intent = new Intent(this.context, PrincipalActivity.class);
                    startActivity(intent);
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