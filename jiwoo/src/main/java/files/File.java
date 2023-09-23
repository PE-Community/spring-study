package files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import post.Post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FILE")
public class File {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="FILE_PK")
    private long file_pk;
    @ManyToOne
    @JoinColumn(name="POST_FK")
    private Post post_fk;
    @Column(name = "UPLOAD_FILE_NM", nullable = false)
    private String upload_file_nm;
    @Column(name = "SERVER_FILE_NM", nullable = false)
    private String server_file_nm;
    @Column(name = "UPLOAD_PATH", nullable = false)
    private String upload_path;
    @Column(name = "TYPE", nullable = false)
    private String type;
    @Column(name = "FILE_SIZE", nullable = false)
    private long file_size;
    private LocalDateTime upload_ymd;

    @Builder
    public File(String upload_file_nm, String server_file_nm, String upload_path, String type, long file_size, LocalDateTime upload_ymd) {
        this.upload_file_nm = upload_file_nm;
        this.server_file_nm = server_file_nm;
        this.upload_path = upload_path;
        this.type = type;
        this.file_size = file_size;
        this.upload_ymd = upload_ymd;
    }
}
