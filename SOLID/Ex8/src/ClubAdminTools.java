// Smell: original fat interface.
public interface ClubAdminTools {
    void addIncome(double amt, String note);
    void addExpense(double amt, String note);
    void addMinutes(String text);
    void createEvent(String name, double budget);
    int getEventsCount();
}
