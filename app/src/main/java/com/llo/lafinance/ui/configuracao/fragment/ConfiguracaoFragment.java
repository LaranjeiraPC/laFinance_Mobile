package com.llo.lafinance.ui.configuracao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.llo.lafinance.R;
import com.llo.lafinance.databinding.FragmentConfiguracaoBinding;
import com.llo.lafinance.model.Configuracao;
import com.llo.lafinance.repositorio.ConfiguracaoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConfiguracaoFragment extends Fragment {

    private Context context;
    private FragmentConfiguracaoBinding binding;

    private TextView idLucroLiquidoMensalValorConfiguracao;
    private TextView idUsuarioValorConfiguracao;
    private ConfiguracaoRepository configuracaoRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfiguracaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.configuracaoRepository = new ConfiguracaoRepository(context);

        Configuracao configuracao = this.configuracaoRepository.consultarConfiguracao();

        idLucroLiquidoMensalValorConfiguracao = binding.idLucroLiquidoMensalValorConfiguracao;
        idUsuarioValorConfiguracao = binding.idUsuarioValorConfiguracao;

        idUsuarioValorConfiguracao.setText(configuracao.getNomeUsuario());
        idLucroLiquidoMensalValorConfiguracao.setText(configuracao.getMetaLucroLiquidoMensal().toString());

        Button button = root.findViewById(R.id.idSalvarConfiguracao);
        button.setOnClickListener(view -> {
            this.salvar();
            Snackbar.make(view, "Dados atualizado com sucesso", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        return root;
    }

    private void salvar() {
        Configuracao configuracao = this.configuracaoRepository.consultarConfiguracao();
        configuracao.setNomeUsuario(idUsuarioValorConfiguracao.getText().toString());
        configuracao.setMetaLucroLiquidoMensal(new BigDecimal(idLucroLiquidoMensalValorConfiguracao.getText().toString()));
        configuracao.setDataAtualizacao(LocalDate.now());
        this.configuracaoRepository.atualizar(configuracao);
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