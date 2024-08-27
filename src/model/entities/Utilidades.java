package model.entities;

import model.exceptions.ValidationException;

import java.io.*;
import java.util.*;

public class Utilidades {

    private static final String CAMINHO_FORMULARIO = "C:\\Users\\55719\\Downloads\\Meus_Projetos\\Java\\SistemaDeCadastro\\src\\model\\formulario.txt";
    private static final String CAMINHO_DADOS = "C:\\Users\\55719\\Downloads\\Meus_Projetos\\Java\\SistemaDeCadastro\\src\\model\\dados";


    //Cadastro de usuários MENU = 1
    public void cadastro(){
        Scanner scan = new Scanner(System.in);
        List<String> perguntas = leitor(CAMINHO_FORMULARIO);

        //Imprimindo perguntas na tela
        for(String pergunta: perguntas){
            System.out.println(pergunta);
        }
        try {

          //Retendo e validando os dados
          Usuario usuario = validacao(scan);
          String nome = usuario.getNome();

          if(nome != null){
              String nomeArquivo = formatador(nome);
              String dadosPrimarios = usuario.formatacaoDados();
              int contador = contadorDeLinhas(CAMINHO_FORMULARIO);
              List<String> listagem = perguntasAdicionadas(scan);
              salvarDados(nomeArquivo, dadosPrimarios, listagem, contador);
          }


        }
        catch (ValidationException | NullPointerException e){
            System.out.println("Erro: "+e.getMessage());
        }
        catch (InputMismatchException e){
            System.out.println("Acesso negado. Digite a altura com vírgula ao invés de ponto.");
        }
        finally {
            scan.close();
        }
    }

    //Validação dos dados
    public Usuario validacao(Scanner scan){

        String nome = scan.nextLine();
        String email = scan.nextLine();
        int idade = scan.nextInt();
        scan.nextLine();
        String altura = scan.nextLine();

        Usuario usuarioValidado = new Usuario();

        try{

            usuarioValidado = new Usuario(nome, email, idade, altura);
        }
        catch (ValidationException e){
            System.out.println("Erro: "+e.getMessage());
        }

        return usuarioValidado;
    }

    //Leitor de perguntas
    public List<String> leitor(String caminho){
        List<String> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            lista = new ArrayList<>();

            while (linha != null) {
                lista.add(linha);
                linha = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erro na impressão: " + e.getMessage());
        }
        return lista;
    }

    //Contador de linhas de arquivos
    public int contadorDeLinhas(String caminho){
        int contador = 0;
        if(Objects.equals(caminho, CAMINHO_DADOS)){
            File pasta = new File(CAMINHO_DADOS);
            File[] contagem = pasta.listFiles();
            assert contagem != null;
            contador += contagem.length + 1;

            return contador;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            while (br.readLine() != null) {
                contador++;
            }
        }
        catch (IOException e) {
            System.out.println("Erro na leitura: " + e.getMessage());
        }
        return contador;
    }

    //Formatação e nomeação do arquivo usuário
    public String formatador(String nome){
        int contador = contadorDeLinhas(CAMINHO_DADOS);
        String nomeFormatado = nome.replace(" ", "").toUpperCase(Locale.ROOT);
        String nomeDoArquivo = contador + "-" + nomeFormatado + ".txt";

        return CAMINHO_DADOS + File.separator + nomeDoArquivo;
    }

