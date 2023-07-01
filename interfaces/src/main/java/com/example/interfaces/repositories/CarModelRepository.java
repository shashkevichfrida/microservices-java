package com.example.interfaces.repositories;

import com.example.interfaces.entities.CarBrand;
import com.example.interfaces.entities.CarModel;
import com.example.interfaces.entities.Engine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    @Query("SELECT DISTINCT carModel.carBrand FROM CarModel carModel WHERE carModel.carBrand.id = :carBrandId")
    public CarBrand getAllByVId(@Param("carBrandId") Long carBrandId);
    @Query("SELECT carModel.engine FROM CarModel carModel WHERE carModel.engine.id = :modelId")
    Engine getAllByName(@Param("modelId") Long modelId);
    @Query("SELECT carModel FROM CarModel carModel WHERE carModel.carBrand.id = :carBrandId")
    List<CarModel> getAllById(@Param("carBrandId") Long carBrandId);

    @Modifying
    @Transactional
    @Query("DELETE from CarModel carModel where carModel.carBrand.id = :carBrandId")
    void deleteByIdCarBrand(@Param("carBrandId") Long carBrandId);
}
