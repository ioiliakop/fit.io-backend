package com.msg.msg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.msg.msg.entities.Area;

public interface AreaRepository extends JpaRepository<Area, Integer>{

	Area findById(int id);
	
	@Modifying
    @Query(value = "insert into trainer_area (fk_trainer_id, fk_area_id) VALUES (:trainerId,:areaId)", nativeQuery = true)
    @Transactional
    void addArea(@Param("trainerId") int fk_trainer_id, @Param("areaId") int fk_area_id);
	
	@Modifying
    @Query(value = "delete from trainer_area where fk_trainer_id =:trainerId and fk_area_id =:areaId", nativeQuery = true)
    @Transactional
    void removeArea(@Param("trainerId") int fk_trainer_id, @Param("areaId") int fk_area_id);
}
