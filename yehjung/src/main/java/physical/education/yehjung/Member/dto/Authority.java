package physical.education.yehjung.Member.dto;

public enum Authority {

    USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), ADMIN("ROLE_ADMIN");

    private final String authority;

    Authority(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return authority;
    }

}
