package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.WalkHistory;
import com.example.animalsshelter2.models.views.WalkHistoryViewModel;

import java.util.List;

public interface WalkHistoryService {

    List<WalkHistory> findByUserId(Long id);
}
