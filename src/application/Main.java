package application;

import model.entities.Utilidades;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Utilidades util = new Utilidades();

        util.cadastro();

        boolean menu = true;

        while(menu){

            System.out.println();
            System.out.println("=== MENU ===");
            System.out.println(" 1 - Cadastrar o usuário");
            System.out.println(" 2 - Listar todos usuários cadastrados");
            System.out.println(" 3 - Cadastrar nova pergunta no formulário");
            System.out.println(" 4 - Deletar pergunta do formulário");
            System.out.println(" 5 - Pesquisar usuário por nome ou idade ou email");

            int escolha = sc.nextInt();

            switch (escolha){
                case 1:
                    util.cadastro();
                    break;

                case 2:
                    util.listaUsuarios();
                    break;

                case 3:

            }

        }

    }
}