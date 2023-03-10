package com.llo.lafinance.ui.relatorio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.llo.lafinance.adapter.RelatorioAdapter;
import com.llo.lafinance.databinding.FragmentRelatorioBinding;
import com.llo.lafinance.model.Ativo;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.model.Relatorio;
import com.llo.lafinance.model.Venda;
import com.llo.lafinance.repositorio.AtivoRepository;
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

    private Context context;
    private FragmentRelatorioBinding binding;
    private CompraRepository compraRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.binding = FragmentRelatorioBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        final ListView listViewRelatorio = this.binding.idlistViewRelatorio;
        final TextView idTotalValorRelatorio = this.binding.idTotalValorRelatorio;
        final Spinner idSpinnerAnoRelatorio = this.binding.idSpinnerAnoRelatorio;
        final Spinner idSpinnerMesRelatorio = this.binding.idSpinnerMesRelatorio;
        final Spinner idSpinnerAtivoRelatorio = this.binding.idSpinnerAtivoRelatorio;

        compraRepository = new CompraRepository(context);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        ArrayList<Venda> vendas = new VendaRepository(this.context).consultarVendas();
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

        this.definirFiltro(idSpinnerAnoRelatorio, idSpinnerMesRelatorio, idSpinnerAtivoRelatorio, vendas);
        this.definirRelatorio(listViewRelatorio, new ArrayList<>(relatorio));
        return root;
    }

    private void definirFiltro(Spinner idSpinnerAnoRelatorio, Spinner idSpinnerMesRelatorio, Spinner idSpinnerAtivoRelatorio, ArrayList<Venda> vendas) {
        List<Integer> ano = vendas.stream().map(Venda::getDataCriacao).map(LocalDate::getYear).collect(Collectors.toList());
        ArrayAdapter adapterAno = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, ano);
        idSpinnerAnoRelatorio.setAdapter(adapterAno);
        adapterAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> mes = vendas.stream().map(Venda::getDataCriacao).map(LocalDate::getMonthValue).map(Objects::toString).collect(Collectors.toList());
        ArrayAdapter adapterMes = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, mes);
        idSpinnerMesRelatorio.setAdapter(adapterMes);
        adapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> ativo = vendas.stream().map(Venda::getCompra).map(c -> this.compraRepository.consultarCompraPorId(c).getAtivo()).collect(Collectors.toList());

        ArrayAdapter adapterAtivo = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, ativo);
        idSpinnerAtivoRelatorio.setAdapter(adapterAtivo);
        adapterAtivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void definirRelatorio(ListView listViewRelatorio, ArrayList<Relatorio> relatorio) {
        RelatorioAdapter relatorioAdapter = new RelatorioAdapter(this.context, relatorio);
        listViewRelatorio.setAdapter(relatorioAdapter);
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