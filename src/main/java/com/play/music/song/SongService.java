package com.play.music.song;

import com.play.music.models.Song;
import com.play.music.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSong(Integer id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (!songOptional.isPresent()) {
            throw new NoSuchElementException("Song not found");
        }

        return songOptional.get();
    }

    public void saveSong(Song song) {
        if (song.getId() == 0) {
            songRepository.save(song);
        } else {
            Optional<Song> songOptional = songRepository.findById(song.getId());

            if (songOptional.isPresent()) {
                Song findSong = songOptional.get();
                findSong.setTitle(song.getTitle());
               findSong.setSongUrl(song.getSongUrl());
               findSong.setAlbum(song.getAlbum());
               findSong.setArtist(song.getArtist());
               findSong.setGenre(song.getGenre());

               songRepository.save(findSong);

            }
        }
    }

    public void deleteSong(Integer id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (!songOptional.isPresent()) {
            throw new NoSuchElementException("Song not found");
        }

        songRepository.delete(songOptional.get());
    }
}
