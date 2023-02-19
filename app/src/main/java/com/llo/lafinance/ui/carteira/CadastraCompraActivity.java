package com.llo.lafinance.ui.carteira;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.llo.lafinance.R;
import com.llo.lafinance.activity.PrincipalActivity;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.repositorio.CompraRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CadastraCompraActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String ativo;
    private EditText quantidade;
    private EditText valorUnitario;
    private String[] nomeAtivos;
    private CompraRepository compraRepository;
    private AtivoRepository ativoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_compra);

        quantidade = findViewById(R.id.edittextquantidade);
        valorUnitario = findViewById(R.id.edittextvalorunitario);

        compraRepository = new CompraRepository(this);
        ativoRepository = new AtivoRepository(this);

        List<Ativo> ativos = ativoRepository.consultarAtivos();
        List<String> tempNomeAtivos = ativos.stream().map(Ativo::getNome).collect(Collectors.toList());

        String[] strarray = new String[tempNomeAtivos.size()];
        nomeAtivos = tempNomeAtivos.toArray(strarray);

        this.listarAtivos(nomeAtivos);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        ativo = nomeAtivos[pos];
        ((TextView) view).setTextColor(Color.BLACK);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void listarAtivos(String[] nomeAtivos) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_ativo);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, nomeAtivos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void salvar(View view) {
        if (ativo.length() > 0 && quantidade.getText().length() > 0 && valorUnitario.getText().length() > 0) {
            Compra compra = new Compra();
            compra.setAtivo(ativo);
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
        finish();
    }

    public void cancelar(View view) {
        this.carregarTelaPrincipal();
    }
}