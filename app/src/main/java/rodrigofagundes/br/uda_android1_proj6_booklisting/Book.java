package rodrigofagundes.br.uda_android1_proj6_booklisting;

public class Book {
    private String titulo;
    private String[] autores;

    public Book(String titulo, String[] autores) {
        this.titulo = titulo;
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public String[] getAutores() {
        return autores;
    }
}