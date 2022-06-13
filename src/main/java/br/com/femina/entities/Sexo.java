package br.com.femina.entities;

public enum Sexo {

    masculino("MASCULINO"),
    feminino("FEMININO"),
    outro("OUTRO");

    public final String valor;

    private Sexo(String valor){
        this.valor = valor;
    }

}
