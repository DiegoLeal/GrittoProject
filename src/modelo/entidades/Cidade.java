package modelo.entidades;

import java.io.Serializable;

public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome_cidade;
	private UniaoFederativa uf;

	public Cidade() {
	}

	public Cidade(Integer id, String nome, UniaoFederativa uf) {
		super();
		this.id = id;
		this.nome_cidade = nome;
		this.uf = uf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_cidade() {
		return nome_cidade;
	}

	public void setNome_cidade(String nome) {
		this.nome_cidade = nome;
	}

	public UniaoFederativa getUf() {
		return uf;
	}

	public void setUf(UniaoFederativa uf) {
		this.uf = uf;
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cidade [id=" + id + ", nome=" + nome_cidade + ", uf=" + uf.getNome_uf() + "]";
	}

}