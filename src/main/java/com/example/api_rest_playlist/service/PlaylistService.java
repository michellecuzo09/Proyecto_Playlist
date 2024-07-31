package com.example.api_rest_playlist.service;

import com.example.api_rest_playlist.model.Playlist;

import java.util.List;

public interface PlaylistService {

    Playlist crearPlaylist(Playlist playlist);
    List<Playlist> getAllPlaylists();
    Playlist getPlaylistByName(String name);
    Playlist actualizarPlaylist(String name, Playlist playlist);
    void eliminarPlaylist(String name);
}
