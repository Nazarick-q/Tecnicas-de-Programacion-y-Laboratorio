import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Temperatura> datos = cargarCSV("temperaturas.csv");
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Ingrese fecha inicial (dd/mm/yyyy): ");
        LocalDate desde = LocalDate.parse(scanner.nextLine(), formatter);
        System.out.print("Ingrese fecha final (dd/mm/yyyy): ");
        LocalDate hasta = LocalDate.parse(scanner.nextLine(), formatter);

        Map<String, Double> promedios = datos.stream()
            .filter(t -> !t.getFecha().isBefore(desde) && !t.getFecha().isAfter(hasta))
            .collect(Collectors.groupingBy(
                Temperatura::getCiudad,
                Collectors.averagingDouble(Temperatura::getTemperatura)
            ));

        System.out.println("\nPromedios de temperátura por ciudad:");
        for (Map.Entry<String, Double> entry : promedios.entrySet()) {
            System.out.printf("%s: %.2f°C\n", entry.getKey(), entry.getValue());
        }

        System.out.print("\nIngrese una fecha especifica (dd/mm/yyyy): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine(), formatter);

        List<Temperatura> enFecha = datos.stream()
            .filter(t -> t.getFecha().equals(fecha))
            .collect(Collectors.toList());

        if (enFecha.isEmpty()) {
            System.out.println("No hay datos para esa fecha.");
        } else {
            Temperatura max = enFecha.stream().max(Comparator.comparing(Temperatura::getTemperatura)).get();
            Temperatura min = enFecha.stream().min(Comparator.comparing(Temperatura::getTemperatura)).get();

            System.out.println("Ciudad más calurosa: " + max.getCiudad() + " (" + max.getTemperatura() + "°C)");
            System.out.println("Ciudad menos calurosa: " + min.getCiudad() + " (" + min.getTemperatura() + "°C)");
        }
    }

    public static List<Temperatura> cargarCSV(String nombreArchivo) {
        List<Temperatura> lista = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            List<String> lineas = Files.readAllLines(Paths.get(nombreArchivo));
            for (int i = 1; i < lineas.size(); i++) {
                String[] partes = lineas.get(i).split(",");
                String ciudad = partes[0];
                LocalDate fecha = LocalDate.parse(partes[1], formatter);
                double temp = Double.parseDouble(partes[2]);
                lista.add(new Temperatura(ciudad, fecha, temp));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo. Verifique que exista");
        }

        return lista;
    }
}