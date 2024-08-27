package model.entities;

import model.exceptions.ValidationException;

import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private Integer idade;
    private String altura;
    private static final String CAMINHO_FORMULARIO = "C:\\Users\\55719\\Downloads\\Meus_Projetos\\Java\\SistemaDeCadastro\\src\\model\\formulario.txt";

    public Usuario(){}

    public Usuario(String nome, String email, Integer idade, String altura) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.altura = altura;
        validarUsuario();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdade() {
        return idade;
    }


    public String getAltura() {
        return altura;
    }

    private void validarUsuario(){
        validarNome(getNome());
        validarEmail(getEmail());
        validarIdade(getIdade());
        validarAltura(getAltura());
    }

    public void validarNome(String nome){
        String verificadorNome = nome.replace(" ", "");

        if(verificadorNome.length() < 10){
            throw new ValidationException("O nome deve conter mais de 10 caracteres.");
        }

    }

    public void validarEmail(String email){
        if(!email.contains("@")){
            throw new ValidationException("O email deve conter @, digite um email válido.");
        }

    }

    public void validarIdade(int idade){
        if(idade < 18){
            throw new ValidationException("Acesso negado. Idade mínima permitida: 18 anos");
        }
    }

    public void validarAltura(String altura){
        if(altura.contains(".")){
            throw new ValidationException("Acesso negado. Digite a altura com vírgula ao invés de ponto.");
        }
    }

    public void verificarEmail(String email){
        //abrir a pasta dos usuarios, ler até a 3 linha de cada usuario e verificar se o email já foi usado

    }
    public String formatacaoDados(){
        String nome = getNome().toUpperCase();
        Utilidades util = new Utilidades();
        List<String> perguntas = util.leitor(CAMINHO_FORMULARIO);

        String sb = perguntas.getFirst() + " " + nome +
                "\n" + perguntas.get(1) + " " + getEmail().toUpperCase() +
                "\n" + perguntas.get(2) + " " + getIdade() +
                "\n" + perguntas.get(3) + " " + getAltura();

        return sb;
    }
}
