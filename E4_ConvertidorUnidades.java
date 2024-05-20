import java.util.Scanner;

public class E4_ConvertidorUnidades {
    public static void main(String[] args) {

        Scanner leer = new Scanner(System.in);

        int KM;
        int CM;
        int Y;
        int op;
        double RES;

        System.out.println("Ingrese la operacion");
        System.out.println("1- Centimetros a pulgadas");
        System.out.println("2- Yardas a metros");
        System.out.println("3- Kilimetros a millas");
        System.out.println("\n");
        op = leer.nextInt();

        switch (op) {
            case 1:
                System.out.println("Centimetros a pulgadas");
                System.out.print("Ingrese los centimetros: ");
                CM = leer.nextInt();
                RES = CM * 2.3;
                System.out.println("El resultado es: " + RES + " pulgadas");
                break;
            case 2:
                System.out.println("Yardas a metros");
                System.out.print("Ingrese las yardas: ");
                Y = leer.nextInt();
                RES = Y * 0.914401829;
                System.out.println("El resultado es: " + RES + " metros");
                break;
            case 3:
                System.out.println("Kilometros a millas");
                System.out.print("Ingrese los kilometros: ");
                KM = leer.nextInt();
                RES = KM * 0.621;
                System.out.println("El resultado es: " + RES + " millas");
                break;

            default:
                break;
        }
        leer.close();
    }
}
