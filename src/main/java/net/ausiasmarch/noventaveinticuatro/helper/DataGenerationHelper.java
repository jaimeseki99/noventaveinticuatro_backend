package net.ausiasmarch.noventaveinticuatro.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.DatatypeConverter;

import net.ausiasmarch.noventaveinticuatro.exception.CannotPerformOperationException;

public class DataGenerationHelper {


    private static final String[] nombres = { 
        "Mónica", "Jose Antonio", "Laura",
         "Lucas", "Eva", "Eloy", "Jesús", 
         "Alan", "Pablo", "Paula", "Raquel",
         "Nieves", "Elena", "Sergio", "Fernando", 
         "Jaime", "Vicente", "Ana", "Andrea", 
         "Raúl", "Carlos", "Manuel", "Alejandro", 
         "Sandra", "Lorena", "Antonio", "David", 
         "Victor", "Jorge", "Alberto", "Óscar",
         "Carmen", "Miguel", "Alba", "Laia", 
         "Juan", "Santiago", "Ramón", "Ainara", 
         "Claudia", "Unai", "Josep", "Oriol", 
         "María", "Teresa", "Ibai", "Iñaki", 
         "Isabel", "Rosa", "Rocío", "Lionel", 
         "Anna", "Rafael", "Ona", "Samuel", 
         "Iñigo", "Carles", "Guillermo", "Guillem",
          "Julia", "Benito", "Marta", "Sofía",
           "David", "Julián"};

    private static final String[] apellidos = {
         "Alcañiz", "Puig", "Ayala", "Farell",
          "Ferrer", "Esteve", "González", "Rozalén", 
          "Lara", "Velarte", "Latorre", "Briones", 
          "Maldonado", "Suárez", "McLure", "Alarcón", 
          "Molero", "Marín", "Muñoz", "García", 
          "Navarro", "López", "Navas", "Aguilar", 
          "Ortega", "Fabra", "Romero", "Díaz", 
          "Roselló", "Gómez", "Serrano", "Quílez", 
          "Martínez", "Sánchez", "Álvarez", "Fernández",
           "Roldán", "Izquierdo", "Marqueño", "Mozo", 
           "Baeza", "Sierra", "Pascual", "Soriano", 
           "Pérez", "Hervás", "Gutiérrez", "Manrique",
            "Pastor", "Núñez", "Benítez", "Ordoñez", 
            "Peris", "Romeu", "Sanchis", "Calatayud", 
            "Aznar", "Aparaci", "Messi", "Castillo", 
            "Melano", "Llanos", "Vargas"};

    private static final String[] ciudades = {
        "A Coruña", "Santiago de Compostela", "Vigo", "Ourense",
        "Pontevedra", "Lugo", "Gijón", "Oviedo",
        "Santander", "Logroño", "Bilbao", "San Sebastián",
        "Vitoria", "Pamplona", "Teruel", "Huesca",
        "Zaragoza", "Barcelona", "Tarragona", "Girona", 
        "Lleida", "Valencia", "Alicante", "Castellón", 
        "Palma de Mallorca", "Menorca", "Ibiza", "Murcia",
        "Albacete", "Ciudad Real", "Toledo", "Guadalajara", 
        "Cuenca", "Madrid", "León", "Valladolid", 
        "Segovia", "Ávila", "Salamanca", "Soria", 
        "Burgos", "Zamora", "Palencia", "Cádiz", 
        "Huelva", "Sevilla", "Granada", "Almería", 
        "Jaén", "Córdoba", "Málaga", "Cáceres", 
        "Badajoz", "Tenerife", "Las Palmas de Gran Canaria"};

    private static final String[] tipoCalle = {"Calle", "Plaza", "Avenida", "Paseo", "Vía"};

    private static final String[] nombreCalle = {
        "Colón", "Puerto", "La Plata", "Real",
        "Reino", "República", "9 de octubre", 
        "2 de mayo", "Gran Vía", "Alcalá", "Prado", 
        "Serrano", "Mayor", "Fuencarral", "Preciados", 
        "Castellana", "Gracia", "Moncada", "Diagonal", 
        "Ferran", "Provencial", "Ruzafa", "Castro", 
        "San Vicente", "Las Barcas", "Los Peregrinos", "Feria", 
        "Pedro Sánchez", "Mariano Rajoy", "Falsa", "Príncipe Felipe", 
        "Evergreen Terrace", "Constitución", "La Virgen", "Asunción", 
        "Santo Tomás", "San Juan", "Independencia", "Viñeda", 
        "Cervantes", "Mayor", "Charizard", "España", 
        "Aragón", "Baleares", "Cataluña", "Canarias", 
        "Galicia", "Andalucía", "País Vasco", "Valencia", 
        "Castilla", "Felipe VI", "Asturias"};

