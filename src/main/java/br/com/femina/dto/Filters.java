package br.com.femina.dto;

import br.com.femina.enums.Tamanhos;

import java.util.List;

public class Filters {

    private List<Long> categoriaIds;
    private List<Long> marcaIds;
    private String cor;
    private Tamanhos tamanho;

    public Filters() {
    }

    public List<Long> getCategoriaIds() {
        return categoriaIds;
    }

    public void setCategoriaIds(List<Long> categoriaIds) {
        this.categoriaIds = categoriaIds;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<Long> getMarcaIds() {
        return marcaIds;
    }

    public void setMarcaIds(List<Long> marcaIds) {
        this.marcaIds = marcaIds;
    }

    public Tamanhos getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanhos tamanho) {
        this.tamanho = tamanho;
    }
}
