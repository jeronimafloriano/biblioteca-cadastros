package biblioteca.cadastros;


import biblioteca.cadastros.domain.model.Cliente;
import biblioteca.cadastros.domain.model.Endereco;
import biblioteca.cadastros.domain.model.Funcionario;

public final class TestFactory {

    private String nome = "Maria";
    private String documento = "89233687090";
    private Endereco endereco;


    public static Endereco umEnderecoDigitado(){
        return new Endereco("74370320", "Rua Vinte Um de Junho",
                "Setor Tancredo Neves", "Goiânia", "GO");

    }

    public static Cliente umClienteDigitado(){
        Endereco endereco = umEnderecoDigitado();
        return  new Cliente("Maria", "89233687090", endereco);
    }

    public static Funcionario umFuncionarioDigitado(){
        Endereco endereco = umEnderecoDigitado();
        return new Funcionario("Maria", "89233687090", endereco);
    }

}
