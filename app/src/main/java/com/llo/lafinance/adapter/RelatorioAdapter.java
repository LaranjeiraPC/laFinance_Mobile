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
import com.llo.lafinance.model.Relatorio;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class RelatorioAdapter extends ArrayAdapter<Relatorio> {

    private Context mContext;
    private List<Relatorio> relatorioList;

    public RelatorioAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Relatorio> list) {
        super(context, 0, list);
        mContext = context;
        relatorioList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_relatorio, parent, false);

        Relatorio relatorioAtiva = relatorioList.get(position);

        TextView ativo = listItem.findViewById(R.id.idAtivoValorRelatorio);
        ativo.setText(relatorioAtiva.getAtivo());

        TextView quantidadeCompra = listItem.findViewById(R.id.idQuantidadeValorCompraRelatorio);
        quantidadeCompra.setText(relatorioAtiva.getQuantidadeCompra().toString());

        TextView unitarioCompra = listItem.findViewById(R.id.idUnitarioValorCompraRelatorio);
        unitarioCompra.setText(numberFormat.format(relatorioAtiva.getPrecoUnitarioCompra()));

        TextView totalCompra = listItem.findViewById(R.id.idTotalValorCompraRelatorio);
        totalCompra.setText(numberFormat.format(relatorioAtiva.getPrecoTotalCompra()));

        TextView quantidadeVenda = listItem.findViewById(R.id.idQuantidadeValorVendaRelatorio);
        quantidadeVenda.setText(relatorioAtiva.getQuantidadeVenda().toString());

        TextView unitarioVenda = listItem.findViewById(R.id.idUnitarioValorVendaRelatorio);
        unitarioVenda.setText(numberFormat.format(relatorioAtiva.getPrecoUnitarioVenda()));

        TextView totalVenda = listItem.findViewById(R.id.idTotalValorVendaRelatorio);
        totalVenda.setText(numberFormat.format(relatorioAtiva.getPrecoTotalVenda()));

        TextView lucroTotal = listItem.findViewById(R.id.idTotalValorDetalheRelatorio);
        lucroTotal.setText(numberFormat.format(relatorioAtiva.getLucroTotal()));

        TextView dataCompra = listItem.findViewById(R.id.idDataCompraValorDetalheRelatorio);
        dataCompra.setText(relatorioAtiva.getDataCriacaoCompra().toString());

        TextView dataVenda = listItem.findViewById(R.id.idDataVendaValorDetalheRelatorio);
        dataVenda.setText(relatorioAtiva.getDataCriacaoVenda().toString());

        return listItem;
    }
}
