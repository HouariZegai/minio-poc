package org.zegai.miniopoc.storage;

public enum FileStorageFolder {

    SPONSOR("sponsor", true),
    PARTNER("partner", true),
    INSTRUCTOR("instructor", true),
    COURSE("course-cover", true),
    ELEMENT_FILE("element-file", false),
    QUESTION_FILE("question-file", false),
    PAYMENT_PROOF("payment-proof", false),
    PROGRESS_REPORT("progress-report", false),
    STUDENT_ANSWERS("student-answers", false);

    private final String name;
    private final boolean isPublic;

    FileStorageFolder(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }

    public String getPath() {
        String parentFolder = isPublic ? "public/" : "";
        return parentFolder + name;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
