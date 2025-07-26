package pl.borovy.personalwebsiteblogapi;

public enum Authority {
    USER,
    ADMIN;

    public String scope() {
        return "SCOPE_" + toString();
    }
}
