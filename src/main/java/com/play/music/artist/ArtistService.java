package com.play.music.artist;

import com.play.music.models.Album;
import com.play.music.models.Artist;
import com.play.music.repository.AlbumRepository;
import com.play.music.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }
}
