package xr.tsa.support;

import org.springframework.data.jpa.repository.JpaRepository;

import xr.tsa.Model.AppCategories;

public interface AppCategoriesRepository  extends JpaRepository<AppCategories, Long> {

}
