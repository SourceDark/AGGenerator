package xr.tsa.support;

import org.springframework.data.jpa.repository.JpaRepository;

import xr.tsa.Model.Ad;

public interface AdRepository extends JpaRepository<Ad, Long> {

}
