public class SecretaryTool implements MinutesTool {
    private final MinutesBook book;
    public SecretaryTool(MinutesBook book) { this.book = book; }

    public void addMinutes(String text) { book.add(text); }
}
