package com.llo.lafinance.ui.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.llo.lafinance.R;
import com.llo.lafinance.activity.PrincipalActivity;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.repositorio.CompraRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditaCompraActivity extends AppCompatActivity {

    private EditText ativo;
    private EditText quantidade;
    private EditText valorUnitario;
    private Integer id;
    private CompraRepository compraRepository;
    private AtivoRepository ativoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_compra);

        Bundle txtBundle = getIntent().getExtras();
        String idCompra = txtBundle.getString("id");
        String nome = txtBundle.getString("nomeAtivo");
        String quantidadeCompra = txtBundle.getString("quantidade");
        String valorUnitarioCompra = txtBundle.getString("valorUnitario");

        ativo = findViewById(R.id.editTextAtivo);
        quantidade = findViewById(R.id.editTextQuantidade);
        valorUnitario = findViewById(R.id.editTextUnitario);

        id = Integer.parseInt(idCompra);
        ativo.setText(nome);
        quantidade.setText(quantidadeCompra);
        valorUnitario.setText(valorUnitarioCompra);

        compraRepository = new CompraRepository(this);
        ativoRepository = new AtivoRepository(this);
    }

    public void salvar(View view) {
        if (quantidade.getText().length() > 0 && valorUnitario.getText().length() > 0) {
            Compra compra = this.compraRepository.consultarCompraPorId(id);
            compra.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            compra.setPrecoUnitario(new BigDecimal(valorUnitario.getText().toString()));
            compra.setPrecoTotal(compra.getPrecoUnitario().multiply(BigDecimal.valueOf(compra.getQuantidade())));
            compra.setDataAtualizacao(LocalDate.now());

            long id = this.compraRepository.atualizar(compra);
            Snackbar.make(view, "Compra atualizada com sucesso: " + id, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            this.carregarTelaPrincipal();
        } else {
            Snackbar.make(view, "Necessário preencher todos os campos!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void excluir(View view) {
        Compra compra = this.compraRepository.consultarCompraPorId(id);
        long id = this.compraRepository.deletar(compra.getId());
        Snackbar.make(view, "Compra excluído com sucesso: " + id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        this.carregarTelaPrincipal();
    }

    private void carregarTelaPrincipal() {
        Intent intent = new Intent(EditaCompraActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancelar(View view) {
        this.finish();
    }
}