package com.play.music.album;

import com.play.music.models.Album;
import com.play.music.models.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/albums/{id}")
    public String getPlaylists(@PathVariable(name = "id") Integer id, Model model) {
        Album album = albumService.find(id);

        long songCount = album.getSongs().stream().count();
        model.addAttribute("album", album);
        model.addAttribute("songCount", songCount);

        return "album/single_album";
    }
}
