package com.vekrest.vekclient.entity;

public enum Status {
    EM_ANALISE("Em Análise"),
    APROVADO("Aprovado");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}