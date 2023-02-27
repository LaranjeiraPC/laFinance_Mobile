package com.llo.lafinance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.llo.lafinance.R;
import com.llo.lafinance.model.Compra;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CarteiraAdapter extends ArrayAdapter<Compra> {

    private Context mContext;
    private List<Compra> CompraList;

    public CarteiraAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Compra> list) {
        super(context, 0, list);
        mContext = context;
        CompraList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_compra, parent, false);

        Compra compraAtiva = CompraList.get(position);

        TextView ativo = listItem.findViewById(R.id.textView_ativo);
        ativo.setText(compraAtiva.getAtivo());

        TextView quantidade = listItem.findViewById(R.id.textView_quantidade);
        quantidade.setText(compraAtiva.getQuantidade().toString());

        TextView precoAtivo = listItem.findViewById(R.id.textView_preco_ativo);
        precoAtivo.setText(numberFormat.format(compraAtiva.getPrecoUnitario()));

        TextView total = listItem.findViewById(R.id.textView_total);
        total.setText(numberFormat.format(compraAtiva.getPrecoTotal()));

        return listItem;
    }
}
