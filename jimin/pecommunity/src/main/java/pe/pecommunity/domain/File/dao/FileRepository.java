package pe.pecommunity.domain.File.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.pecommunity.domain.File.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
