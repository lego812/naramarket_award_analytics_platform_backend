package com.example.award.repository;

import com.example.award.domain.Award;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardRepository extends JpaRepository<Award, Long> {
}
