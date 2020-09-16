package modelo.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agenda implements Modal {

    public Agenda(Long id, LocalDate data, String mensagem, String historico, LocalTime hora) {
        this.id = id;
        this.data = data;
        this.descricao = mensagem;
        this.historico = historico;
        this.hora = hora;
    }

    private Long id;

    private LocalDate data;

    private String descricao;

    private String historico;

    private LocalTime hora;

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
