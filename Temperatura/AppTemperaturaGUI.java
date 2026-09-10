import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AppTemperaturaGUI extends JFrame {
    private JPanel panelPrincipal;
    private JTextField campoFechaInicial;
    private JTextField campoFechaFinal;
    private JTextField campoFechaEspecifica;
    private JTextArea areaResultados;
    private JButton botonCalcularPromedio;
    private JButton botonBuscarExtremosEnFecha;
    private List<Temperatura> datosCargados;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AppTemperaturaGUI() {
        super("Análisis de Temperaturas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        inicializarComponentes();
        
        datosCargados = cargarCSV("temperaturas.csv");
        
        setVisible(true);
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelEntradas = new JPanel();
        panelEntradas.setLayout(new GridLayout(6, 2, 5, 10));
        
        panelEntradas.add(new JLabel("Fecha inicial (dd/MM/yyyy):"));
        campoFechaInicial = new JTextField(10);
        panelEntradas.add(campoFechaInicial);
        
        panelEntradas.add(new JLabel("Fecha final (dd/MM/yyyy):"));
        campoFechaFinal = new JTextField(10);
        panelEntradas.add(campoFechaFinal);
        
        botonCalcularPromedio = new JButton("Calcular Promedios");
        botonCalcularPromedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularPromedios();
            }
        });
        panelEntradas.add(botonCalcularPromedio);
        panelEntradas.add(new JLabel("")); 
        
        panelEntradas.add(new JLabel("Fecha específica (dd/MM/yyyy):"));
        campoFechaEspecifica = new JTextField(10);
        panelEntradas.add(campoFechaEspecifica);
        
        botonBuscarExtremosEnFecha = new JButton("Buscar Extremos en Fecha");
        botonBuscarExtremosEnFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarExtremosEnFecha();
            }
        });
        panelEntradas.add(botonBuscarExtremosEnFecha);
        panelEntradas.add(new JLabel("")); 

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);

        panelPrincipal.add(panelEntradas, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
    }
    
    private void calcularPromedios() {
        try {
            LocalDate desde = LocalDate.parse(campoFechaInicial.getText(), formatter);
            LocalDate hasta = LocalDate.parse(campoFechaFinal.getText(), formatter);
            

            Map<String, Double> promedios = datosCargados.stream()
                .filter(t -> !t.getFecha().isBefore(desde) && !t.getFecha().isAfter(hasta))
                .collect(Collectors.groupingBy(
                    Temperatura::getCiudad,
                    Collectors.averagingDouble(Temperatura::getTemperatura)
                ));
            
            StringBuilder resultado = new StringBuilder();
            resultado.append("Promedios de temperátura por ciudad:\n");
            
            for (Map.Entry<String, Double> entry : promedios.entrySet()) {
                resultado.append(String.format("%s: %.2f°C\n", entry.getKey(), entry.getValue()));
            }
            
            areaResultados.setText(resultado.toString());
            
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de fecha. Use dd/MM/yyyy", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarExtremosEnFecha() {
        try {
            LocalDate fecha = LocalDate.parse(campoFechaEspecifica.getText(), formatter);
            
            List<Temperatura> enFecha = datosCargados.stream()
                .filter(t -> t.getFecha().equals(fecha))
                .collect(Collectors.toList());
            
            if (enFecha.isEmpty()) {
                areaResultados.setText("No hay datos para esa fecha.");
            } else {
                Temperatura max = enFecha.stream().max(Comparator.comparing(Temperatura::getTemperatura)).get();
                Temperatura min = enFecha.stream().min(Comparator.comparing(Temperatura::getTemperatura)).get();
                
                StringBuilder resultado = new StringBuilder();
                resultado.append("Resultados para la fecha: ").append(fecha.format(formatter)).append("\n\n");
                resultado.append("Ciudad más calurosa: ").append(max.getCiudad())
                         .append(" (").append(max.getTemperatura()).append("°C)\n");
                resultado.append("Ciudad menos calurosa: ").append(min.getCiudad())
                         .append(" (").append(min.getTemperatura()).append("°C)");
                
                areaResultados.setText(resultado.toString());
            }
            
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Error en el formato de fecha. Use dd/MM/yyyy", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, 
                "Error al leer el archivo. Verifique que exista",
                "Error de archivo", 
                JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppTemperaturaGUI();
            }
        });
    }
}