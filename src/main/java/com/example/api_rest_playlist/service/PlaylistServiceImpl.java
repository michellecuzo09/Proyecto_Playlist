package com.example.api_rest_playlist.service;

import com.example.api_rest_playlist.model.Playlist;
import com.example.api_rest_playlist.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public Playlist crearPlaylist(Playlist playlist) {
        if (playlist.getName() == null || playlist.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la playlist no puede ser nulo o estar vacío");
        }
        return playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist getPlaylistByName(String name) {
        return playlistRepository.findByName(name);
    }

    @Override
    public Playlist actualizarPlaylist(String name, Playlist playlist) {
        Playlist existingPlaylist = playlistRepository.findByName(name);
        if (existingPlaylist == null) {
            throw new RuntimeException("Playlist no encontrada");
        }
        if (!name.equals(playlist.getName())) {
            throw new IllegalArgumentException("No se puede cambiar el nombre de la playlist");
        }
        existingPlaylist.setDescription(playlist.getDescription());
        existingPlaylist.setSongs(playlist.getSongs());
        return playlistRepository.save(existingPlaylist);
    }

    @Override
    public void eliminarPlaylist(String name) {
        Playlist playlist = playlistRepository.findByName(name);
        if (playlist != null) {
            playlistRepository.delete(playlist);
        }
    }
}