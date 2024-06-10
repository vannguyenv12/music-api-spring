package com.play.music.genre;

import com.play.music.models.Artist;
import com.play.music.models.Genre;
import com.play.music.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

}
