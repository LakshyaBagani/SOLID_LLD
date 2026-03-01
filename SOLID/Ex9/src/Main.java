public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");

        Checker checker = new PlagiarismChecker();
        Grader grader = new CodeGrader();
        Reporter reporter = new ReportWriter();
        Rubric rubric = new Rubric();

        EvaluationPipeline pipeline = new EvaluationPipeline(checker, grader, reporter, rubric);
        pipeline.evaluate(sub);
    }
}
