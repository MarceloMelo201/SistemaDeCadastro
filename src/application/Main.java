package application;

import model.entities.Utilidades;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Utilidades util = new Utilidades();

        String seila = sc.nextLine();
        Double valor = Double.parseDouble(seila);
        System.out.println(valor);

        sc.close();
    }
}