package xr.tsa.support;

import org.springframework.data.jpa.repository.JpaRepository;

import xr.tsa.Model.UserInstalledApps;

public interface UserInstalledAppsRepository extends JpaRepository<UserInstalledApps, Long> {

}
