package model.entities;

import model.exceptions.ValidationException;

import javax.xml.transform.Source;
import java.io.*;
import java.util.*;

public class Utilidades {

    private static final String CAMINHO_FORMULARIO = "C:\\Users\\55719\\Downloads\\Meus_Projetos\\Java\\SistemaDeCadastro\\src\\model\\formulario.txt";
    private static final String CAMINHO_DADOS = "C:\\Users\\55719\\Downloads\\Meus_Projetos\\Java\\SistemaDeCadastro\\src\\model\\dados";
    private Usuario usuario;


    //Cadastro de usuários
    public void cadastro(){
        Scanner scan = new Scanner(System.in);
        List<String> perguntas = leitor(CAMINHO_FORMULARIO);

        //Imprimindo perguntas na tela
        for(String pergunta: perguntas){
            System.out.println(pergunta);
        }
        try {

          //Retendo e validando os dados
          usuario = validacao(scan);
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
        double altura = scan.nextDouble();

        scan.nextLine();

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
        String nomeFormatado = nome.replace(" ", "");
        String nomeDoArquivo = contador + "-" + nomeFormatado.toUpperCase(Locale.ROOT) + ".txt";

        return CAMINHO_DADOS + File.separator + nomeDoArquivo;
    }

    //Verifica se novas perguntas foram adicionadas
    public List<String> perguntasAdicionadas(Scanner scan){
        List<String> listagem = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_FORMULARIO))) {

            for (int i = 0; i < 4; i++) {
                br.readLine();
            }

            while(br.readLine() != null){
                String resposta = scan.nextLine();
                listagem.add(resposta);
            }
        }
        catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return listagem;
    }

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

}
