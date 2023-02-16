package com.llo.lafinance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.llo.lafinance.R;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.CompraRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CadastraCompraActivity extends AppCompatActivity {

    private EditText ativo;
    private EditText quantidade;
    private EditText valorUnitario;
    private CompraRepository compraRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_compra);

        ativo = findViewById(R.id.edittextativo);
        quantidade = findViewById(R.id.edittextquantidade);
        valorUnitario = findViewById(R.id.edittextvalorunitario);

        compraRepository = new CompraRepository(this);
    }

    public void salvar(View view) {
        if (ativo.getText().length() > 0
                && quantidade.getText().length() > 0
                && valorUnitario.getText().length() > 0) {
            Compra compra = new Compra();
            compra.setAtivo(ativo.getText().toString());
            compra.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            compra.setPrecoUnitario(new BigDecimal(valorUnitario.getText().toString()));
            compra.setDataCriacao(LocalDate.now());
            compra.setPrecoTotal(compra.getPrecoUnitario().multiply(BigDecimal.valueOf(compra.getQuantidade())));

            long id = this.compraRepository.inserir(compra);
            Snackbar.make(view, "Compra realizada com sucesso: " + id, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            this.carregarTelaPrincipal();
        } else {

            Snackbar.make(view, "Necessário preencher todos os campos!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void carregarTelaPrincipal() {
        Intent intent = new Intent(CadastraCompraActivity.this, PrincipalActivity.class);
        startActivity(intent);
    }
}