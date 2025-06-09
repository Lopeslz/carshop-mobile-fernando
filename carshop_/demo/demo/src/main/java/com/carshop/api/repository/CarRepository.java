    package com.carshop.api.repository;

    import com.carshop.api.model.Car;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;

    public interface CarRepository extends JpaRepository<Car, Long> {
        List<Car> findBySellerId(Long sellerId);
        List<Car> findAllByOrderByCreatedAtDesc();


        @Query("SELECT c FROM Car c WHERE " +
                "(:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
                "(:year IS NULL OR c.year = :year) AND " +
                "(:variant IS NULL OR LOWER(c.variant) LIKE LOWER(CONCAT('%', :variant, '%')))")
        List<Car> searchCars(@Param("title") String title,
                                @Param("year") String year,
                                @Param("variant") String variant);



    }
