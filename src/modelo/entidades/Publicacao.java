package modelo.entidades;

public class Publicacao implements Modal {

    private Long id;

    private String descricao;

    private String foto;

    private Usuario usuario;

    private Object endereco;

    private Object catServico;

    private Integer numero;

    private String complemento;

    public Publicacao(Long id, String descricao, String foto, Usuario usuario, Object endereco, Object catServico, Integer numero, String complemento) {
        this.id = id;
        this.descricao = descricao;
        this.foto = foto;
        this.usuario = usuario;
        this.endereco = endereco;
        this.catServico = catServico;
        this.numero = numero;
        this.complemento = complemento;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Object getEndereco() {
        return endereco;
    }

    public void setEndereco(Object endereco) {
        this.endereco = endereco;
    }

    public Object getCatServico() {
        return catServico;
    }

    public void setCatServico(Object catServico) {
        this.catServico = catServico;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
