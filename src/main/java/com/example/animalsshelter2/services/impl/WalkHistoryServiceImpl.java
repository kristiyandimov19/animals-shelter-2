package com.example.animalsshelter2.services.impl;

import com.example.animalsshelter2.models.WalkHistory;
import com.example.animalsshelter2.repositories.WalkHistoryRepository;
import com.example.animalsshelter2.services.WalkHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WalkHistoryServiceImpl implements WalkHistoryService {

    private final WalkHistoryRepository walkHistoryRepository;

    public WalkHistoryServiceImpl(WalkHistoryRepository walkHistoryRepository) {
        this.walkHistoryRepository = walkHistoryRepository;
    }

    @Override
    public Page<WalkHistory> findByUserId(Long id, int page) {
        PageRequest requestPage = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"id"));
        LocalDate ld = LocalDate.now().minusMonths(3);
        return walkHistoryRepository.findAllByUserId(ld, id,requestPage);
    }
}
