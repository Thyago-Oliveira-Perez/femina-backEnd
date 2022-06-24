package br.com.femina.entities;

public enum Tamanho {

    PP("PP"),
        P("P"),
            M("M"),
                G("G"),
                    GG("GG");

    private final String valor;

    private Tamanho(String valor) { this.valor = valor; };

}
