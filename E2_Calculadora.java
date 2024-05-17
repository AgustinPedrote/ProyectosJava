import java.util.Scanner;

public class E2_Calculadora {
    public static void main(String[] args) {

        char operator;
        Double number1, number2, result;

        Scanner input = new Scanner(System.in);

        System.out.print("Elige un operador (+, -, *, /): ");
        operator = input.next().charAt(0);

        System.out.print("Introduce el primer número: ");
        number1 = input.nextDouble();

        System.out.print("Introduce el segundo número: ");
        number2 = input.nextDouble();

        switch (operator) {
            case '+':
                result = number1 + number2;
                System.out.println(number1 + " + " + number2 + " = " + result);
                break;
            case '-':
                result = number1 - number2;
                System.out.println(number1 + " - " + number2 + " = " + result);
                break;
            case '*':
                result = number1 * number2;
                System.out.println(number1 + " * " + number2 + " = " + result);
                break;
            case '/':
                result = number1 / number2;
                System.out.println(number1 + " / " + number2 + " = " + result);
                break;
            default:
                System.out.println("Operador invalido.");
                break;
        }
        input.close();
    }
}
