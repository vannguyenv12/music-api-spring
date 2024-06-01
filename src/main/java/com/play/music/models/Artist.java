package com.play.music.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artists")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String artistName;
    private String avatarUrl;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private Set<Album> albums = new HashSet<>();
}
