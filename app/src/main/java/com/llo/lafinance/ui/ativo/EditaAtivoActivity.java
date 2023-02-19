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
import com.llo.lafinance.repositorio.CompraRepository;

import java.time.LocalDate;
import java.util.Objects;

public class EditaAtivoActivity extends AppCompatActivity {

    private Integer idAtivo;
    private EditText nomeAtivo;
    private EditText descricaoAtivo;
    private AtivoRepository ativoRepository;
    private CompraRepository compraRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_ativo);

        Bundle txtBundle = getIntent().getExtras();
        String id = txtBundle.getString("id");
        String nome = txtBundle.getString("nome");
        String descricao = txtBundle.getString("descricao");

        nomeAtivo = findViewById(R.id.editTextNomeAtivo);
        descricaoAtivo = findViewById(R.id.editTextDescricaoAtivo);

        idAtivo = Integer.parseInt(id);
        nomeAtivo.setText(nome);
        descricaoAtivo.setText(descricao);

        ativoRepository = new AtivoRepository(this);
        compraRepository = new CompraRepository(this);
    }

    public void salvar(View view) {
        if (Objects.nonNull(nomeAtivo) && nomeAtivo.getText().length() > 0) {
            this.validaAtivoExistente(view);

            Ativo ativo = this.ativoRepository.consultarAtivoPorId(idAtivo);

            boolean existeCompras = compraRepository.consultarComprasPorNomeAtivo(ativo.getNome());
            if (existeCompras) {
                Snackbar.make(view, "Existente compras de ações para o nome do ativo anterior!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                ativo = this.definirAtivo(ativo, nomeAtivo, descricaoAtivo);

                long id = this.ativoRepository.atualizar(ativo);
                Snackbar.make(view, "Ativo atualizado com sucesso: " + id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                this.retornarTelaPrincipal();
            }
        } else {
            Snackbar.make(view, "Necessário preencher o campo Nome!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void excluir(View view) {
        Ativo ativo = this.ativoRepository.consultarAtivoPorId(idAtivo);

        boolean existeCompras = compraRepository.consultarComprasPorNomeAtivo(ativo.getNome());
        if (existeCompras) {
            Snackbar.make(view, "Existente compras de ações!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            this.definirAtivo(ativo, nomeAtivo, descricaoAtivo);

            long id = this.ativoRepository.deletar(idAtivo);
            Snackbar.make(view, "Ativo excluído com sucesso: " + id, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            this.retornarTelaPrincipal();
        }
    }

    private void validaAtivoExistente(View view) {
        Ativo ativoBanco = this.ativoRepository.consultarAtivo(nomeAtivo.getText().toString());
        if (Objects.nonNull(ativoBanco))
            Snackbar.make(view, "Ativo já cadastrado: " + ativoBanco.getNome(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
    }

    private Ativo definirAtivo(Ativo ativo, EditText nomeAtivo, EditText descricaoAtivo) {
        ativo.setNome(nomeAtivo.getText().toString());
        ativo.setDescricao(descricaoAtivo.getText().toString());
        ativo.setDataAtualizacao(LocalDate.now());
        return ativo;
    }

    private void retornarTelaPrincipal() {
        Intent intent = new Intent(EditaAtivoActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancelar(View view) {
        finish();
    }
}