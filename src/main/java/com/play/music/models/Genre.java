package com.play.music.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String genreName;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();
}
