package com.llo.lafinance.ui.ativo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.llo.lafinance.R;
import com.llo.lafinance.activity.CadastraCompraActivity;
import com.llo.lafinance.activity.PrincipalActivity;
import com.llo.lafinance.databinding.FragmentAtivoBinding;
import com.llo.lafinance.databinding.FragmentHomeBinding;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.ui.home.HomeViewModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class AtivoFragment extends Fragment {

    private EditText nomeAtivo;
    private EditText descricaoAtivo;
    private Context context;
    private FragmentAtivoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAtivoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nomeAtivo = root.findViewById(R.id.textView_cadastra_sigla_ativo);
        descricaoAtivo = root.findViewById(R.id.textView_cadastra_nome_ativo);

        Button button = root.findViewById(com.llo.lafinance.R.id.buttonsalvarativo);
        button.setOnClickListener(this::salvar);

        return root;
    }

    private void salvar(View view) {
        if (Objects.nonNull(nomeAtivo) && nomeAtivo.getText().length() > 0) {

            Ativo ativo = new AtivoRepository(context).consultarAtivo(nomeAtivo.getText().toString());

            if( Objects.nonNull(ativo)){
                Snackbar.make(view, "Ativo já cadastrado: " + ativo.getNome(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Ativo novoAtivo = this.definirAtivo();

                long id = new AtivoRepository(context).inserir(novoAtivo);
                Snackbar.make(view, "Ativo cadastrado com sucesso: " + id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                this.carregarTelaPrincipal();
            }
        } else {
            Snackbar.make(view, "Necessário preencher o campo Nome!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @NonNull
    private Ativo definirAtivo() {
        Ativo novoAtivo = new Ativo();
        novoAtivo.setNome(nomeAtivo.getText().toString());
        novoAtivo.setDescricao(descricaoAtivo.getText().toString());
        novoAtivo.setDataCriacao(LocalDate.now());
        return novoAtivo;
    }

    private void carregarTelaPrincipal() {
        Intent intent = new Intent(context, PrincipalActivity.class);
        startActivity(intent);
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