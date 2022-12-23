package com.example.animalsshelter2.services;

import com.example.animalsshelter2.models.WalkHistory;

import java.text.ParseException;
import java.util.List;

public interface WalkHistoryService {

    List<WalkHistory> findByUserId(Long id) throws ParseException;
}
