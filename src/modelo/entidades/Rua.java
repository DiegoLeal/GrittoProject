package modelo.entidades;

public class Rua {

	private Integer id;
	private String nome_rua;

	public Rua() {
	}

	public Rua(Integer id, String nome_rua) {
		super();
		this.id = id;
		this.nome_rua = nome_rua;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_rua() {
		return nome_rua;
	}

	public void setNome_rua(String nome_rua) {
		this.nome_rua = nome_rua;
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
		Rua other = (Rua) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rua [id=" + id + ", nome_rua=" + nome_rua + "]";
	}

}
