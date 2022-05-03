package br.com.femina.entity;

public enum Cargo {

    funcionario("Funcionario"),
    gerente("Gerente"),
    admin("Admin");

    public final String valor;

    private Cargo(String valor) {
        this.valor = valor;
    }



}
