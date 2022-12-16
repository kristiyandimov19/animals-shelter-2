package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.WalkHistory;
import com.example.animalsshelter2.repositories.WalkHistoryRepository;
import com.example.animalsshelter2.services.WalkHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalkHistoryServiceImpl implements WalkHistoryService {

    private final WalkHistoryRepository walkHistoryRepository;

    public WalkHistoryServiceImpl(WalkHistoryRepository walkHistoryRepository) {
        this.walkHistoryRepository = walkHistoryRepository;
    }

    @Override
    public List<WalkHistory> findByUserId(Long id) {
        return walkHistoryRepository.findAllByUserId(id);
    }
}
