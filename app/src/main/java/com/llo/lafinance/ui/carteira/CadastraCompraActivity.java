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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.llo.lafinance.R;
import com.llo.lafinance.domain.HomeService;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.model.enums.Status;
import com.llo.lafinance.repositorio.AtivoRepository;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.VendaRepository;
import com.llo.lafinance.ui.principal.PrincipalActivity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CadastraCompraActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String ativo;
    private EditText quantidade;
    private EditText valorUnitario;
    private EditText metaPrecoUnitarioVenda;
    private String[] nomeAtivos;
    private CompraRepository compraRepository;
    private AtivoRepository ativoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_compra);

        quantidade = findViewById(R.id.edittextquantidade);
        valorUnitario = findViewById(R.id.edittextvalorunitario);
        metaPrecoUnitarioVenda = findViewById(R.id.edittextmetaprecounitariovenda);

        compraRepository = new CompraRepository(this);
        ativoRepository = new AtivoRepository(this);

        List<Ativo> ativos = this.ativoRepository.consultarAtivos();
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
            compra.setStatus(Status.DISPONIVEL);
            compra.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            compra.setPrecoUnitario(new BigDecimal(valorUnitario.getText().toString()));
            compra.setMetaPrecoUnitarioVenda(new BigDecimal(metaPrecoUnitarioVenda.getText().toString()));
            compra.setDataCriacao(LocalDate.now());
            compra.setPrecoTotal(compra.getPrecoUnitario().multiply(BigDecimal.valueOf(compra.getQuantidade())));

            long id = this.compraRepository.inserir(compra);
            Toast.makeText(this, "Compra realizada com sucesso: " + id, Toast.LENGTH_LONG).show();

            new HomeService(this.compraRepository, new VendaRepository(this), new CarteiraRepository(this)).atualizarCarteira();
            this.carregarTelaPrincipal();
        } else {
            Toast.makeText(this, "Necess??rio preencher todos os campos!", Toast.LENGTH_LONG).show();
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