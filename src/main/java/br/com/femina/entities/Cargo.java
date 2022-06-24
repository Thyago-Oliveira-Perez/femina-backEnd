package br.com.femina.entities;

public enum Cargo {

    FUNCIONARIO("FUNCIONARIO"),
    GERENTE("GERENTE"),
    ADMIN("ADMIN");

    public final String valor;

    private Cargo(String valor) {
        this.valor = valor;
    }

}
