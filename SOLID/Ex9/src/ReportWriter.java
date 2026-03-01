public class ReportWriter implements Reporter {
    public String write(Submission s, int plag, int code) {
        return "report-" + s.roll + ".txt";
    }
}
