package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Movie;

public interface MovieDao extends JpaRepository<Movie, Long> {

}
