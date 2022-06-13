package br.com.femina.entities;

public enum Tamanho {

    pp("PP"),
        p("P"),
            m("M"),
                g("G"),
                    gg("GG");

    private final String valor;

    private Tamanho(String valor){this.valor = valor;};

}
