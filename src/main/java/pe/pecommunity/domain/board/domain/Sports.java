package pe.pecommunity.domain.board.domain;

public enum Sports {
    SOCCER("축구"), BASKETBALL("농구"), BADMINTON("배드민턴"), VOLLEYBALL("배구");

    private String code;

    Sports(String code) {
        this.code = code;
    }
}
