package info.nyberg.frogroom.services;

import info.nyberg.frogroom.repositories.FrogRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
class FrogRoomCheckIndicator implements HealthIndicator {
  @Autowired private FrogRepositry frogRepositry;

  @Override
  public Health getHealth(boolean includeDetails) {
    if (frogRepositry.findAll().size() > 0) return Health.up().build();
    else return Health.down().build();
  }

  @Override
  public Health health() {
    if (frogRepositry.findAll().size() > 0) return Health.up().build();
    else return Health.down().build();
  }
}
