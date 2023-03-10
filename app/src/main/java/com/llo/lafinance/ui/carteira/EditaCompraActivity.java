package com.llo.lafinance.ui.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.llo.lafinance.R;
import com.llo.lafinance.domain.HomeService;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.VendaRepository;
import com.llo.lafinance.ui.carteira.fragment.ExcluiCarteiraFragment;
import com.llo.lafinance.ui.principal.PrincipalActivity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditaCompraActivity extends AppCompatActivity {

    private EditText ativo;
    private EditText quantidade;
    private EditText valorUnitario;
    private EditText metaPrecoUnitarioVenda;
    private Integer id;
    private BigDecimal totalCompra;
    private CompraRepository compraRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_compra);

        Bundle txtBundle = getIntent().getExtras();
        String idCompra = txtBundle.getString("id");
        String nome = txtBundle.getString("nomeAtivo");
        String quantidadeCompra = txtBundle.getString("quantidade");
        String valorUnitarioCompra = txtBundle.getString("valorUnitario");
        String metaPrecoUnitarioVendaCompra = txtBundle.getString("metaPrecoUnitarioVenda");
        String total = txtBundle.getString("totalCompra");

        ativo = findViewById(R.id.editTextAtivo);
        quantidade = findViewById(R.id.editTextQuantidade);
        valorUnitario = findViewById(R.id.editTextUnitario);
        metaPrecoUnitarioVenda = findViewById(R.id.edittextmetaprecounitariovenda);

        id = Integer.parseInt(idCompra);
        ativo.setText(nome);
        quantidade.setText(quantidadeCompra);
        valorUnitario.setText(valorUnitarioCompra);
        metaPrecoUnitarioVenda.setText(metaPrecoUnitarioVendaCompra);
        totalCompra = new BigDecimal(total);

        compraRepository = new CompraRepository(this);
    }

    public void salvar(View view) {
        if (quantidade.getText().length() > 0 && valorUnitario.getText().length() > 0) {
            Compra compra = this.compraRepository.consultarCompraPorId(id);
            compra.setQuantidade(Integer.parseInt(quantidade.getText().toString()));
            compra.setPrecoUnitario(new BigDecimal(valorUnitario.getText().toString()));
            compra.setPrecoTotal(compra.getPrecoUnitario().multiply(BigDecimal.valueOf(compra.getQuantidade())));
            compra.setMetaPrecoUnitarioVenda(new BigDecimal(metaPrecoUnitarioVenda.getText().toString()));
            compra.setDataAtualizacao(LocalDate.now());

            long id = this.compraRepository.atualizar(compra);
            Toast.makeText(this, "Compra atualizada com sucesso: " + id, Toast.LENGTH_LONG).show();
            new HomeService(this.compraRepository, new VendaRepository(this), new CarteiraRepository(this)).atualizarCarteira();
            this.carregarTelaPrincipal();
        } else {
            Toast.makeText(this, "Necess??rio preencher todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    public void excluir(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("idCompra", id.toString());

        DialogFragment excluiCarteiraFragment = new ExcluiCarteiraFragment();
        excluiCarteiraFragment.setArguments(bundle);
        excluiCarteiraFragment.show(getSupportFragmentManager(), "excluicompra");
    }

    public void vender(View view) {
        Intent intent = new Intent(EditaCompraActivity.this, CadastraVendaActivity.class);

        Bundle txtBundle = new Bundle();
        txtBundle.putString("idCompra", id.toString());
        txtBundle.putString("nomeAtivoCompra", ativo.getText().toString());
        txtBundle.putString("quantidadeCompra", quantidade.getText().toString());
        txtBundle.putString("precoUnitarioCompra", valorUnitario.getText().toString());
        txtBundle.putString("precoTotalCompra", totalCompra.toString());

        intent.putExtras(txtBundle);
        startActivity(intent);
        finish();
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