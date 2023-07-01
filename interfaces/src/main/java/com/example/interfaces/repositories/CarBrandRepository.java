package com.example.interfaces.repositories;


import com.example.interfaces.entities.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {

    @Query("SELECT DISTINCT carBrand FROM CarBrand carBrand WHERE carBrand.name = :name")
    public CarBrand findByName(@Param("name") String name);
}
