import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Scanner;

class Libro {

    private String codigo, titulo, autor, localizacion, signatura;
    private boolean disponible;

    public Libro(String codigo, String titulo, String autor, String localizacion, String signatura,
            boolean disponible) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.localizacion = localizacion;
        this.signatura = signatura;
        this.disponible = disponible;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSignatura() {
        return signatura;
    }

    public void setSignatura(String signatura) {
        this.signatura = signatura;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Libro{" + "codigo=" + codigo + ", titulo=" + titulo + ", autor=" + autor + ", localizacion="
                + localizacion + ", signatura=" + signatura + ", disponible=" + disponible + '}';
    }

}

class Socio {

    private String numero, nombre, direccion;

    public Socio(String numero, String nombre, String direccion) {
        this.numero = numero;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Socio{" + "numero=" + numero + ", nombre=" + nombre + ", direccion=" + direccion + '}';
    }

}

class Prestamo {

    private String codigoLibro, numeroSocio;
    private LocalDateTime fecha;

    public Prestamo(String codigoLibro, String numeroSocio, LocalDateTime fecha) {
        this.codigoLibro = codigoLibro;
        this.numeroSocio = numeroSocio;
        this.fecha = fecha;
    }

    public String getCodigoLibro() {
        return codigoLibro;
    }

    public void setCodigoLibro(String codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public String getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(String numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getFechaFormateada() {
        String formato = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        return formateador.format(this.fecha);
    }

    @Override
    public String toString() {
        return "Prestamo{" + "codigoLibro=" + codigoLibro + ", numeroSocio=" + numeroSocio + ", fecha="
                + this.getFechaFormateada() + '}';
    }

}

class ControladorSocios {

    private static final String NOMBRE_ARCHIVO = "socios.txt";
    private static final String SEPARADOR_CAMPO = ";";
    private static final String SEPARADOR_REGISTRO = "\n";

    public static void solicitarDatosParaRegistrar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese numero de socio: ");
        String numero = sc.nextLine();
        System.out.println("Ingrese nombre de socio: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese direccion de socio: ");
        String direccion = sc.nextLine();
        ControladorSocios.registrar(new Socio(numero, nombre, direccion));
        System.out.println("Registrado exitosamente");
        sc.close();
    }

    public static void registrar(Socio socio) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true));
            bufferedWriter.write(socio.getNumero() + SEPARADOR_CAMPO + socio.getNombre() + SEPARADOR_CAMPO
                    + socio.getDireccion() + SEPARADOR_REGISTRO);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error escribiendo en archivo: " + e.getMessage());
        }
    }

    @SuppressWarnings("finally")
    public static ArrayList<Socio> obtener() {
        ArrayList<Socio> socios = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(NOMBRE_ARCHIVO);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] socioComoArreglo = linea.split(SEPARADOR_CAMPO);
                socios.add(new Socio(socioComoArreglo[0], socioComoArreglo[1], socioComoArreglo[2]));
            }
        } catch (IOException e) {
            System.out.println("Excepción leyendo archivo: " + e.getMessage());
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("Excepción cerrando: " + e.getMessage());
            }
            return socios;
        }
    }

    public static void imprimirSocios(ArrayList<Socio> socios) {
        ArrayList<Prestamo> prestamos = ControladorPrestamos.obtener();
        System.out.println(
                "+-----+----------+----------------------------------------+----------------------------------------+--------------------+");
        System.out.printf("|%-5s|%-10s|%-40s|%-40s|%-20s|\n", "#", "No. socio", "Nombre", "Direccion",
                "Libros prestados");
        System.out.println(
                "+-----+----------+----------------------------------------+----------------------------------------+--------------------+");
        for (int x = 0; x < socios.size(); x++) {
            Socio socio = socios.get(x);
            System.out.printf("|%-5s|%-10s|%-40s|%-40s|%-20s|\n", x + 1, socio.getNumero(), socio.getNombre(),
                    socio.getDireccion(), ControladorPrestamos.cantidadLibrosPrestados(socio.getNumero(), prestamos));
            System.out.println(
                    "+-----+----------+----------------------------------------+----------------------------------------+--------------------+");
        }
    }

    public static void imprimirSociosNoFiables(ArrayList<Socio> socios) {
        ArrayList<Prestamo> prestamos = ControladorPrestamos.obtener();
        System.out.println(
                "+-----+----------+----------------------------------------+----------------------------------------+--------------------+");
        System.out.printf("|%-5s|%-10s|%-40s|%-40s|%-20s|\n", "#", "No. socio", "Nombre", "Direccion",
                "Libros prestados");
        System.out.println(
                "+-----+----------+----------------------------------------+----------------------------------------+--------------------+");
        for (int x = 0; x < socios.size(); x++) {
            Socio socio = socios.get(x);
            int librosPrestados = ControladorPrestamos.cantidadLibrosPrestados(socio.getNumero(), prestamos);
            if (librosPrestados < 10) {
                continue;
            }
            System.out.printf("|%-5s|%-10s|%-40s|%-40s|%-20s|\n", x + 1, socio.getNumero(), socio.getNombre(),
                    socio.getDireccion(), librosPrestados);
            System.out.println(
                    "+-----+----------+----------------------------------------+----------------------------------------+--------------------+");
        }
    }

    public static int buscarSocioPorNumero(String numero, ArrayList<Socio> socios) {
        for (int x = 0; x < socios.size(); x++) {
            Socio socio = socios.get(x);
            if (socio.getNumero().equals(numero)) {
                return x;
            }
        }
        return -1;
    }

    public static Socio imprimirSociosYPedirSeleccion() {
        ArrayList<Socio> socios = ControladorSocios.obtener();
        Scanner sc = new Scanner(System.in);
        while (true) {
            ControladorSocios.imprimirSocios(socios);
            System.out.println("Ingrese el numero de socio: ");
            String numero = sc.nextLine();
            int indice = ControladorSocios.buscarSocioPorNumero(numero, socios);
            if (indice == -1) {
                System.out.println("No existe socio con ese numero");
            } else {
                return socios.get(indice);
            }
            sc.close();
        }
    }
}

