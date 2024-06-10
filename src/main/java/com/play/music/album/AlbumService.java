package com.play.music.album;

import com.play.music.models.Album;
import com.play.music.repository.AlbumRepository;
import com.play.music.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    public List<Album> findAll() {
        return albumRepository.findAll();
    }
}
