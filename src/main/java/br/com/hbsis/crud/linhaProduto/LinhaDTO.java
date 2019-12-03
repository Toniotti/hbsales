package br.com.hbsis.crud.linhaProduto;


public class LinhaDTO {
    private int idLinha;
    private String nome;
    private int categoria;


    public LinhaDTO(){

    }

    public LinhaDTO(int id, String nome, int categoria){
        this.idLinha = id;
        this.nome = nome;
        this.categoria = categoria;
    }

    public static LinhaDTO of(Linha linha){
        return new LinhaDTO(
                linha.getIdLinha(),
                linha.getNomeLinha(),
                linha.getCategoria().getIdCategoria());
    }

    public int getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(int idLinha) {
        this.idLinha = idLinha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
