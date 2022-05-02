package br.com.femina.entity;

public enum Tamanho {

    pp("PP"),
        p("P"),
            m("M"),
                g("G"),
                    gg("GG");

    private final String valor;

    private Tamanho(String valor){this.valor = valor;};

}
