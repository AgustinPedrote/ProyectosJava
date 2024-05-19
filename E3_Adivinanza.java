import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class E3_Adivinanza{
    public static void main(String[] args) {

        // Generar número aleatorio entre 1 y 100.
        int numeroMagico = ThreadLocalRandom.current().nextInt(1, 100);
        Scanner sc = new Scanner(System.in);
        int numeroUsuario;

        do {
            System.out.println("Intenta adivinar el número del 1 al 100 que he pensado. Ingresa tu propuesta: ");
            numeroUsuario = sc.nextInt();
            if (numeroMagico > numeroUsuario) {
                System.out.println("El número que he pensado es mayor que el introducido.");
            } else if (numeroMagico < numeroUsuario) {
                System.out.println("El número que he pensado es menor que el introducido.");
            }
        } while (numeroMagico != numeroUsuario);

        System.out.println("Has acertado, ¡FELICIDADES!");

        sc.close();
    }
}