class ControladorLibros {

    private static final String NOMBRE_ARCHIVO = "libros.txt";
    private static final String SEPARADOR_CAMPO = ";";
    private static final String SEPARADOR_REGISTRO = "\n";

    public static void solicitarDatosParaRegistrar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el codigo del libro:");
        String codigo = sc.nextLine();
        System.out.println("Ingrese el titulo del libro:");
        String titulo = sc.nextLine();
        System.out.println("Ingrese el autor del libro:");
        String autor = sc.nextLine();
        System.out.println("El libro esta disponible? [true/false]");
        boolean disponible = Boolean.valueOf(sc.nextLine());
        System.out.println("Ingrese la localizacion del libro:");
        String localizacion = sc.nextLine();
        System.out.println("Ingrese la signatura del libro:");
        String signatura = sc.nextLine();
        ControladorLibros.registrar(new Libro(codigo, titulo, autor, localizacion, signatura, disponible));
        System.out.println("Libro registrado correctamente");
        sc.close();
    }

    public static void registrar(Libro libro) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true));
            bufferedWriter.write(libro.getCodigo()
                    + SEPARADOR_CAMPO + libro.getTitulo()
                    + SEPARADOR_CAMPO + libro.getAutor()
                    + SEPARADOR_CAMPO + String.valueOf(libro.isDisponible())
                    + SEPARADOR_CAMPO + libro.getLocalizacion()
                    + SEPARADOR_CAMPO + libro.getSignatura() + SEPARADOR_REGISTRO);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error escribiendo en archivo: " + e.getMessage());
        }
    }

    public static void guardarLibros(ArrayList<Libro> libros) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, false));
            for (int x = 0; x < libros.size(); x++) {
                Libro libro = libros.get(x);
                bufferedWriter.write(libro.getCodigo()
                        + SEPARADOR_CAMPO + libro.getTitulo()
                        + SEPARADOR_CAMPO + libro.getAutor()
                        + SEPARADOR_CAMPO + String.valueOf(libro.isDisponible())
                        + SEPARADOR_CAMPO + libro.getLocalizacion()
                        + SEPARADOR_CAMPO + libro.getSignatura() + SEPARADOR_REGISTRO);

            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error escribiendo en archivo: " + e.getMessage());
        }
    }

    @SuppressWarnings("finally")
    public static ArrayList<Libro> obtener() {
        ArrayList<Libro> libros = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(NOMBRE_ARCHIVO);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] libroComoArreglo = linea.split(SEPARADOR_CAMPO);
                libros.add(new Libro(libroComoArreglo[0], libroComoArreglo[1], libroComoArreglo[2], libroComoArreglo[4],
                        libroComoArreglo[5], Boolean.valueOf(libroComoArreglo[3])));
            }
        } catch (IOException e) {
            System.out.println("Excepción leyendo archivo: " + e.getMessage());
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("Excepción cerrando: " + e.getMessage());
            }
            return libros;
        }
    }

    public static int buscarPorCodigo(String codigo, ArrayList<Libro> libros) {
        for (int x = 0; x < libros.size(); x++) {
            Libro libroActual = libros.get(x);
            if (libroActual.getCodigo().equals(codigo)) {
                return x;
            }
        }
        return -1;
    }

    public static void marcarComoPrestado(String codigoLibro) {
        ArrayList<Libro> libros = ControladorLibros.obtener();
        int indice = ControladorLibros.buscarPorCodigo(codigoLibro, libros);
        if (indice == -1) {
            return;
        }
        libros.get(indice).setDisponible(false);
        ControladorLibros.guardarLibros(libros);
    }

    public static void cambiarSignatura(String codigoLibro, String nuevaSignatura, String nuevaLocalizacion) {
        ArrayList<Libro> libros = ControladorLibros.obtener();
        int indice = ControladorLibros.buscarPorCodigo(codigoLibro, libros);
        if (indice == -1) {
            return;
        }
        libros.get(indice).setSignatura(nuevaSignatura);
        libros.get(indice).setLocalizacion(nuevaLocalizacion);
        ControladorLibros.guardarLibros(libros);
    }

    public static void solicitarDatosParaCambiarSignatura() {
        Scanner sc = new Scanner(System.in);
        Libro libro = ControladorLibros.imprimirLibrosYPedirSeleccion();
        if (!libro.isDisponible()) {
            System.out.println("No puede cambiar un libro que no esta disponible");
            sc.close();
            return;
        }
        System.out.println("Ingrese nueva localizacion: ");
        String nuevaLocalizacion = sc.nextLine();
        System.out.println("Ingrese nueva signatura:");
        String nuevaSignatura = sc.nextLine();
        ControladorLibros.cambiarSignatura(libro.getCodigo(), nuevaSignatura, nuevaLocalizacion);
        System.out.println("Localizacion cambiada correctamente");
        sc.close();
    }

    public static void imprimirLibros(ArrayList<Libro> libros) {
        System.out.println(
                "+-----+----------+----------------------------------------+--------------------+----------+------------------------------+------------------------------+");
        System.out.printf("|%-5s|%-10s|%-40s|%-20s|%-10s|%-30s|%-30s|\n", "No", "Codigo", "Titulo", "Autor",
                "Disponible",
                "Localizacion", "Signatura");
        System.out.println(
                "+-----+----------+----------------------------------------+--------------------+----------+------------------------------+------------------------------+");

        for (int x = 0; x < libros.size(); x++) {
            Libro libro = libros.get(x);
            System.out.printf("|%-5d|%-10s|%-40s|%-20s|%-10s|%-30s|%-30s|\n", x + 1, libro.getCodigo(),
                    libro.getTitulo(),
                    libro.getAutor(), libro.isDisponible() ? "Si" : "No", libro.getLocalizacion(),
                    libro.getSignatura());
            System.out.println(
                    "+-----+----------+----------------------------------------+--------------------+----------+------------------------------+------------------------------+");
        }
    }

    public static Libro imprimirLibrosYPedirSeleccion() {
        ArrayList<Libro> libros = ControladorLibros.obtener();
        Scanner sc = new Scanner(System.in);
        while (true) {
            ControladorLibros.imprimirLibros(libros);
            System.out.println("Ingrese el codigo del libro: ");
            String codigo = sc.nextLine();
            int indice = ControladorLibros.buscarPorCodigo(codigo, libros);
            if (indice == -1) {
                System.out.println("No existe libro con ese codigo");
            } else {
                Libro libro = libros.get(indice);
                if (libro.isDisponible()) {
                    return libro;
                } else {
                    System.out.println("El libro esta ocupado");
                }
            }
            sc.close();
        }
    }
}

