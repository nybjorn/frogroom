package info.nyberg.frogroom.repositories;

import info.nyberg.frogroom.models.Frog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrogRepositry extends JpaRepository<Frog, Long> {}
