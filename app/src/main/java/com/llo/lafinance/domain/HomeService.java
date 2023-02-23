package com.llo.lafinance.domain;

import com.llo.lafinance.model.Carteira;
import com.llo.lafinance.model.Compra;
import com.llo.lafinance.model.Venda;
import com.llo.lafinance.repositorio.CarteiraRepository;
import com.llo.lafinance.repositorio.CompraRepository;
import com.llo.lafinance.repositorio.VendaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class HomeService {

    private final CompraRepository compraRepository;
    private final VendaRepository vendaRepository;
    private final CarteiraRepository carteiraRepository;

    public HomeService(CompraRepository compraRepository, VendaRepository vendaRepository, CarteiraRepository carteiraRepository) {
        this.compraRepository = compraRepository;
        this.vendaRepository = vendaRepository;
        this.carteiraRepository = carteiraRepository;
    }

    public Carteira consultarCarteira() {
        return this.carteiraRepository.consultarCarteira(LocalDate.now().getYear());
    }

    public void atualizarCarteira() {
        ArrayList<Compra> compras = this.compraRepository.consultarComprasAtivas();
        ArrayList<Venda> vendas = this.vendaRepository.consultarVendas();
        Venda venda = this.vendaRepository.consultarTopEmpresaAno(LocalDate.now().getYear());

        Carteira carteiraTemp = this.carteiraRepository.consultarCarteira(LocalDate.now().getYear());

        if (Objects.isNull(carteiraTemp.getId())) {
            Carteira carteira = new Carteira();
            carteira.setAnoVigor(LocalDate.now().getYear());
            carteira.setDataCriacao(LocalDate.now());
            carteira.setTotalInvestido(compras.stream().map(Compra::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            carteira.setLucroLiquidoTotalAno(vendas.isEmpty() ? BigDecimal.ZERO : vendas.stream().map(Venda::getLucroTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            carteira.setMesLucroLiquido(Objects.nonNull(venda) ? this.definirMes(venda.getDataCriacao().getMonth().name()) : "");
            carteira.setValorMesLucroLiquido(Objects.nonNull(venda) ? venda.getLucroTotal() : BigDecimal.ZERO);
            this.carteiraRepository.inserir(carteira);
        } else {
            carteiraTemp.setDataAtualizacao(LocalDate.now());
            carteiraTemp.setTotalInvestido(compras.stream().map(Compra::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            carteiraTemp.setLucroLiquidoTotalAno(vendas.isEmpty() ? BigDecimal.ZERO : vendas.stream().map(Venda::getLucroTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            carteiraTemp.setMesLucroLiquido(Objects.nonNull(venda) ? this.definirMes(venda.getDataCriacao().getMonth().name()) : "");
            carteiraTemp.setValorMesLucroLiquido(Objects.nonNull(venda) ? venda.getLucroTotal() : BigDecimal.ZERO);
            this.carteiraRepository.atualizar(carteiraTemp);
        }
    }

    private String definirMes(String mes) {
        switch (mes.toLowerCase()) {
            case "january": {
                return "Janeiro";
            }
            case "february": {
                return "Fevereiro";
            }
            case "march": {
                return "Março";
            }
            case "april": {
                return "Abril";
            }
            case "may": {
                return "Maio";
            }
            case "june": {
                return "Junho";
            }
            case "july": {
                return "Julho";
            }
            case "august": {
                return "Agosto";
            }
            case "september": {
                return "Setembro";
            }
            case "october": {
                return "Outubro";
            }
            case "november": {
                return "Novembro";
            }
            case "december": {
                return "Dezembro";
            }
            default:
                return "";
        }
    }
}
