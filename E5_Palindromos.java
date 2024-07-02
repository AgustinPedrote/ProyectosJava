public class E5_Palindromos {
    public static void main(String[] args) {
        String[] cadenas = { "La ruta natural", "Esto no es", "Parzibyte", "Hola", "Sol", "Ana", "Oro", "Oso",
                "A ti no, bonita", "Me gusta programar en Java" };
        for (String cadena : cadenas) {
            System.out.println("¿'" + cadena + "' es palíndromo? " + esPalindromo(cadena));
        }
    }

    // Comprobar si es palíndromo
    public static boolean esPalindromo(String cadena) {
        cadena = cadena.toLowerCase().replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o")
                .replace("ú", "u").replace(" ", "").replace(".", "").replace(",", "");

        // Invertir la cadena
        String invertida = new StringBuilder(cadena).reverse().toString();

        // Si es igual que la original entonces son palíndromos
        return invertida.equals(cadena);
    }

}
