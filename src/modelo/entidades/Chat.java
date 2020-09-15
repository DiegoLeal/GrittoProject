package modelo.entidades;

import java.io.Serializable;

public class Chat implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Usuario remetente;
	private Usuario destinatario;
	private String mensagem;
	
	public Chat() {		
	}

	public Chat(Integer id, Usuario remetente, Usuario destinatario, String mensagem) {
		this.id = id;
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.mensagem = mensagem;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getRemetente() {
		return remetente;
	}

	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((remetente == null) ? 0 : remetente.hashCode());
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
		Chat other = (Chat) obj;
		if (remetente == null) {
			if (other.remetente != null)
				return false;
		} else if (!remetente.equals(other.remetente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Chat [remetente=" + remetente + ", destinatario=" + destinatario + ", mensagem=" + mensagem + "]";
	}

}


