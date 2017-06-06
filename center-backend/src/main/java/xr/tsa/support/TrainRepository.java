package xr.tsa.support;

import org.springframework.data.jpa.repository.JpaRepository;

import xr.tsa.Model.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

}
