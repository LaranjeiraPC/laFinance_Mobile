package com.llo.lafinance.ui.home.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.llo.lafinance.databinding.FragmentHomeBinding;
import com.llo.lafinance.domain.HomeService;
import com.llo.lafinance.model.Carteira;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.ConfiguracaoRepository;
import com.llo.lafinance.repositorio.VendaRepository;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private Context context;
    private FragmentHomeBinding binding;
    private CompraRepository compraRepository;
    private ConfiguracaoRepository configuracaoRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        compraRepository = new CompraRepository(context);
        configuracaoRepository = new ConfiguracaoRepository(context);

        final TextView idBoasVindas = binding.idBoasVindasValorHome;
        final TextView ativo1 = binding.idAtivoUmValorHome;
        final TextView ativo2 = binding.idAtivoDoisValorHome;
        final TextView ativo3 = binding.idAtivoTresValorHome;
        final TextView ativo4 = binding.idAtivoQuatroValorHome;
        final TextView ativo5 = binding.idAtivoCincoValorHome;
        final PieChart idGraficoHome = binding.idGraficoHome;

        final LinearLayout idUmCampo = binding.idUmCampoLegendaHome;
        final LinearLayout idDoisCampo = binding.idDoisCampoLegendaHome;
        final LinearLayout idTresCampo = binding.idTresCampoLegendaHome;
        final LinearLayout idQuatroCampo = binding.idQuatroCampoLegendaHome;
        final LinearLayout idCincoCampo = binding.idCincoCampoLegendaHome;

        final TextView idTotalInvestidoValorHome = binding.idTotalInvestidoValorHome;
        final TextView iLucroLiquidoTotalValorHome = binding.iLucroLiquidoTotalValorHome;
        final TextView iMesLucroLiquidoValorHome = binding.iMesLucroLiquidoValorHome;
        final TextView iMesLucroLiquidoDadoValorHome = binding.iMesLucroLiquidoDadoValorHome;

        this.definirBoasVindas(idBoasVindas);
        this.definirDadosExibicao(ativo1, ativo2, ativo3, ativo4, ativo5,
                idGraficoHome, idUmCampo, idDoisCampo, idTresCampo, idQuatroCampo, idCincoCampo,
                idTotalInvestidoValorHome, iLucroLiquidoTotalValorHome, iMesLucroLiquidoValorHome, iMesLucroLiquidoDadoValorHome);
        return root;
    }

    private void definirDadosExibicao(TextView ativo1, TextView ativo2, TextView ativo3, TextView ativo4, TextView ativo5, PieChart idGraficoHome, LinearLayout idUmCampo, LinearLayout idDoisCampo, LinearLayout idTresCampo, LinearLayout idQuatroCampo, LinearLayout idCincoCampo, TextView idTotalInvestidoValorHome, TextView iLucroLiquidoTotalValorHome, TextView iMesLucroLiquidoValorHome, TextView iMesLucroLiquidoDadoValorHome) {
        List<Compra> compras = this.compraRepository.consultarComprasDisponiveisPorQuantidadeAgrupada();
        this.setData(ativo1, ativo2, ativo3, ativo4, ativo5, idGraficoHome, compras);
        this.desabilitarLayout(ativo1, ativo2, ativo3, ativo4, ativo5, idUmCampo, idDoisCampo, idTresCampo, idQuatroCampo, idCincoCampo);

        Carteira carteira = new HomeService(this.compraRepository, new VendaRepository(context), new CarteiraRepository(context)).consultarCarteira();
        if (Objects.nonNull(carteira.getId())) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            idTotalInvestidoValorHome.setText(numberFormat.format(carteira.getTotalInvestido()));
            iLucroLiquidoTotalValorHome.setText(numberFormat.format(carteira.getLucroLiquidoTotalAno()));
            iMesLucroLiquidoValorHome.setText(carteira.getMesLucroLiquido());
            iMesLucroLiquidoDadoValorHome.setText(numberFormat.format(carteira.getValorMesLucroLiquido()));
        }
    }

    private void definirBoasVindas(TextView idBoasVindas) {
        String usuario = this.configuracaoRepository.consultarConfiguracao().getNomeUsuario();
        if (LocalDateTime.now().getHour() >= 0 && LocalDateTime.now().getHour() <= 11) {
            idBoasVindas.setText("Bom dia, " + usuario + "!");
        } else if (LocalDateTime.now().getHour() > 11 && LocalDateTime.now().getHour() < 18) {
            idBoasVindas.setText("Boa tarde, " + usuario + "!");
        } else if (LocalDateTime.now().getHour() >= 18) {
            idBoasVindas.setText("Boa noite, " + usuario + "!");
        }
    }

    private void desabilitarLayout(TextView ativo1, TextView ativo2, TextView ativo3, TextView ativo4, TextView ativo5, LinearLayout idUmCampo, LinearLayout idDoisCampo, LinearLayout idTresCampo, LinearLayout idQuatroCampo, LinearLayout idCincoCampo) {
        if (ativo1.getText().length() == 0)
            idUmCampo.setVisibility(View.INVISIBLE);

        if (ativo2.getText().length() == 0)
            idDoisCampo.setVisibility(View.INVISIBLE);

        if (ativo3.getText().length() == 0)
            idTresCampo.setVisibility(View.INVISIBLE);

        if (ativo4.getText().length() == 0)
            idQuatroCampo.setVisibility(View.INVISIBLE);

        if (ativo5.getText().length() == 0)
            idCincoCampo.setVisibility(View.INVISIBLE);
    }

    private void setData(TextView ativo1, TextView ativo2, TextView ativo3, TextView ativo4, TextView ativo5, PieChart idGraficoHome, List<Compra> compras) {
        for (int i = 0; i < compras.size(); i++) {
            switch (i) {
                case 0: {
                    ativo1.setText(compras.get(i).getAtivo() + " - " + compras.get(i).getQuantidade().toString());
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#FFA726")));
                    break;
                }
                case 1: {
                    ativo2.setText(compras.get(i).getAtivo() + " - " + compras.get(i).getQuantidade().toString());
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#66BB6A")));
                    break;
                }
                case 2: {
                    ativo3.setText(compras.get(i).getAtivo() + " - " + compras.get(i).getQuantidade().toString());
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#EF5350")));
                    break;
                }
                case 3: {
                    ativo4.setText(compras.get(i).getAtivo() + " - " + compras.get(i).getQuantidade().toString());
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#29B6F6")));
                    break;
                }
                case 4: {
                    ativo5.setText(compras.get(i).getAtivo() + " - " + compras.get(i).getQuantidade().toString());
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#CCA57F")));
                    break;
                }
            }
            idGraficoHome.startAnimation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}