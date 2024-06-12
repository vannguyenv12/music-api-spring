package com.play.music.album;

import com.play.music.models.Album;
import com.play.music.models.Playlist;
import com.play.music.repository.AlbumRepository;
import com.play.music.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    public List<Album> findAll() {
        return albumRepository.findAll();
    }
    public Album find(Integer id) {
        Optional<Album> album = albumRepository.findById(id);

        if (!album.isPresent()) {
            throw new NoSuchElementException("Playlist not found");
        }

        return album.get();
    }
}