    //Verifica se novas perguntas foram adicionadas
    public List<String> perguntasAdicionadas(Scanner scan){
        List<String> listagem = new ArrayList<>();
        List<String> perguntas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_FORMULARIO))) {

            for (int i = 0; i < 4; i++) {
                br.readLine();
            }
            String linha = br.readLine();
            while(linha != null){
                perguntas.add(linha);
                String resposta = scan.nextLine();
                listagem.add(linha + " " +resposta);
                linha = br.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return listagem;
    }

    //Cria um arquivo e salva os dados do usuário
    public void salvarDados(String arquivoCaminho, String dadosPrimarios, List<String> listagem, int contador){
      if(contador > 4){
          try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoCaminho))) {
              bw.write(dadosPrimarios);

              for(String arquivo: listagem){
                  bw.write("\n" + arquivo);
              }
          }
          catch (IOException e) {
              System.out.println("Erro na escrita: " + e.getMessage());
          }

      } else {
          try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoCaminho))) {
              bw.write(dadosPrimarios);
          }
          catch (IOException e) {
              System.out.println("Erro na escrita: " + e.getMessage());
          }
      }
    }

    //Lista todos os usuários MENU = 2
    public void listaUsuarios(){
        File pasta = new File(CAMINHO_DADOS);
        File[] contagem = pasta.listFiles();
        assert contagem != null;
        List<String> caminhos = new ArrayList<>();


        for (File file : contagem) {
            caminhos.add(file.getAbsolutePath()); // retendo o caminho completo do arquivo
        }

        List<String> usuarios = buscadorGeral(caminhos);
        List<String> resultado = new ArrayList<>();

        for (String palavra: usuarios){
            String[] vetor = palavra.split(" ");
            resultado.add(formatarPalavras(vetor));
        }

        int i = 0;
        for(String palavra: resultado){

            System.out.println((i + 1) + " - " + palavra);
            i++;
        }
    }

    //Busca usuários pelo nome
    public List<String> buscadorPorNome(List<String> caminhos, List <String> nome, String buscador){
        List<String> usuarios = new ArrayList<>();

        for(int i = 0; i < caminhos.size(); i++){
            if (nome.get(i).contains(buscador) || nome.get(i).contains(buscador.toUpperCase())){
                try(BufferedReader br = new BufferedReader(new FileReader(caminhos.get(i)))){
                    usuarios.add(br.readLine().substring(28));
                }
                catch (IOException e){
                    System.out.println("Erro: "+e.getMessage());
                }
            }
        }
            return usuarios;
    }

    //Busca geral por usuario
    public List<String> buscadorGeral(List<String> caminhos){
        List<String> usuarios = new ArrayList<>();

        for (String caminho : caminhos) {

            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
                usuarios.add(br.readLine().substring(28));
            }
            catch (IOException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        return usuarios;
    }

    //Formata as palavras e coloca a 1 letra maiúscula
    public String formatarPalavras(String[] palavras){

        StringBuilder resultado = new StringBuilder();

        for(String palavra: palavras ){
            String primeiraLetraMaiuscula = palavra.substring(0, 1).toUpperCase() + palavra.substring(1).toLowerCase();
            resultado.append(primeiraLetraMaiuscula).append(" ");
        }
        return resultado.toString().trim();
    }

    //Busca por usuários por nome = MENU 5
    public void buscaUsuariosNome(String buscador){
        File pasta = new File(CAMINHO_DADOS); //acessando pasta dos usuarios
        File[] contagem = pasta.listFiles(); //listando quantos usuarios existem
        assert contagem != null;
        List<String> caminhos = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        for (File file : contagem) {
            caminhos.add(file.getAbsolutePath()); // retendo o caminho completo do arquivo
            nomes.add(file.getName()); //Retendo o nome do arquivo
        }

        List<String> usuarios = buscadorPorNome(caminhos, nomes, buscador);
        List<String> resultado = new ArrayList<>();

        for (String palavra: usuarios){
            String[] vetor = palavra.split(" ");
            resultado.add(formatarPalavras(vetor));
        }

        int i = 0;
        for(String palavra: resultado){

            System.out.println((i + 1) + " - " + palavra);
            i++;
        }
    }

    //Adiciona perguntas no formulário MENU 3
    public void adicionarPerguntas(String pergunta){
        int numeroLinhas = contadorDeLinhas(CAMINHO_FORMULARIO);
        String perguntaFormatada = (numeroLinhas + 1) + " - " + pergunta;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_FORMULARIO, true))){
            bw.newLine();
            bw.write(perguntaFormatada);

        }
        catch (IOException e){
            System.out.println("Erro: "+e.getMessage());
        }
    }

    //Remove perguntas no formulário MENU 4
    public void removerPerguntas(int numero){
        int contador = contadorDeLinhas(CAMINHO_FORMULARIO);
        if (numero > 4) {
            List<String> perguntas = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_FORMULARIO))) {
                String pergunta;
                for(int i = 1; i < 5; i++){
                    pergunta = br.readLine();
                    perguntas.add(pergunta);
                }
                for(int i = 5; i <= contador; i++){
                    pergunta = br.readLine();

                    if(i < numero){
                        perguntas.add(pergunta);
                    }
                    if(i > numero){
                        String perguntaFormatada = (i - 1) + " - " + pergunta.substring(4);
                        perguntas.add(perguntaFormatada);
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            perguntas.removeIf(String::isEmpty);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_FORMULARIO))) {
                for (String pergunta : perguntas) {

                    if(perguntas.get(0) == pergunta){
                        bw.write(pergunta);
                    } else {
                        bw.write("\n" + pergunta);
                    }
                }
            }
            catch (IOException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("As 4 primeiras perguntas são imutáveis, digite um número válido!");
        }
    }


}
