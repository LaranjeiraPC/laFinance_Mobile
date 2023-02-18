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
import com.llo.lafinance.model.Ativo;

import java.util.ArrayList;
import java.util.List;

public class AtivoAdapter extends ArrayAdapter<Ativo> {

    private Context mContext;
    private List<Ativo> ativoList;

    public AtivoAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Ativo> list) {
        super(context, 0, list);
        mContext = context;
        ativoList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_ativo,parent,false);

        Ativo ativo = ativoList.get(position);

        TextView nome = listItem.findViewById(R.id.textViewNomeAtivo);
        nome.setText(ativo.getNome());

        TextView descricao = listItem.findViewById(R.id.textViewDescricaoAtivo);
        descricao.setText(ativo.getDescricao());

        return listItem;
    }
}
