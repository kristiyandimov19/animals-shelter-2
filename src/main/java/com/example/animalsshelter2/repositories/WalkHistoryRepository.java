package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.WalkHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


public interface WalkHistoryRepository extends JpaRepository<WalkHistory,Long> {

    @Query("select w from WalkHistory w where w.localDate >= :#{#localDate} AND w.user.id = :#{#userId}")
    Page<WalkHistory> findAllByUserId(LocalDate localDate, @Param("userId") Long userId, PageRequest pageable);
}
