package com.vekrest.vekclient.entity;

public enum Status {
    EM_ANALISE("EM ANALISE"),
    APROVADO("APROVADO");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}