package com.play.music.playlist;

import com.play.music.models.Playlist;
import com.play.music.models.Song;
import com.play.music.models.User;
import com.play.music.repository.PlaylistRepository;
import com.play.music.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    public Playlist find(Integer id) {
       Optional<Playlist> playlist = playlistRepository.findById(id);

       if (!playlist.isPresent()) {
           throw new NoSuchElementException("Playlist not found");
       }

       return playlist.get();
    }

    public void removeSong(Integer playlistId, Integer songId) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        if (!playlistOptional.isPresent()) {
            throw new NoSuchElementException("Playlist not found");
        }

        Playlist playlist = playlistOptional.get();

        Optional<Song> songOptional = songRepository.findById(songId);
        if (!songOptional.isPresent()) {
            throw new NoSuchElementException("Song not found");
        }

        Song song = songOptional.get();

        if (playlist.getSongs().contains(song)) {
            playlist.getSongs().remove(song);
            playlistRepository.save(playlist);
        }
    }

    public void addSong(Integer playlistId, Integer songId) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        if (!playlistOptional.isPresent()) {
            throw new NoSuchElementException("Playlist not found");
        }

        Playlist playlist = playlistOptional.get();

        Optional<Song> songOptional = songRepository.findById(songId);
        if (!songOptional.isPresent()) {
            throw new NoSuchElementException("Song not found");
        }

        Song song = songOptional.get();

        if (playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
        }
    }

    public void savePlaylist(Playlist playlist, User user) {
        playlist.setUser(user);
        playlistRepository.save(playlist);
    }

    public void removePlaylist(Integer id) {
        Playlist playlist = playlistRepository.findById(id).get();

        playlistRepository.delete(playlist);
    }
}
