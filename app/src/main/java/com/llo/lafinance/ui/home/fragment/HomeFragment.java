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
import com.llo.lafinance.domain.service.HomeService;
import com.llo.lafinance.model.Carteira;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.ConfiguracaoRepository;
import com.llo.lafinance.repositorio.VendaRepository;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.math.BigDecimal;
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
        this.binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = this.binding.getRoot();

        this.compraRepository = new CompraRepository(this.context);
        this.configuracaoRepository = new ConfiguracaoRepository(this.context);

        this.definirBoasVindas(this.binding.idBoasVindasValorHome);
        this.definirDadosExibicao(
                this.binding.idAtivoUmValorHome,
                this.binding.idAtivoDoisValorHome,
                this.binding.idAtivoTresValorHome,
                this.binding.idAtivoQuatroValorHome,
                this.binding.idAtivoCincoValorHome,
                this.binding.idGraficoHome,
                this.binding.idUmCampoLegendaHome,
                this.binding.idDoisCampoLegendaHome,
                this.binding.idTresCampoLegendaHome,
                this.binding.idQuatroCampoLegendaHome,
                this.binding.idCincoCampoLegendaHome,
                this.binding.idTotalInvestidoValorHome,
                this.binding.iLucroLiquidoTotalValorHome,
                this.binding.iMesLucroLiquidoValorHome,
                this.binding.iMesLucroLiquidoDadoValorHome);
        return root;
    }

    private void definirDadosExibicao(TextView ativo1, TextView ativo2, TextView ativo3, TextView ativo4,
                                      TextView ativo5, PieChart idGraficoHome, LinearLayout idUmCampo,
                                      LinearLayout idDoisCampo, LinearLayout idTresCampo, LinearLayout idQuatroCampo,
                                      LinearLayout idCincoCampo, TextView idTotalInvestidoValorHome,
                                      TextView iLucroLiquidoTotalValorHome, TextView iMesLucroLiquidoValorHome,
                                      TextView iMesLucroLiquidoDadoValorHome) {
        List<Compra> compras = this.compraRepository.consultarComprasDisponiveisPorQuantidadeAgrupada();
        this.setData(ativo1, ativo2, ativo3, ativo4, ativo5, idGraficoHome, compras);
        this.desabilitarLayout(ativo1, ativo2, ativo3, ativo4, ativo5, idUmCampo, idDoisCampo,
                idTresCampo, idQuatroCampo, idCincoCampo);

        Carteira carteira = new HomeService(this.compraRepository, new VendaRepository(this.context),
                new CarteiraRepository(this.context)).consultarCarteira();
        if (Objects.nonNull(carteira.getId())) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            idTotalInvestidoValorHome.setText(numberFormat.format(carteira.getTotalInvestido()));
            iLucroLiquidoTotalValorHome.setText(numberFormat.format(carteira.getLucroLiquidoTotalAno()));

            if (carteira.getValorMesLucroLiquido().compareTo(BigDecimal.ZERO) > 0) {
                iMesLucroLiquidoValorHome.setText(carteira.getMesLucroLiquido());
                iMesLucroLiquidoDadoValorHome.setText(numberFormat.format(carteira.getValorMesLucroLiquido()));
            }
        }
    }

    private void definirBoasVindas(TextView idBoasVindas) {
        String usuario = this.configuracaoRepository.consultarConfiguracao().getNomeUsuario();
        if (LocalDateTime.now().getHour() >= 0 && LocalDateTime.now().getHour() <= 11) {
            idBoasVindas.setText(String.format("Bom dia, %s", usuario + "!"));
        } else if (LocalDateTime.now().getHour() > 11 && LocalDateTime.now().getHour() < 18) {
            idBoasVindas.setText(String.format("Boa tarde, %s", usuario + "!"));
        } else if (LocalDateTime.now().getHour() >= 18) {
            idBoasVindas.setText(String.format("Boa noite, %s", usuario + "!"));
        }
    }

    private void desabilitarLayout(TextView ativo1, TextView ativo2, TextView ativo3,
                                   TextView ativo4, TextView ativo5, LinearLayout idUmCampo,
                                   LinearLayout idDoisCampo, LinearLayout idTresCampo,
                                   LinearLayout idQuatroCampo, LinearLayout idCincoCampo) {
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

    private void setData(TextView ativo1, TextView ativo2, TextView ativo3, TextView ativo4,
                         TextView ativo5, PieChart idGraficoHome, List<Compra> compras) {
        for (int i = 0; i < compras.size(); i++) {
            String textoFormatado = String.format(compras.get(i).getAtivo() + " - " + compras.get(i).getQuantidade().toString());
            switch (i) {
                case 0: {
                    ativo1.setText(textoFormatado);
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#FFA726")));
                    break;
                }
                case 1: {
                    ativo2.setText(textoFormatado);
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#66BB6A")));
                    break;
                }
                case 2: {
                    ativo3.setText(textoFormatado);
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#EF5350")));
                    break;
                }
                case 3: {
                    ativo4.setText(textoFormatado);
                    idGraficoHome.addPieSlice(
                            new PieModel(
                                    compras.get(i).getAtivo(),
                                    compras.get(i).getQuantidade(),
                                    Color.parseColor("#29B6F6")));
                    break;
                }
                case 4: {
                    ativo5.setText(textoFormatado);
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
        this.binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }
}