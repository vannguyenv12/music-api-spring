package com.play.music.playlist;

import com.play.music.models.Playlist;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@RestController
public class PlaylistRestController {
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/playlists-rest")
    public Set<Playlist> getAllPlaylists(HttpSession session) {
        Set<Playlist> playlists = playlistService.findAll((String) session.getAttribute("username"));

        return playlists;
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
