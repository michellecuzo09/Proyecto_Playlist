package com.example.api_rest_playlist.controller;

import com.example.api_rest_playlist.model.Playlist;
import com.example.api_rest_playlist.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearPlaylist(@RequestBody Playlist playlist) {
        String name = playlist.getName();

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de la playlist no puede ser nulo o estar vacío");
        }

        if (!name.matches("[a-zA-Z ]+")) {
            return ResponseEntity.badRequest().body("El nombre de la playlist solo puede contener letras y espacios");
        }

        try {
            Playlist createdPlaylist = playlistService.crearPlaylist(playlist);

            String encodedName = URLEncoder.encode(createdPlaylist.getName(), StandardCharsets.UTF_8.toString());

            return ResponseEntity.created(URI.create("/playlists/" + encodedName)).body("Lista de reproducción creada con éxito");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al codificar la URI");
        }
    }

    @GetMapping("/lists")
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        if (playlists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String listName) {
        Playlist playlist = playlistService.getPlaylistByName(listName);
        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(playlist);
    }

    @PutMapping("/{listName}")
    public ResponseEntity<String> actualizarPlaylist(@PathVariable String listName, @RequestBody Playlist playlist) {
        try {
            if (!listName.matches("[a-zA-Z ]+")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de la playlist solo puede contener letras y espacios.");
            }
            playlistService.actualizarPlaylist(listName, playlist);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede cambiar el nombre de la playlist.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist no encontrada.");
        }
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<String> eliminarPlaylist(@PathVariable String listName) {
        Playlist playlist = playlistService.getPlaylistByName(listName);
        if (playlist != null) {
            playlistService.eliminarPlaylist(listName);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist no encontrada.");
    }
}