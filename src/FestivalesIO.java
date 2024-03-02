
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 */
public class FestivalesIO {

    
    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);

            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

    }

    /**
     * se parsea la línea extrayendo sus datos y creando y
     * devolviendo un objeto Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {

        String[] tokens = lineaFestival.split(":");
        String nom = tokens[0].trim();

        String[] nombre = nom.split(" ");
        StringBuilder nomMayus = new StringBuilder();
        for(String minombre: nombre){
            String nomM = minombre.substring(0, 1).toUpperCase() + minombre.substring(1).toLowerCase();
            nomMayus.append(nomM + " ");
        }

        String lug = tokens[1].trim().toUpperCase();

        String[] fecha = tokens[2].trim().split("-");
        Integer anio = Integer.valueOf(fecha[2]);
        Integer mes = Integer.valueOf(fecha[1]);
        Integer dia = Integer.valueOf(fecha[0]);
        LocalDate fechaIn = LocalDate.of(anio, mes, dia);

        Integer durac = Integer.valueOf(tokens[3].trim());

        HashSet<Estilo> estil = new HashSet<>();
        int i = 4;
        while(i<tokens.length){
            estil.add(Estilo.valueOf(tokens[i].toUpperCase().trim()));
            i++;
        }
        Festival mifest = new Festival(nomMayus.toString(), lug, fechaIn, durac, estil);

        return mifest;
    }
    
   
    
    
}
