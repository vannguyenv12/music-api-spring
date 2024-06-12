package com.play.music.playlist;

import com.play.music.models.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
public class PlaylistRestController {
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/playlists-rest")
    public List<Playlist> getAllPlaylists() {
        return playlistService.findAll();
    }

//    @GetMapping("/playlists/add/{playlistId}/add-song/{songId}")
//    public String addSong(@PathVariable(name = "playlistId") Integer playlistId,
//                          @PathVariable(name = "songId") Integer songId,
//                          @RequestHeader(value = "Referer", required = false) String referer,
//                          RedirectAttributes redirectAttributes) {
//
//        playlistService.addSong(playlistId, songId);
//        return "redirect:" + referer;
//    }
}
