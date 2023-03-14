package com.llo.lafinance.ui.relatorio.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.llo.lafinance.adapter.RelatorioAdapter;
import com.llo.lafinance.databinding.FragmentRelatorioBinding;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.model.Relatorio;
import com.llo.lafinance.model.Venda;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.VendaRepository;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RelatorioFragment extends Fragment {

    private Spinner idSpinnerAnoRelatorio;
    private Spinner idSpinnerMesRelatorio;
    private ListView listViewRelatorio;
    private TextView idTotalValorRelatorio;
    private String ano;
    private String mes;
    private String[] anos;
    private String[] meses;

    private Context context;
    private FragmentRelatorioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.binding = FragmentRelatorioBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        listViewRelatorio = this.binding.idlistViewRelatorio;
        idTotalValorRelatorio = this.binding.idTotalValorRelatorio;
        idSpinnerAnoRelatorio = this.binding.idSpinnerAnoRelatorio;
        idSpinnerMesRelatorio = this.binding.idSpinnerMesRelatorio;

        ArrayList<Venda> vendas = this.consultarDadosRelatorio(listViewRelatorio, idTotalValorRelatorio, null, null);

        this.definirFiltro(idSpinnerAnoRelatorio, idSpinnerMesRelatorio, vendas);

        idSpinnerAnoRelatorio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if (Objects.nonNull(anos)) {
                    ano = anos[position];
                    ((TextView) view).setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        idSpinnerMesRelatorio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                if (Objects.nonNull(meses)) {
                    mes = meses[position];
                    ((TextView) view).setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button button = this.binding.idFiltro;
        button.setOnClickListener(this::filtarRelatorio);
        return root;
    }

    @NonNull
    private ArrayList<Venda> consultarDadosRelatorio(ListView listViewRelatorio, TextView idTotalValorRelatorio, String anoTemp, String mesTemp) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        ArrayList<Venda> vendas = new VendaRepository(this.context).consultarVendas(anoTemp, mesTemp);
        List<Relatorio> relatorio = vendas.stream().map(v -> {
            Compra compra = new CompraRepository(this.context).consultarCompraPorId(v.getCompra());
            Relatorio relatorioTemp = new Relatorio();
            relatorioTemp.setAtivo(compra.getAtivo());
            relatorioTemp.setQuantidadeCompra(compra.getQuantidade());
            relatorioTemp.setPrecoUnitarioCompra(compra.getPrecoUnitario());
            relatorioTemp.setPrecoTotalCompra(compra.getPrecoTotal());
            relatorioTemp.setDataCriacaoCompra(compra.getDataCriacao());
            relatorioTemp.setQuantidadeVenda(v.getQuantidade());
            relatorioTemp.setPrecoUnitarioVenda(v.getPrecoUnitario());
            relatorioTemp.setPrecoTotalVenda(v.getPrecoTotal());
            relatorioTemp.setDataCriacaoVenda(v.getDataCriacao());
            relatorioTemp.setLucroTotal(v.getLucroTotal());
            return relatorioTemp;
        }).collect(Collectors.toList());

        idTotalValorRelatorio.setText(numberFormat.format(relatorio.stream().map(Relatorio::getLucroTotal).reduce(BigDecimal.ZERO, BigDecimal::add)));
        this.definirRelatorio(listViewRelatorio, new ArrayList<>(relatorio));
        return vendas;
    }

    private void definirFiltro(Spinner idSpinnerAnoRelatorio, Spinner idSpinnerMesRelatorio, ArrayList<Venda> vendas) {
        List<String> anoTemp = vendas.stream().map(Venda::getDataCriacao).map(LocalDate::getYear).map(String::valueOf).sorted().collect(Collectors.toList());
        anos = anoTemp.stream().toArray(String[]::new);
        this.ano = anos[0];

        ArrayAdapter adapterAno = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, anoTemp);
        idSpinnerAnoRelatorio.setAdapter(adapterAno);
        adapterAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterAno.setNotifyOnChange(true);

        List<String> mesTemp = vendas.stream().map(Venda::getDataCriacao).map(LocalDate::getMonthValue).map(Objects::toString).sorted().collect(Collectors.toList());
        mesTemp.add("");

        meses = mesTemp.stream().toArray(String[]::new);
        this.mes = meses[0];

        ArrayAdapter adapterMes = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, mesTemp);
        idSpinnerMesRelatorio.setAdapter(adapterMes);
        adapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMes.setNotifyOnChange(true);
    }

    private void definirRelatorio(ListView listViewRelatorio, ArrayList<Relatorio> relatorio) {
        RelatorioAdapter relatorioAdapter = new RelatorioAdapter(this.context, relatorio);
        listViewRelatorio.setAdapter(relatorioAdapter);
    }

    private void filtarRelatorio(View view) {
        this.consultarDadosRelatorio(listViewRelatorio, idTotalValorRelatorio, ano, mes);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }
}