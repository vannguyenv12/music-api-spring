package com.play.music.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "songs")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String songUrl;

    @ManyToOne
    private Genre genre;

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Album album;

    @ManyToMany(mappedBy = "songs", cascade = CascadeType.ALL)
    Set<Playlist> playlists = new HashSet<>();

    public void addPlayList(Playlist playlist) {
        this.playlists.add(playlist);
        playlist.getSongs().add(this);
    }

    public void removePlayList(Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.getSongs().remove(this);
    }

}
