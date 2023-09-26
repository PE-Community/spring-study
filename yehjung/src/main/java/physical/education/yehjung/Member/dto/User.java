package physical.education.yehjung.Member.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_PK")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTH_CD_PK", nullable = false)
    private Authority role;//ROLE_USER. ROLE_ADMIN

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @CreationTimestamp
    private Timestamp dormantYmd;

}

