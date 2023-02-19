package com.llo.lafinance.model.enums;

public enum Status {
    DISPONIVEL("Disponível para venda"),
    VENDIDO("Ação vendido");

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
