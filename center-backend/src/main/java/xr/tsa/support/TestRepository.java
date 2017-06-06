package xr.tsa.support;

import org.springframework.data.jpa.repository.JpaRepository;

import xr.tsa.Model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
