package com.example.api_rest_playlist.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Set<Canciones> songs;

    public Playlist() {
    }

    public Playlist(Long id, String name, String description, Set<Canciones> songs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Canciones> getSongs() {
        return songs;
    }

    public void setSongs(Set<Canciones> songs) {
        this.songs = songs;
    }
}
