package files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
    List<File> saveFile(List<File> file);

    void deleteFileByPost_fk(long post_fk);
    List<File> findFileByPost_fk(long post_fk);
}
