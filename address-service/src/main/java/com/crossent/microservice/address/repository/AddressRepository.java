package com.crossent.microservice.address.repository;

import com.crossent.microservice.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT count(*) FROM address", nativeQuery = true)
    int findAllCount();

    @Query(value = "SELECT sido FROM address GROUP BY sido", nativeQuery = true)
    List<String> findAllSidoGroupBy();

    @Query(value = "SELECT gugun FROM address WHERE sido=:sido GROUP BY gugun", nativeQuery = true)
    List<String> findAllGuGunGroupBy(@Param("sido") String sido);

    @Query(value = "SELECT dong FROM address WHERE sido=:sido AND gugun =:gugun GROUP BY dong", nativeQuery = true)
    List<String> findAllDongGroupBy(@Param("sido") String sido, @Param("gugun") String gugun );

    Address findBySidoAndGugunAndDong(String sido, String gugun, String dong);

}
