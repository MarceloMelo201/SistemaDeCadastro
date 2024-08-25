package model.entities;

import model.exceptions.ValidationException;

public class Usuario {
    private String nome;
    private String email;
    private Integer idade;
    private Double altura;

    public Usuario(){}

    public Usuario(String nome, String email, Integer idade, Double altura) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.altura = altura;
        validarUsuario();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
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

    public void validarAltura(double altura){
        String alturaStr = Double.toString(altura);
        if(alturaStr.contains(".")){
            throw new ValidationException("Acesso negado. Digite a altura com vírgula ao invés de ponto.");
        }
    }

    public String formatacaoDados(){
        String nomeUnido = getNome().replace(" ", "").toUpperCase();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nomeUnido.length(); i++) {
            sb.append(nomeUnido.charAt(i));

            if (i < nomeUnido.length() - 1) {
                sb.append(",");
            }
        }
        String nomeFormatado = sb.toString();

        return (nomeFormatado + "\n" + getEmail() + "\n" + getIdade() + "\n" + getAltura());
    }
}
