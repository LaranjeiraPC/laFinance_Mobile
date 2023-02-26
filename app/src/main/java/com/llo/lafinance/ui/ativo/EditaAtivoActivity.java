package com.llo.lafinance.ui.ativo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.llo.lafinance.R;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.ui.ativo.fragment.ExcluiAtivoFragment;
import com.llo.lafinance.ui.principal.PrincipalActivity;

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
            if (!ativo.getNome().equals(nomeAtivo.getText().toString().toUpperCase()) && existeCompras) {
                Toast.makeText(this, "Existente compras de ações para o nome do ativo anterior!", Toast.LENGTH_LONG).show();
            } else {
                ativo = this.definirAtivo(ativo, nomeAtivo, descricaoAtivo);
                long id = this.ativoRepository.atualizar(ativo);
                Toast.makeText(this, "Ativo atualizado com sucesso: " + id, Toast.LENGTH_LONG).show();

                this.retornarTelaPrincipal();
            }
        } else {
            Toast.makeText(this, "Necessário preencher o campo Nome!", Toast.LENGTH_LONG).show();
        }
    }

    public void excluir(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("idAtivo", idAtivo.toString());

        DialogFragment dialogFragment = new ExcluiAtivoFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "excluiativo");
    }

    private void validaAtivoExistente(View view) {
        Ativo ativoBanco = this.ativoRepository.consultarAtivo(nomeAtivo.getText().toString());
        if (Objects.nonNull(ativoBanco))
            Toast.makeText(this, "Ativo já cadastrado: " + ativoBanco.getNome(), Toast.LENGTH_LONG).show();
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