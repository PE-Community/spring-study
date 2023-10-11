package pe.pecommunity.domain.File.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.pecommunity.domain.post.domain.Post;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="file_pk")
    private long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="post_fk")
    private Post post;

    @Column(nullable = false)
    private String uploadFileName;

    @Column(nullable = false)
    private String storeFileName;

    @Column(nullable = false)
    private long fileSize;

    private LocalDateTime uploadDate;

    @Builder(builderMethodName = "createFileBuilder")
    public File(Post post, String uploadFileName, String storeFileName, long fileSize) {
        this.post = post;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.fileSize = fileSize;
        this.uploadDate = LocalDateTime.now();

        //==연관관계 편의 메서드==//
        post.getFiles().add(this);
    }
}
