import java.util.Scanner;

public class App {

    private static int contadorMovimientos = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TORRES DE HANOI V.1 ===");
        System.out.print("Ingrese el numero de discos: ");

        int discos = scanner.nextInt();

        if (discos <= 0) {
            System.out.println("El numero de discos debe ser mayor que 0.");
            scanner.close();
            return;
        }

        resolverTorresHanoi(discos, 'A', 'C', 'B');
        System.out.println("Total de movimientos: " + contadorMovimientos);

        scanner.close();
    }

    private static void resolverTorresHanoi(int n, char origen, char destino, char auxiliar) {
        if (n == 1) {
            contadorMovimientos++;
            System.out.println("Movimiento " + contadorMovimientos + ": disco 1 de " + origen + " a " + destino);
            return;
        }

        resolverTorresHanoi(n - 1, origen, auxiliar, destino);

        contadorMovimientos++;
        System.out.println("Movimiento " + contadorMovimientos + ": disco " + n + " de " + origen + " a " + destino);

        resolverTorresHanoi(n - 1, auxiliar, destino, origen);
    }
}
