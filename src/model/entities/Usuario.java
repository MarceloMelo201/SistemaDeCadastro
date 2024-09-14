package model.entities;

import model.exceptions.ValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Usuario {
    private String nome;
    private String email;
    private Integer idade;
    private String altura;
    private static final String CAMINHO_FORMULARIO = "SistemaDeCadastro\\src\\model\\formulario.txt";
    private static final String CAMINHO_DADOS = "SistemaDeCadastro\\src\\model\\dados";

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

    public Integer getIdade() {
        return idade;
    }

    public String getAltura() {
        return altura;
    }

    public void validarUsuario(){
        try {
            validarNome(getNome());
            validarEmail(getEmail());
            validarIdade(getIdade());
            validarAltura(getAltura());

        } catch (ValidationException e) {
            System.out.println("Erro: "+e.getMessage());
            System.exit(1);
        }

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
        if(!verificarEmail(email)){
            throw new ValidationException("Acesso negado, Email digitado já cadastrado no sistema.");
        };

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

    public boolean verificarEmail(String email){
        File pasta = new File(CAMINHO_DADOS); //acessando pasta dos usuários
        File[] contagem = pasta.listFiles(); //listando quantos usuários existem
        assert contagem != null;
        List<String> caminhos = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        for (File file : contagem) {
            caminhos.add(file.getAbsolutePath()); // retendo o caminho completo do arquivo
            nomes.add(file.getName()); //Retendo o nome do arquivo
        }
        int i = 0;
        while(i < caminhos.size()){

            try(BufferedReader br = new BufferedReader(new FileReader(caminhos.get(i)))){
                br.readLine();
                String emailCadastrado = br.readLine().substring(31);

                if(emailCadastrado.toLowerCase(Locale.ROOT).equals(email)){
                    return false;
                }
                i++;
            }
            catch (IOException e){
                System.out.println("Erro: "+e.getMessage());
            }
        }
        return true;
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
