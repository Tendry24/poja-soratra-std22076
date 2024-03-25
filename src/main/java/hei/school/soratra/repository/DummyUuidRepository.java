package hei.school.soratra.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hei.school.soratra.PojaGenerated;
import hei.school.soratra.repository.model.DummyUuid;

@PojaGenerated
@Repository
public interface DummyUuidRepository extends JpaRepository<DummyUuid, String> {

  @Override
  Optional<DummyUuid> findById(String id);
}
