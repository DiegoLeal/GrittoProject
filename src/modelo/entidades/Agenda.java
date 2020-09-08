package modelo.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Agenda implements Modal {

    public Agenda(Long idAgenda, LocalDate data, String mensagem, Integer enderecoId, String historico, LocalDateTime hora) {
        this.idAgenda = idAgenda;
        this.data = data;
        this.mensagem = mensagem;
        this.enderecoId = enderecoId;
        this.historico = historico;
        this.hora = hora;
    }

    private Long idAgenda;

    private LocalDate data;

    private String mensagem;

    private Integer enderecoId;

    private String historico;

    private LocalDateTime hora;

    public Long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    @Override
    public Long getId() {
        return getIdAgenda();
    }
}
