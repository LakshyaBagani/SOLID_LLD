public class EvaluationPipeline {
    private final Checker checker;
    private final Grader grader;
    private final Reporter reporter;
    private final Rubric rubric;

    public EvaluationPipeline(Checker checker, Grader grader, Reporter reporter, Rubric rubric) {
        this.checker = checker; this.grader = grader;
        this.reporter = reporter; this.rubric = rubric;
    }

    public void evaluate(Submission sub) {
        int plag = checker.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = grader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = reporter.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
