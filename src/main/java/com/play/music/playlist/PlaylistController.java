package com.play.music.playlist;

import com.play.music.models.Playlist;
import com.play.music.models.User;
import com.play.music.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class PlaylistController {
    private final PlaylistService playlistService;
    private final UserRepository userRepository;

    @GetMapping("/playlists")
    public String getPlaylists(Model model, HttpSession session) {
        Set<Playlist> playlists = playlistService.findAll((String) session.getAttribute("username"));

        model.addAttribute("playlist", new Playlist());
        model.addAttribute("playlists", playlists);

        return "playlist/index";
    }

    @GetMapping("/playlists/{id}")
    public String getPlaylists(@PathVariable(name = "id") Integer id, Model model) {
        Playlist playlist = playlistService.find(id);
        long songCount = playlist.getSongs().stream().count();
        model.addAttribute("playlist", playlist);
        model.addAttribute("songCount", songCount);

        return "playlist/single_playlist";
    }

    @GetMapping("/playlists/{playlistId}/remove-song/{songId}")
    public String removeSong(@PathVariable(name = "playlistId") Integer playlistId,
                             @PathVariable(name = "songId") Integer songId,
                             @RequestHeader(value = "Referer", required = false) String referer,
                             RedirectAttributes redirectAttributes) {

        playlistService.removeSong(playlistId, songId);

        return "redirect:" + referer;
    }

    @GetMapping("/playlists/add/{playlistId}/add-song/{songId}")
    public String addSong(@PathVariable(name = "playlistId") Integer playlistId,
                             @PathVariable(name = "songId") Integer songId,
                             @RequestHeader(value = "Referer", required = false) String referer,
                             RedirectAttributes redirectAttributes) {

        System.out.println("hello: " + playlistId + " " + songId);
        playlistService.addSong(playlistId, songId);

        return "redirect:" + referer;
    }

    @PostMapping("/playlists/save")
    public String savePlaylist(Playlist playlist,
                               @RequestHeader(value = "Referer", required = false) String referer,
                               HttpSession session
                               ) {

        User user = userRepository.findByUsername((String) session.getAttribute("username"));
        playlistService.savePlaylist(playlist, user);

        return "redirect:" + referer;
    }

    @GetMapping("/playlists/remove/{id}")
    public String removePlaylist(@PathVariable(name = "id") Integer id,
                               @RequestHeader(value = "Referer", required = false) String referer
    ) {

        playlistService.removePlaylist(id);
        return "redirect:" + referer;
    }

}
