package com.play.music.song;

import com.play.music.models.Song;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongRestApi {
    private final SongService songService;

    public SongRestApi(SongService songService) {
        this.songService = songService;
    }
    @GetMapping("/playSong/{id}")
    @ResponseBody
    public PlaySong  getPlaySong(@PathVariable(name = "id") Integer id) {
        Song song=songService.getSong(id);
        PlaySong pl=new PlaySong(song.getId(),song.getTitle(),song.getArtist().getArtistName(),song.getSongUrl(),song.getArtist().getAvatarUrl());
        return pl;
    }
}
