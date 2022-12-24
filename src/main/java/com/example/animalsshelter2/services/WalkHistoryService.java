package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.WalkHistory;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface WalkHistoryService {

    Page<WalkHistory> findByUserId(Long id,int page) throws ParseException;
}