class ControladorPrestamos {

    private static final String NOMBRE_ARCHIVO = "prestamos.txt";
    private static final String SEPARADOR_CAMPO = ";";
    private static final String SEPARADOR_REGISTRO = "\n";

    public static void registrar(Prestamo prestamo) {
        ControladorLibros.marcarComoPrestado(prestamo.getCodigoLibro());
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true));
            bufferedWriter.write(prestamo.getCodigoLibro() + SEPARADOR_CAMPO + prestamo.getNumeroSocio()
                    + SEPARADOR_CAMPO + prestamo.getFechaFormateada() + SEPARADOR_REGISTRO);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error escribiendo en archivo: " + e.getMessage());
        }
    }

    public static ArrayList<Prestamo> obtener() {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(NOMBRE_ARCHIVO);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] prestamoComoArreglo = linea.split(SEPARADOR_CAMPO);
                prestamos.add(new Prestamo(prestamoComoArreglo[0], prestamoComoArreglo[1],
                        LocalDateTime.parse(prestamoComoArreglo[2],
                                new DateTimeFormatterBuilder().parseCaseInsensitive()
                                        .append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toFormatter())));
            }
        } catch (IOException e) {
            System.out.println("Excepción leyendo archivo: " + e.getMessage());
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("Excepción cerrando: " + e.getMessage());
            }
        }
        return prestamos;
    }

    public static int cantidadLibrosPrestados(String numeroSocio, ArrayList<Prestamo> prestamos) {
        int cantidad = 0;
        for (int x = 0; x < prestamos.size(); x++) {
            Prestamo prestamo = prestamos.get(x);
            if (prestamo.getNumeroSocio().equals(numeroSocio)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public static void imprimirPrestamos(ArrayList<Prestamo> prestamos) {
        System.out
                .println("+-----+------------------------------+------------------------------+--------------------+");
        System.out.printf("|%-5s|%-30s|%-30s|%-20s|\n", "No", "Codigo libro", "No. Socio", "Fecha");
        System.out
                .println("+-----+------------------------------+------------------------------+--------------------+");

        for (int x = 0; x < prestamos.size(); x++) {
            Prestamo prestamo = prestamos.get(x);
            System.out.printf("|%-5d|%-30s|%-30s|%-20s|\n", x, prestamo.getCodigoLibro(), prestamo.getNumeroSocio(),
                    prestamo.getFechaFormateada());
            System.out.println(
                    "+-----+------------------------------+------------------------------+--------------------+");
        }
    }

    public static void solicitarDatosYCrearPrestamo() {
        Libro libro = ControladorLibros.imprimirLibrosYPedirSeleccion();
        Socio socio = ControladorSocios.imprimirSociosYPedirSeleccion();
        ControladorPrestamos.registrar(new Prestamo(libro.getCodigo(), socio.getNumero(), LocalDateTime.now()));
        System.out.println("Prestamo registrado correctamente");
    }

    public class Biblioteca {

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            String eleccion = "";
            while (!eleccion.equals("9")) {
                System.out.println(
                        "1. Registrar socio\n2. Registrar libro\n3. Registrar prestamo\n4. Ver socios\n5. Ver libros\n6. Ver prestamos\n7. Ver socios no fiables\n8. Cambiar localizacion de libro\n9. Salir\nElige:");
                eleccion = sc.nextLine();
                if (eleccion.equals("1")) {
                    ControladorSocios.solicitarDatosParaRegistrar();
                }
                if (eleccion.equals("2")) {
                    ControladorLibros.solicitarDatosParaRegistrar();
                }
                if (eleccion.equals("3")) {
                    ControladorPrestamos.solicitarDatosYCrearPrestamo();
                }
                if (eleccion.equals("4")) {
                    ControladorSocios.imprimirSocios(ControladorSocios.obtener());
                }
                if (eleccion.equals("5")) {
                    ControladorLibros.imprimirLibros(ControladorLibros.obtener());
                }
                if (eleccion.equals("6")) {
                    ControladorPrestamos.imprimirPrestamos(ControladorPrestamos.obtener());
                }
                if (eleccion.equals("7")) {
                    ControladorSocios.imprimirSociosNoFiables(ControladorSocios.obtener());
                }
                if (eleccion.equals("8")) {
                    ControladorLibros.solicitarDatosParaCambiarSignatura();
                }
            }
            // Cerrar el objeto Scanner después de salir del bucle while
            sc.close();
        }
    }
}
