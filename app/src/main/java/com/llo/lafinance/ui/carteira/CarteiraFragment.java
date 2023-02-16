package com.llo.lafinance.ui.carteira;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.llo.lafinance.adapter.CarteiraAdapter;
import com.llo.lafinance.databinding.FragmentCarteiraBinding;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.CompraRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CarteiraFragment extends Fragment {

    private Context context;
    private FragmentCarteiraBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Compra> compras = new CompraRepository(context).consultarComprasAtivas();

        binding = FragmentCarteiraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView carteiraTotalTextView = binding.idCarteiraTotal;
        final ListView carteira = binding.listViewCarteira;

        this.definirPrecoTotalAtivos(carteiraTotalTextView, compras);
        this.definirListaCompras(carteira, compras);

        return root;
    }

    private void definirPrecoTotalAtivos(TextView carteiraTotalTextView, ArrayList<Compra> compras) {
        BigDecimal precoTotalAtivos = compras.stream().map(c -> c.getPrecoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        carteiraTotalTextView.setText("R$" + precoTotalAtivos.toString());
    }

    private void definirListaCompras(ListView carteira, ArrayList<Compra> compras) {
        CarteiraAdapter carteiraAdapter = new CarteiraAdapter(context, compras);
        carteira.setAdapter(carteiraAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}