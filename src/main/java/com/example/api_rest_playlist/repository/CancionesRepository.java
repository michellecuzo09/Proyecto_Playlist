package com.example.api_rest_playlist.repository;

import com.example.api_rest_playlist.model.Canciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancionesRepository extends JpaRepository<Canciones,Long> {
}