    private static final String[] equipos = {
        "Real Madrid", "FC Barcelona", "Atlético de Madrid", "Valencia CF", 
        "Sevilla FC", "Villarreal CF", "Real Sociedad", "Athletic Club Bilbao", 
        "Real Betis Balompié", "RC Celta de Vigo", "Getafe CF", "Rayo Vallecano", 
        "Girona FC", "Deportivo Alavés", "RCD Mallorca", "Cádiz CF", 
        "CA Osasuna", "Granada CF", "UD Almería", "UD Las Palmas", 
        "Chelsea FC", "Manchester United", "Manchester City", "Arsenal FC", 
        "Liverpool FC", "Tottenham Hotspur", "West Ham United", "Aston Villa", 
        "FC Bayern München", "Borussia Dortmund", "RB Leipzig", "Bayer Leverkusen", 
        "Juventus", "Inter Milan", "AC Milan", "AS Roma", 
        "Lazio", "Fiorentina", "Paris Saint Germain", "Olympique Lyon", 
        "Olympique Marsella", "AS Monaco", "Denver Nuggets", "Los Angeles Lakers", 
        "Los Angeles Clippers", "Golden State Warriors", "Boston Celtics", "New York Knicks", 
        "Chicago Bulls", "Miami Heat", "Houston Rockets", "Dallas Mavericks", 
        "San Antonio Spurs", "Toronto Raptors", "Philadelphia 76ers", "Milwaukee Bucks", 
        "Portland Trail Blazers", "Utah Jazz", "Phoenix Suns", "Sacramento Kings",
        "Atlanta Hawks", "Charlotte Hornets", "Cleveland Cavaliers", "Detroit Pistons", 
        "Indiana Pacers", "Orlando Magic", "Washington Wizards", "Minnesota Timberwolves", 
        "Brooklyn Nets", "New Orleans Pelicans", "Memphis Grizzlies", "Oklahoma City Thunder"};

    private static final String[] tiposCamiseta = {"Local", "Visitante", "Tercera"};

    private static final String[] versionCamiseta = {"Versión Jugador", "Versión Aficionado", "Retro", "Versión Mujer", "Versión Niño"};

    private static final String[] tallasCamiseta = {"S", "M", "L", "XL", "XXL"};

    private static final String[] mangasCamiseta = {"Manga Corta", "Manga Larga"};

     public static String getNombreRandom() {
        return nombres[(int) (Math.random() * nombres.length)];
    }

    public static String getApellidoRandom() {
        return apellidos[(int) (Math.random() * apellidos.length)];
    }

    public static String doNormalizeString(String cadena) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String cadenaSinAcentos = cadena;
        for (int i = 0; i < original.length(); i++) {
            cadenaSinAcentos = cadenaSinAcentos.replace(original.charAt(i), ascii.charAt(i));
        }
        return cadenaSinAcentos;
    }

    public static String generarNumeroTelefono() {
        String primerDigito = String.valueOf(6 + new Random().nextInt(2));
        String restoNumero = generarNumeroRestante();
        return primerDigito + restoNumero;
    }

    public static String generarNumeroRestante() {
        Random random = new Random();
        StringBuilder numeroRestante = new StringBuilder();
        for (int i = 0; i<8; i++) {
            numeroRestante.append(random.nextInt(10));
        }
        return numeroRestante.toString();
    }

    public static String getCiudadRandom() {
        return ciudades[(int) (Math.random() * ciudades.length)];
    }

    public static String getTipoCalleRandom() {
        return tipoCalle[(int) (Math.random() * tipoCalle.length)];
    }

    public static String getNombreCalleRandom() {
        return nombreCalle[(int) (Math.random() * nombreCalle.length)];
    }

    public static String generarDireccionRandom() {
        Random random = new Random();
        StringBuilder nuevaCalle = new StringBuilder();

        nuevaCalle.append(getTipoCalleRandom());
        nuevaCalle.append(" ");
        nuevaCalle.append(getNombreCalleRandom());
        nuevaCalle.append(" ");
        nuevaCalle.append(random.nextInt(150));
        nuevaCalle.append(", ");
        nuevaCalle.append(getCiudadRandom());
        
        return nuevaCalle.toString();
    }

    public static double generarDobleRandom() {
        Random random = new Random();
        return random.nextDouble(1000);
    }

    public static int generarIntRandom() {
        Random random = new Random();
        return random.nextInt(1000);
    }

    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        int numeroRandom = random.nextInt((max - min) + 1) + min;
        return numeroRandom;
    }

    public static LocalDateTime getFechaRandom() {
        long diaMinimo = LocalDate.of(2020, 1, 1).toEpochDay();
        long diaMaximo = LocalDate.of(2023, 11, 1).toEpochDay();
        long diaRandom = ThreadLocalRandom.current().nextLong(diaMinimo, diaMaximo);
        return LocalDate.ofEpochDay(diaRandom).atTime(getRandomInt(0, 23), getRandomInt(0, 59), getRandomInt(0, 59));
    }


    public static String getSHA256(String strToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte [] digest = md.digest(strToHash.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            throw new CannotPerformOperationException("No such algorithm: SHA256");
        }
    }
    
}
