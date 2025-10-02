package ui;

import java.util.HashSet;
import java.util.Scanner;
import structures.TablasHash;
import structures.PilaGenerica;

public class Main {

    private final Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1 -> {
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                }

                case 2 -> {
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                }

                case 3 -> {
                    try (sc) {
                        System.out.println("Chao");
                    }
                    System.exit(0);
                }


                default -> System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
        PilaGenerica<Character> expresiones = new PilaGenerica<>(s.length());
        for (char c : s.toCharArray()) {
            if(c == '(' || c == '[' || c == '{'){
                expresiones.Push(c);
            }
            else if(c == ')' || c == ']' || c == '}'){
                if(expresiones.getTop() != -1){
                    return false;
                }
            }
            char top = expresiones.Pop();
            if((c == ')' && top != '(') ||
                (c == ']' && top != '[') ||
                (c == '}' && top != '{')){
                return false;
                }
        }
        return false;
    }

    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public void encontrarParesConSuma(int[] numeros, int objetivo) {
        try {
        TablasHash tabla = new TablasHash(numeros.length * 2);
        HashSet<String> paresUnicos = new HashSet<>();

        for (int num : numeros) {
            int complemento = objetivo - num;

         
            if (tabla.search(complemento, complemento)) {
                int menor = Math.min(num, complemento);
                int mayor = Math.max(num, complemento);
                String par = "(" + menor + ", " + mayor + ")";

                if (!paresUnicos.contains(par)) {
                    paresUnicos.add(par);
                    System.out.println("Par encontrado: " + par);
                }
            }
        
            tabla.insert(num, num);
        }

        if (paresUnicos.isEmpty()) {
            System.out.println("Ningún par encontrado para suma " + objetivo);
        }

        } catch (Exception e) {
            
        }
    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
