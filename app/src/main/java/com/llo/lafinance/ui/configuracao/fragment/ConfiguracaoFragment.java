package com.llo.lafinance.ui.configuracao.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.binding = FragmentConfiguracaoBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        this.configuracaoRepository = new ConfiguracaoRepository(this.context);

        Configuracao configuracao = this.configuracaoRepository.consultarConfiguracao();

        this.idLucroLiquidoMensalValorConfiguracao = this.binding.idLucroLiquidoMensalValorConfiguracao;
        this.idUsuarioValorConfiguracao = this.binding.idUsuarioValorConfiguracao;

        this.idUsuarioValorConfiguracao.setText(configuracao.getNomeUsuario());
        this.idLucroLiquidoMensalValorConfiguracao.setText(configuracao.getMetaLucroLiquidoMensal().toString());

        ImageView buttonSalvar = root.findViewById(R.id.idImageSalvarConfiguracao);
        buttonSalvar.setOnClickListener(view -> this.salvar());

        return root;
    }

    private void salvar() {
        Configuracao configuracao = this.configuracaoRepository.consultarConfiguracao();
        configuracao.setNomeUsuario(this.idUsuarioValorConfiguracao.getText().toString());
        configuracao.setMetaLucroLiquidoMensal(new BigDecimal(this.idLucroLiquidoMensalValorConfiguracao.getText().toString()));
        configuracao.setDataAtualizacao(LocalDate.now());
        this.configuracaoRepository.atualizar(configuracao);
        Toast.makeText(this.context, "Dados atualizado com sucesso!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }
}