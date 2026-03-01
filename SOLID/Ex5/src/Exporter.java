public abstract class Exporter {

    public final ExportResult export(ExportRequest req) {
        if (req == null) throw new IllegalArgumentException("req must not be null");
        return doExport(req);
    }

    protected abstract ExportResult doExport(ExportRequest req);
}
