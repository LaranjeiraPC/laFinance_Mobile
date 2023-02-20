package com.llo.lafinance.ui.carteira.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.llo.lafinance.ui.carteira.EditaCompraActivity;

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
        this.carregarEditaActivity(compras, carteira);

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

    private void carregarEditaActivity(ArrayList<Compra> Compras, ListView compraListView) {
        compraListView.setOnItemClickListener((parent, view, position, id) -> {
            Compra compraTemp = Compras.get(position);

            Intent intent = new Intent(context, EditaCompraActivity.class);

            Bundle txtBundle = new Bundle();
            txtBundle.putString("id", compraTemp.getId().toString());
            txtBundle.putString("nomeAtivo", compraTemp.getAtivo());
            txtBundle.putString("quantidade", compraTemp.getQuantidade().toString());
            txtBundle.putString("valorUnitario", compraTemp.getPrecoUnitario().toString());
            txtBundle.putString("totalCompra", compraTemp.getPrecoTotal().toString());

            intent.putExtras(txtBundle);
            startActivity(intent);
        });
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