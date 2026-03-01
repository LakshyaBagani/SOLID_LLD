public class Main {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        System.out.println("PDF: " + fmt(pdf.export(req)));
        System.out.println("CSV: " + fmt(csv.export(req)));
        System.out.println("JSON: " + fmt(json.export(req)));
    }

    static String fmt(ExportResult r) {
        if (r.error != null) return "ERROR: " + r.error;
        return "OK bytes=" + r.bytes.length;
    }
}
