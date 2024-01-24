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
        "Levante UD", "RCD Espanyol", "Real Zaragoza", "Real Sporting de Gijón",
        "Chelsea FC", "Manchester United", "Manchester City", "Arsenal FC", 
        "Liverpool FC", "Tottenham Hotspur", "West Ham United", "Aston Villa", 
        "Crystal Palace", "Everton FC", "Leicester City", "Newcastle United",
        "FC Bayern München", "Borussia Dortmund", "RB Leipzig", "Bayer Leverkusen", 
        "Borussia Mönchengladbach", "VfL Wolfsburg", "Eintracht Frankfurt", "Werder Bremen",
        "Juventus", "Inter Milan", "AC Milan", "AS Roma", 
        "SSC Napoli", "Atalanta BC", "Lazio", "Fiorentina",
        "Paris Saint Germain", "Olympique Lyon", "Montpellier", "LOSC Lille",
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

    private static final String[] mangasCamiseta = {"Corta", "Larga"};

    // Array de Strings que contengan diferentes adjetivos que puedan describir a una camiseta de fútbol o de baloncesto
    private static final String[] adjetivosCamiseta = {"Retro", "Clásica", "Moderna", "Original", "Alternativa", "Ajustada", "Ancha", "Cómoda", "Corta", "Larga", "Deportiva", "Elegante", "Femenina", "Masculina", "Infantil", "Juvenil", "Adulto", "Unisex", "Buen Estado", "Mal estado", "Nueva", "Usada", "Barata", "Cara", "Cortesía", "Descuento", "Oferta", "Rebaja", "Regalo", "Gratis", "Envío Gratis", "Envío Rápido", "Envío Urgente", "Envío Normal", "Envío Lento", "Envío Gratis"};
    
    private static final String[] adjetivosPersona = {"La recomiendo", "No la recomiendo", "Me encanta", "Tremenda decepción", "No me gusta", "Me gusta", "Me encanta", "Me flipa", "Me flipa mucho", "Me flipa un montón", "Me flipa un huevo", "Me flipa un huevo y la yema del otro", "Me flipa un huevo y la yema del otro y la clara del otro", "Me flipa un huevo y la yema del otro y la clara del otro y la cáscara del otro", "Me flipa un huevo y la yema del otro y la clara del otro y la cáscara del otro y la gallina que lo puso", "Me flipa un huevo y la yema del otro y la clara del otro y la cáscara del otro y la gallina que lo puso y el granjero que la cuidó", "Me flipa un huevo y la yema del otro y la clara del otro y la cáscara del otro y la gallina que lo puso y el granjero que la cuidó y el camionero que la transportó", "Me flipa un huevo y la yema del otro y la clara del otro y la cáscara del otro y la gallina que lo puso y el granjero que la cuidó y el camionero que la transportó y el tendero que la vendió", "Me flipa un huevo y la yema del otro y la clara del otro y la cáscara del otro y la gallina que lo puso y el granjero que la cuidó y el camionero que la transportó y el tendero que la vendió y el cliente que la compró"};

    private static final String[] paises = {"España", "Portugal", "Francia", "Estados Unidos", "Inglaterra", "Italia", "Alemania", "Países Bajos", "Rusia"};

    private static final String[] ligas = {"La Liga", "Premier League", "Serie A", "Bundesliga", "Ligue 1", "Eredivisie", "MLS", "Liga Portuguesa", "Liga Rusa", "NBA", "Liga ACB Endesa"};

    private static final String[] deportes = {"Fútbol", "Baloncesto"};

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

    public static double getRandomDouble(int min, int max) {
        Random random = new Random();
        double numeroRandom = min + (max - min) * random.nextDouble();
        return numeroRandom;
    }

    public static LocalDateTime getFechaRandom() {
        long diaMinimo = LocalDate.of(2020, 1, 1).toEpochDay();
        long diaMaximo = LocalDate.of(2023, 11, 1).toEpochDay();
        long diaRandom = ThreadLocalRandom.current().nextLong(diaMinimo, diaMaximo);
        return LocalDate.ofEpochDay(diaRandom).atTime(getRandomInt(0, 23), getRandomInt(0, 59), getRandomInt(0, 59));
    }

    public static String getEquipoRandom() {
        return equipos[(int) (Math.random() * equipos.length)];
    }

    public static String getTipoCamisetaRandom() {
        return tiposCamiseta[(int) (Math.random() * tiposCamiseta.length)];
    }

    public static String getVersionCamisetaRandom() {
        return versionCamiseta[(int) (Math.random() * versionCamiseta.length)];
    }

    public static String getTallaCamisetaRandom() {
        return tallasCamiseta[(int) (Math.random() * tallasCamiseta.length)];
    }

    public static String getMangaCamisetaRandom() {
        return mangasCamiseta[(int) (Math.random() * mangasCamiseta.length)];
    }

    //Método para generar un título de camiseta aleatorio
    public static String generarTituloCamisetaRandom() {
        
        StringBuilder tituloCamiseta = new StringBuilder();

        tituloCamiseta.append("Camiseta ");
        tituloCamiseta.append(getTipoCamisetaRandom());
        tituloCamiseta.append(" ");
        tituloCamiseta.append(getEquipoRandom());
        tituloCamiseta.append(" ");
        tituloCamiseta.append(getVersionCamisetaRandom());
        tituloCamiseta.append(" ");
        tituloCamiseta.append(getMangaCamisetaRandom());
        tituloCamiseta.append(" ");
        tituloCamiseta.append(getTallaCamisetaRandom());
        
        return tituloCamiseta.toString();
    }

    //Método para obtener un adjetivo de la camiseta random
    public static String getAdjetivoCamisetaRandom() {
        return adjetivosCamiseta[(int) (Math.random() * adjetivosCamiseta.length)];
    }

    //Método para obtener un adjetivo de la persona aleatorio
    public static String getAdjetivoPersonaRandom() {
        return adjetivosPersona[(int) (Math.random() * adjetivosPersona.length)];
    }

    //Método para generar un comentario aleatorio
    public static String generarComentarioRandom() {
        StringBuilder comentario = new StringBuilder();
        comentario.append("La camiseta que me ha llegado me parece ");
        comentario.append(getAdjetivoCamisetaRandom());
        comentario.append(". ");
        comentario.append("Sinceramente ");
        comentario.append(getAdjetivoPersonaRandom());
        comentario.append(".");

        return comentario.toString();
    }

    public static String getPaisRandom() {
        return paises[(int) (Math.random() * paises.length)];
    }

    public static String getLigaRandom() {
        return ligas[(int) (Math.random() * ligas.length)];
    }

    public static String getDeporteRandom() {
        return deportes[(int) (Math.random() * deportes.length)];
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
