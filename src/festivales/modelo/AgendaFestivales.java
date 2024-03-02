package festivales.modelo;

import festivales.modelo.Estilo;
import festivales.modelo.Festival;
import festivales.modelo.Mes;

import java.util.*;


/**
 * Esta clase guarda una agenda con los festivales programados
 * en una serie de meses
 *
 * La agenda guardalos festivales en una colección map
 * La clave del map es el mes (un enumerado festivales.modelo.festivales.modelo.Mes)
 * Cada mes tiene asociados en una colección ArrayList
 * los festivales  de ese mes
 *
 * Solo aparecen los meses que incluyen algún festival
 *
 * Las claves se recuperan en orden alfabéico
 *
 */
public class AgendaFestivales {
    private TreeMap<Mes, ArrayList<Festival>> agenda;
    
    public AgendaFestivales() {
        this.agenda = new TreeMap<>();
    }

    /**
     * añade un nuevo festival a la agenda
     *
     * Si la clave (el mes en el que se celebra el festival)
     * no existe en la agenda se creará una nueva entrada
     * con dicha clave y la colección formada por ese único festival
     *
     * Si la clave (el mes) ya existe se añade el nuevo festival
     * a la lista de festivales que ya existe ese ms
     * insertándolo de forma que quede ordenado por nombre de festival.
     * Para este segundo caso usa el método de ayuda
     * obtenerPosicionDeInsercion()
     *
     */
    public void addFestival(Festival festival) {
        //TODO
        Mes mimes = festival.getMes();
        if(!agenda.containsKey(mimes)){
            ArrayList<Festival> festivales = new ArrayList<>();
            festivales.add(festival);
            agenda.put(mimes, festivales);
        }
        else{
            int pos = obtenerPosicionDeInsercion(agenda.get(mimes), festival);
            agenda.get(mimes).add(pos, festival);
        }
        
    }

    /**
     *
     * @param festivales una lista de festivales
     * @param festival
     * @return la posición en la que debería ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales, Festival festival) {
       //TODO
        int pos = 0;
//        for(festivales.modelo.Festival mifestival: festivales){
//            if(festival.getNombre().compareToIgnoreCase(mifestival.getNombre()) > 0 && festival.getNombre().compareToIgnoreCase(festivales.get(festivales.indexOf(mifestival)+1).getNombre()) < 0){
//                pos = festivales.indexOf(mifestival);
//            }
//        }

        Iterator<Festival> it = festivales.iterator();
        while(it.hasNext()){
            Festival mifest = it.next();
            String fest = mifest.getNombre();
            if(festival.getNombre().compareToIgnoreCase(fest)>0){
               pos = festivales.indexOf(mifest)+1;
            }

        }

        return pos;
        
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas para recorrer el map
     */
    @Override
    public String toString() {
        //TODO
        StringBuilder sb = new StringBuilder();
        for(Mes mimes: agenda.keySet()){
            sb.append(mimes + "\n");
            int i = 0;
            ArrayList<Festival> misfest = agenda.get(mimes);
            while(i<misfest.size()){
                Festival mifest = misfest.get(i);
                sb.append(mifest.toString() + "\n");
                i++;
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     *
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve -1
     */
    public int festivalesEnMes(Mes mes) {
       //TODO
        int cant= 0;
        if(agenda.containsKey(mes)){
            cant = agenda.get(mes).size();
        }
        else{
           return 0;
        }

        return cant;
    }

    /**
     * Se trata de agrupar todos los festivales de la agenda
     * por estilo.
     * Cada estilo que aparece en la agenda tiene asociada una colección
     * que es el conjunto de nombres de festivales que pertenecen a ese estilo
     * Importa el orden de los nombres en el conjunto
     *
     * Identifica el tipo exacto del valor de retorno
     */
    public  TreeMap<Estilo, TreeSet<String>> festivalesPorEstilo() {
       //TODO
        TreeMap<Estilo, TreeSet<String>> festPorEstilos= new TreeMap<>();
        for(Estilo e: Estilo.values()){
            TreeSet<String> nom = new TreeSet<>();
            for(Mes mimes: agenda.keySet()){
                int i = 0;
                ArrayList<Festival> misfest = agenda.get(mimes);
                    while(i<misfest.size()){
                            if(misfest.get(i).getEstilos().contains(e)){
                                nom.add(misfest.get(i).getNombre());
                            }
                            i++;
                    }

            }
            festPorEstilos.put(e, nom);
        }
        Iterator<Estilo> it = festPorEstilos.keySet().iterator();
        while(it.hasNext()){
            Estilo est = it.next();
            if(festPorEstilos.get(est).isEmpty()){
                it.remove();
            }
        }

        return festPorEstilos;
    }

    /**
     * Se cancelan todos los festivales organizados en alguno de los
     * lugares que indica el conjunto en el mes indicado. Los festivales
     * concluidos o que no empezados no se tienen en cuenta
     * Hay que borrarlos de la agenda
     * Si el mes no existe se devuelve -1
     *
     * Si al borrar de un mes los festivales el mes queda con 0 festivales
     * se borra la entrada completa del map
     */
    public int cancelarFestivales(HashSet<String> lugares, Mes mes) {
       //TODO
        int cant = 0;
        if(agenda.containsKey(mes)){
            Iterator<Festival> it = agenda.get(mes).iterator();
            while(it.hasNext()){
                Festival mifest = it.next();
                if(lugares.contains(mifest.getLugar())){
                    it.remove();
                    cant++;
                }
                if(agenda.get(mes).isEmpty()){
                    agenda.remove(mes);
                }
            }

//            ArrayList<festivales.modelo.Festival> misfest = agenda.get(mes);
//            for(festivales.modelo.Festival fest: misfest){
//                if(lugares.contains(fest.getLugar())){
//                    misfest.remove(fest);
//                    if(misfest.isEmpty()){
//                        agenda.remove(mes);
//                    }
//                }
//            }
        }
        else{
            return - 1;
        }
        return cant;
    }
}
