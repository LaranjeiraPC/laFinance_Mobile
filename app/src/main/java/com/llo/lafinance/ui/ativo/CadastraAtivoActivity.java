package com.llo.lafinance.ui.ativo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.llo.lafinance.R;
import com.llo.lafinance.activity.PrincipalActivity;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.ui.carteira.CadastraCompraActivity;

import java.time.LocalDate;
import java.util.Objects;

public class CadastraAtivoActivity extends AppCompatActivity {
    private EditText nomeAtivo;
    private EditText descricaoAtivo;
    private AtivoRepository ativoRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_ativo);

        nomeAtivo = findViewById(R.id.editTextNomeAtivo);
        descricaoAtivo = findViewById(R.id.editTextDescricaoAtivo);

        ativoRepository = new AtivoRepository(this);
    }

    public void salvar(View view) {
        if (Objects.nonNull(nomeAtivo) && nomeAtivo.getText().length() > 0) {

            Ativo ativo = this.ativoRepository.consultarAtivo(nomeAtivo.getText().toString());

            if (Objects.nonNull(ativo)) {
                Snackbar.make(view, "Ativo já cadastrado: " + ativo.getNome(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Ativo novoAtivo = this.definirAtivo(nomeAtivo, descricaoAtivo);

                long id = this.ativoRepository.inserir(novoAtivo);
                Snackbar.make(view, "Ativo cadastrado com sucesso: " + id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                this.retornarTelaPrincipal();
            }
        } else {
            Snackbar.make(view, "Necessário preencher o campo Nome!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private Ativo definirAtivo(EditText nomeAtivo, EditText descricaoAtivo) {
        Ativo ativo = new Ativo();
        ativo.setNome(nomeAtivo.getText().toString());
        ativo.setDescricao(descricaoAtivo.getText().toString());
        ativo.setDataCriacao(LocalDate.now());
        ativo.setDataAtualizacao(LocalDate.now());
        return ativo;
    }

    private void retornarTelaPrincipal() {
        Intent intent = new Intent(CadastraAtivoActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancelar(View view) {
        finish();
    }
}