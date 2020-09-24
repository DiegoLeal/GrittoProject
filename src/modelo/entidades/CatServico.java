package modelo.entidades;

import db.Size;

import java.io.Serializable;

import db.Size;

public class CatServico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@Size(max = 10, message = "Numero maximo de caracteres atindidos")
	private String nome;
	
	public CatServico () {
		
	}

	public CatServico(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatServico other = (CatServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CatServico [id=" + id + ", nome=" + nome + "]";
	}	
}
