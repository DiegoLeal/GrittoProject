package modelo.entidades;

import java.io.Serializable;
import java.util.Date;


public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String rg;
	private String cpf;
	private Date dataNascimento;
	private String telefone;
	private String senha;
	private String email;
	private String sexo;
	private CatServico catServico;

	public Usuario() {
	}

	public Usuario(Integer id, String nome, String rg, String cpf, Date dataNascimento, String telefone, String senha,
			String email, String sexo, CatServico catServico) {
		this.id = id;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.sexo = sexo;
		this.senha = senha;
		this.email = email;
		this.catServico = catServico;
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public CatServico getCatServico() {
		return catServico;
	}

	public void setCatServico(CatServico catServico) {
		this.catServico = catServico;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", rg=" + rg + ", cpf=" + cpf + ", dataNascimento="
				+ dataNascimento + ", telefone=" + telefone + ", senha=" + senha + ", email=" + email + ", sexo=" + sexo
				+ ", profissao=" + catServico + "]";
	}
}
