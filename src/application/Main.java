package application;

import model.entities.Utilidades;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanInt = new Scanner(System.in);
        Scanner scan = new Scanner(System.in);
        Utilidades utilidades = new Utilidades();
        utilidades.cadastro();

        boolean menu = true;

            while(menu){

                System.out.println();
                System.out.println("=== MENU ===");
                System.out.println(" 1 - Cadastrar o usuário");
                System.out.println(" 2 - Listar todos usuários cadastrados");
                System.out.println(" 3 - Cadastrar nova pergunta no formulário");
                System.out.println(" 4 - Deletar pergunta do formulário");
                System.out.println(" 5 - Pesquisar usuário por nome ou idade ou email");
                System.out.println(" 6 - Sair");

                int escolha = scanInt.nextInt();

                switch (escolha){
                    case 1:
                        utilidades.cadastro();
                        break;

                    case 2:
                        utilidades.listaUsuarios();
                        break;

                    case 3:
                        System.out.println();
                        System.out.println("Digite a pergunta: ");
                        String pergunta = scan.nextLine();
                        utilidades.adicionarPerguntas(pergunta);
                        break;

                    case 4:
                        System.out.println();
                        System.out.println("Digite o número da pergunta a ser deletada (Maior ou igual a 5): ");
                        int numero = scanInt.nextInt();
                        utilidades.removerPerguntas(numero);
                        break;

                    case 5:
                        System.out.println();
                        System.out.println(" 1 - Pesquisa por nome");
                        System.out.println(" 2 - Pesquisa por idade");
                        System.out.println(" 3 - Pesquisa por email");
                        int pesquisa = scanInt.nextInt();


                        if(pesquisa == 1){

                            System.out.println();
                            System.out.println("Digite o nome: ");
                            String nome = scan.nextLine();
                            utilidades.buscaUsuariosNome(nome);
                            break;
                        }
                        else if(pesquisa == 2){
                            System.out.println();
                            System.out.println("Digite a idade: ");
                            int idade = scanInt.nextInt();
                            utilidades.buscaUsuariosIdade(idade);
                            break;
                        }
                        else if (pesquisa == 3){
                            System.out.println();
                            System.out.println("Digite a idade: ");
                            String email = scan.nextLine();
                            utilidades.buscaUsuariosEmail(email);
                            break;
                        }


                    case 6:
                        menu = false;


                    default:
                        System.out.println("Digite uma opção válida.");
                        break;
                }
            }

        scanInt.close();
        scan.close();
    }
}