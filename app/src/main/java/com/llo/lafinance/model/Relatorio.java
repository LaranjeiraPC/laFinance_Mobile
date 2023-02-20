package com.llo.lafinance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Relatorio implements Serializable {

    private String ativo;
    private Integer quantidadeCompra;
    private BigDecimal precoUnitarioCompra;
    private BigDecimal precoTotalCompra;
    private LocalDate dataCriacaoCompra;
    private Integer quantidadeVenda;
    private BigDecimal precoUnitarioVenda;
    private BigDecimal precoTotalVenda;
    private LocalDate dataCriacaoVenda;
    private BigDecimal lucroTotal;

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public Integer getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(Integer quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
    }

    public BigDecimal getPrecoUnitarioCompra() {
        return precoUnitarioCompra;
    }

    public void setPrecoUnitarioCompra(BigDecimal precoUnitarioCompra) {
        this.precoUnitarioCompra = precoUnitarioCompra;
    }

    public BigDecimal getPrecoTotalCompra() {
        return precoTotalCompra;
    }

    public void setPrecoTotalCompra(BigDecimal precoTotalCompra) {
        this.precoTotalCompra = precoTotalCompra;
    }

    public LocalDate getDataCriacaoCompra() {
        return dataCriacaoCompra;
    }

    public void setDataCriacaoCompra(LocalDate dataCriacaoCompra) {
        this.dataCriacaoCompra = dataCriacaoCompra;
    }

    public Integer getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(Integer quantidadeVenda) {
        this.quantidadeVenda = quantidadeVenda;
    }

    public BigDecimal getPrecoUnitarioVenda() {
        return precoUnitarioVenda;
    }

    public void setPrecoUnitarioVenda(BigDecimal precoUnitarioVenda) {
        this.precoUnitarioVenda = precoUnitarioVenda;
    }

    public BigDecimal getPrecoTotalVenda() {
        return precoTotalVenda;
    }

    public void setPrecoTotalVenda(BigDecimal precoTotalVenda) {
        this.precoTotalVenda = precoTotalVenda;
    }

    public LocalDate getDataCriacaoVenda() {
        return dataCriacaoVenda;
    }

    public void setDataCriacaoVenda(LocalDate dataCriacaoVenda) {
        this.dataCriacaoVenda = dataCriacaoVenda;
    }

    public BigDecimal getLucroTotal() {
        return lucroTotal;
    }

    public void setLucroTotal(BigDecimal lucroTotal) {
        this.lucroTotal = lucroTotal;
    }
}
