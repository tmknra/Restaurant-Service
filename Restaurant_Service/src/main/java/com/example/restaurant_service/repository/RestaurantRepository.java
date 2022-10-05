package com.example.restaurant_service.repository;

import com.example.restaurant_service.entity.Restaurant;
import com.example.restaurant_service.repository.data.RestaurantSmall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByOwnerId(Long ownerId);

    @Query(value = """
                   SELECT res.id, max(res.name) as name, avg(feed.rating) as average FROM restaurants as res
                   join feedbacks as feed on res.id = feed.restaurant_id
                   group by res.id""",
            nativeQuery = true)
    Page<RestaurantSmall> findSmallRestaurants(Pageable pageable);

    @Modifying
    @Query("update Restaurant as r set r.ownerId = :newOwnerId where r.ownerId = :oldOwnerId")
    void updateOwner(@Param("oldOwnerId") Long oldOwnerId, @Param("newOwnerId") Long newOwnerId);

    @Modifying
    @Query("update Restaurant as r set r.ownerId = null where r.ownerId = :oldOwnerId")
    void deleteOwnerFromRestaurants(@Param("oldOwnerId") Long oldOwnerId);
}
