package dominio;

public class Livros {
	@Override
	public String toString() {
		return "Titulo: " + Titulo + " - " + " Gênero: " + Genero + " Autor: " + Autor;
	}

	private int id_livro;
	private String Titulo;
	private String Autor;
	private String Genero;
	
	public int getId_livro() {
		return id_livro;
	}
	public void setId_livro(int id_livro) {
		this.id_livro = id_livro;
	}
	public String getTitulo() {
		return Titulo;
	}
	public void setTitulo(String titulo) {
		this.Titulo = titulo;
	}
	public String getAutor() {
		return Autor;
	}
	public void setAutor(String autor) {
		this.Autor = autor;
	}
	public String getGenero() {
		return Genero;
	}
	public void setGenero(String genero) {
		this.Genero = genero;
	}
}
