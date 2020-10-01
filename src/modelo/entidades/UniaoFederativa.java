package modelo.entidades;

import java.io.Serializable;

public class UniaoFederativa implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome_uf;

	public UniaoFederativa() {
	}

	public UniaoFederativa(Integer id, String nome_uf) {
		super();
		this.id = id;
		this.nome_uf = nome_uf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_uf() {
		return nome_uf;
	}

	public void setNome_uf(String nome_uf) {
		this.nome_uf = nome_uf;
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
		UniaoFederativa other = (UniaoFederativa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Uniao_federativa [id=" + id + ", nome_uf=" + nome_uf + "]";
	}

}
