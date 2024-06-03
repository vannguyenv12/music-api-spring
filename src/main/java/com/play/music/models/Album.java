package com.play.music.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String backgroundUrl;

    @ManyToOne
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();
}
