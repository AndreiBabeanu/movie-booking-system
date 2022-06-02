package unibuc.moviebooking.enums;

public class GenresHelper {

    public static String convertToString(Genres genre) {
        switch (genre) {
            case DRAMA: return "DRAMA";
            case ACTION: return "ACTION";
            case COMEDY: return "COMEDY";
            case SCI_FI: return "SCI-FI";
            case ROMANCE: return "ROMANCE";
            case THRILLER: return "THRILLER";
            case ADVENTURE: return "ADVENTURE";
            default: return "NOT AVAILABLE";
        }
    }

    public static Genres convertToGenre(String genre) {
        switch (genre) {
            case "DRAMA": return Genres.DRAMA;
            case "ACTION": return Genres.ACTION;
            case "COMEDY": return Genres.COMEDY;
            case "SCI_FI": return Genres.SCI_FI;
            case "THRILLER": return Genres.THRILLER;
            case "ADVENTURE": return Genres.ADVENTURE;
            default: return Genres.ROMANCE;
        }
    }
}
