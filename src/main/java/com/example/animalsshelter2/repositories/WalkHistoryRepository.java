package com.example.animalsshelter2.repositories;

import com.example.animalsshelter2.models.WalkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalkHistoryRepository extends JpaRepository<WalkHistory,Long> {

    List<WalkHistory> findAllByUserId(Long id);
}
