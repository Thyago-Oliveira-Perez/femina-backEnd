package br.com.femina.entities;

public enum Cargo {

    funcionario("Funcionario"),
    gerente("Gerente"),
    admin("Admin");

    public final String valor;

    private Cargo(String valor) {
        this.valor = valor;
    }

}
