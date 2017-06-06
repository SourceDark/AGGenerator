package xr.tsa.support;

import org.springframework.data.jpa.repository.JpaRepository;

import xr.tsa.Model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

}
