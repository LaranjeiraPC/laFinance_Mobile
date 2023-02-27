package com.llo.lafinance.ui.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.llo.lafinance.R;
import com.llo.lafinance.domain.HomeService;
import com.llo.lafinance.model.Venda;
import com.llo.lafinance.model.enums.Status;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.VendaRepository;
import com.llo.lafinance.ui.principal.PrincipalActivity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CadastraVendaActivity extends AppCompatActivity {

    private EditText nomeAtivo;
    private Integer idCompra;
    private EditText quantidadeCompra;
    private EditText precoUnitarioCompra;
    private EditText precoTotalCompra;
    private EditText quantidadeVenda;
    private EditText precoUnitarioVenda;
    private CompraRepository compraRepository;
    private VendaRepository vendaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_venda);

        Bundle txtBundle = getIntent().getExtras();
        String idCompraTemp = txtBundle.getString("idCompra");
        String nomeAtivoCompraTemp = txtBundle.getString("nomeAtivoCompra");
        String quantidadeCompraTemp = txtBundle.getString("quantidadeCompra");
        String precoUnitarioCompraTemp = txtBundle.getString("precoUnitarioCompra");
        String precoTotalCompraTemp = txtBundle.getString("precoTotalCompra");

        nomeAtivo = findViewById(R.id.editTextAtivoCompraVenda);
        quantidadeCompra = findViewById(R.id.editTextQuantidadeValorCompraVenda);
        precoUnitarioCompra = findViewById(R.id.editTextPrecoUnitarioValorCompraVenda);
        precoTotalCompra = findViewById(R.id.editTextTotalValorCompraVenda);
        quantidadeVenda = findViewById(R.id.editTextQuantidadeValorVenda);
        precoUnitarioVenda = findViewById(R.id.editTextValorUnitarioValorVenda);

        idCompra = Integer.parseInt(idCompraTemp);
        nomeAtivo.setText(nomeAtivoCompraTemp);
        quantidadeCompra.setText(quantidadeCompraTemp);
        precoUnitarioCompra.setText(precoUnitarioCompraTemp);
        precoTotalCompra.setText(precoTotalCompraTemp);

        compraRepository = new CompraRepository(this);
        vendaRepository = new VendaRepository(this);
    }

    public void salvar(View view) {
        if (quantidadeVenda.getText().length() > 0 && precoUnitarioVenda.getText().length() > 0) {
            BigDecimal precoTotalCompra = this.compraRepository.consultarCompraPorId(idCompra).getPrecoTotal();
            Venda venda = new Venda();
            venda.setCompra(idCompra);
            venda.setQuantidade(Integer.parseInt(quantidadeVenda.getText().toString()));
            venda.setPrecoUnitario(new BigDecimal(precoUnitarioVenda.getText().toString()));
            venda.setDataCriacao(LocalDate.now());
            venda.setPrecoTotal(venda.getPrecoUnitario().multiply(BigDecimal.valueOf(venda.getQuantidade())));
            venda.setLucroTotal(venda.getPrecoTotal().subtract(precoTotalCompra));

            long id = this.vendaRepository.inserir(venda);
            Toast.makeText(this, "Venda realizada com sucesso: " + id, Toast.LENGTH_LONG).show();

            this.compraRepository.atualizarStatus(idCompra, Status.VENDIDO);
            new HomeService(this.compraRepository, this.vendaRepository, new CarteiraRepository(this)).atualizarCarteira();
            this.carregarTelaPrincipal();
        } else {
            Toast.makeText(this, "Necessário preencher todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    private void carregarTelaPrincipal() {
        Intent intent = new Intent(CadastraVendaActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancelar(View view) {
        this.carregarTelaPrincipal();
    }
}