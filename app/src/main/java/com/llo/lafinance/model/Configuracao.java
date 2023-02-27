package com.llo.lafinance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Configuracao implements Serializable {

    private Integer id;
    private String nomeUsuario;
    private BigDecimal metaLucroLiquidoMensal;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public BigDecimal getMetaLucroLiquidoMensal() {
        return metaLucroLiquidoMensal;
    }

    public void setMetaLucroLiquidoMensal(BigDecimal metaLucroLiquidoMensal) {
        this.metaLucroLiquidoMensal = metaLucroLiquidoMensal;
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
}