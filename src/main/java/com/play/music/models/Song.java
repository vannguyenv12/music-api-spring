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
    private String artistName;
    private String songUrl;
    private String imgUrl;

    @ManyToOne
    private Genre genre;

    @ManyToMany(mappedBy = "songs")
    Set<Playlist> playlists = new HashSet<>();
}
