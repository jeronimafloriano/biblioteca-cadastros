package biblioteca.cadastros.domain.model;

public class ClientesPorCep {
    private String cep;
    private Long total;

    public ClientesPorCep(String cep, Long total) {
        this.cep = cep;
        this.total = total;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
