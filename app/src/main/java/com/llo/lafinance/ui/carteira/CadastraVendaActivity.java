package com.llo.lafinance.ui.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.llo.lafinance.R;
import com.llo.lafinance.domain.service.HomeService;
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

        this.nomeAtivo = findViewById(R.id.editTextAtivoCompraVenda);
        this.quantidadeCompra = findViewById(R.id.editTextQuantidadeValorCompraVenda);
        this.precoUnitarioCompra = findViewById(R.id.editTextPrecoUnitarioValorCompraVenda);
        this.precoTotalCompra = findViewById(R.id.editTextTotalValorCompraVenda);
        this.quantidadeVenda = findViewById(R.id.editTextQuantidadeValorVenda);
        this.precoUnitarioVenda = findViewById(R.id.editTextValorUnitarioValorVenda);

        this.idCompra = Integer.parseInt(txtBundle.getString("idCompra"));
        this.nomeAtivo.setText(txtBundle.getString("nomeAtivoCompra"));
        this.quantidadeCompra.setText(txtBundle.getString("quantidadeCompra"));
        this.precoUnitarioCompra.setText(txtBundle.getString("precoUnitarioCompra"));
        this.precoTotalCompra.setText(txtBundle.getString("precoTotalCompra"));

        this.compraRepository = new CompraRepository(this);
        this.vendaRepository = new VendaRepository(this);

        ImageView buttonSalvar = findViewById(R.id.idImageVenderCompra);
        buttonSalvar.setOnClickListener(view -> this.salvar());

        ImageView buttonExcluir = findViewById(R.id.idImageCancelarVenda);
        buttonExcluir.setOnClickListener(view -> this.cancelar());

    }

    public void salvar() {
        if (this.quantidadeVenda.getText().length() > 0 && this.precoUnitarioVenda.getText().length() > 0) {
            BigDecimal precoTotalCompra = this.compraRepository.consultarCompraPorId(this.idCompra).getPrecoTotal();
            Venda venda = new Venda();
            venda.setCompra(idCompra);
            venda.setQuantidade(Integer.parseInt(this.quantidadeVenda.getText().toString()));
            venda.setPrecoUnitario(new BigDecimal(this.precoUnitarioVenda.getText().toString()));
            venda.setDataCriacao(LocalDate.now());
            venda.setPrecoTotal(venda.getPrecoUnitario().multiply(BigDecimal.valueOf(venda.getQuantidade())));
            venda.setLucroTotal(venda.getPrecoTotal().subtract(precoTotalCompra));

            this.vendaRepository.inserir(venda);
            Toast.makeText(this, "Venda realizada com sucesso!", Toast.LENGTH_LONG).show();

            this.compraRepository.atualizarStatus(this.idCompra, Status.VENDIDO);
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

    public void cancelar() {
        this.carregarTelaPrincipal();
    }
}