package com.example.tickets;

import java.util.*;

public class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;

    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        this.tags = Collections.unmodifiableList(new ArrayList<>(b.tags));
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; }
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    public Builder toBuilder() {
        Builder b = new Builder(id, reporterEmail, title);
        b.description = description;
        b.priority = priority;
        b.tags = new ArrayList<>(tags);
        b.assigneeEmail = assigneeEmail;
        b.customerVisible = customerVisible;
        b.slaMinutes = slaMinutes;
        b.source = source;
        return b;
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }

    public static class Builder {
        private final String id;
        private final String reporterEmail;
        private final String title;

        String description;
        String priority;
        List<String> tags = new ArrayList<>();
        String assigneeEmail;
        boolean customerVisible;
        Integer slaMinutes;
        String source;

        public Builder(String id, String reporterEmail, String title) {
            this.id = id; this.reporterEmail = reporterEmail; this.title = title;
        }

        public Builder description(String d) { this.description = d; return this; }
        public Builder priority(String p) { this.priority = p; return this; }
        public Builder tags(List<String> t) { this.tags = new ArrayList<>(t); return this; }
        public Builder addTag(String t) { this.tags.add(t); return this; }
        public Builder assigneeEmail(String e) { this.assigneeEmail = e; return this; }
        public Builder customerVisible(boolean v) { this.customerVisible = v; return this; }
        public Builder slaMinutes(Integer m) { this.slaMinutes = m; return this; }
        public Builder source(String s) { this.source = s; return this; }

        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            if (assigneeEmail != null) Validation.requireEmail(assigneeEmail, "assigneeEmail");
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");
            return new IncidentTicket(this);
        }
    }
}
