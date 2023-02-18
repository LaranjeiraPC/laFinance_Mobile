package com.llo.lafinance.ui.ativo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.llo.lafinance.R;
import com.llo.lafinance.adapter.AtivoAdapter;
import com.llo.lafinance.databinding.FragmentAtivoBinding;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.repositorio.AtivoRepository;

import java.util.ArrayList;

public class AtivoFragment extends Fragment {
    private Context context;
    private FragmentAtivoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAtivoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Ativo> ativos = new AtivoRepository(context).consultarAtivos();
        final ListView ativoListView = binding.listViewAtivo;

        final TextView ativoTotalTextView = binding.idAtivoTotal;

        this.definirQuantidadeTotalAtivos(ativoTotalTextView, ativos);
        this.definirListaAtivos(ativoListView, ativos);

        Button button = root.findViewById(R.id.buttonCadastraAtivo);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(context, CadastraAtivoActivity.class);
            startActivity(intent);
        });

        return root;
    }

    private void definirQuantidadeTotalAtivos(TextView ativoTotalTextView, ArrayList<Ativo> ativos) {
        ativoTotalTextView.setText(String.valueOf(ativos.size()));
    }

    private void definirListaAtivos(ListView ativo, ArrayList<Ativo> ativos) {
        AtivoAdapter ativoAdapter = new AtivoAdapter(context, ativos);
        ativo.setAdapter(ativoAdapter);
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