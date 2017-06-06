package xr.tsa.support;

import org.springframework.data.jpa.repository.JpaRepository;

import xr.tsa.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
