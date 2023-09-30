package physical.education.yehjung.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import physical.education.yehjung.file.dto.FileDto;

public interface FileRepository extends JpaRepository<FileDto,Long> {
}
