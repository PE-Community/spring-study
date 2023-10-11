package pe.pecommunity.domain.member.domain;

public enum Role {
    MEMBER("member"), ADMIN("admin"), DORMANT_ACCOUNT("dormant_account");

//    private static final String PREFIX = "ROLE_";
    private final String description;

    Role(String description) {
        this.description = description;
    }

//    public String getNameWithoutPrefix() {
//        return this.name().substring(PREFIX.length());
//    }
}
