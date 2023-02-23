package com.llo.lafinance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Carteira implements Serializable {

    private Integer id;
    private BigDecimal totalInvestido;
    private BigDecimal lucroLiquidoTotalAno;
    private String mesLucroLiquido;
    private Integer anoVigor;
    private BigDecimal valorMesLucroLiquido;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotalInvestido() {
        return totalInvestido;
    }

    public void setTotalInvestido(BigDecimal totalInvestido) {
        this.totalInvestido = totalInvestido;
    }

    public BigDecimal getLucroLiquidoTotalAno() {
        return lucroLiquidoTotalAno;
    }

    public void setLucroLiquidoTotalAno(BigDecimal lucroLiquidoTotalAno) {
        this.lucroLiquidoTotalAno = lucroLiquidoTotalAno;
    }

    public String getMesLucroLiquido() {
        return mesLucroLiquido;
    }

    public void setMesLucroLiquido(String mesLucroLiquido) {
        this.mesLucroLiquido = mesLucroLiquido;
    }

    public BigDecimal getValorMesLucroLiquido() {
        return valorMesLucroLiquido;
    }

    public void setValorMesLucroLiquido(BigDecimal valorMesLucroLiquido) {
        this.valorMesLucroLiquido = valorMesLucroLiquido;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getAnoVigor() {
        return anoVigor;
    }

    public void setAnoVigor(Integer anoVigor) {
        this.anoVigor = anoVigor;
    }
}
