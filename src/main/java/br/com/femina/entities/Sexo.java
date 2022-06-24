package br.com.femina.entities;

public enum Sexo {

    MASCULINO("MASCULINO"),
    FEMININO("FEMININO"),
    OUTRO("OUTRO");

    public final String valor;

    private Sexo(String valor){
        this.valor = valor;
    }

}
