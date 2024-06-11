package com.play.music.song;
import java.util.Collections;
import com.play.music.album.AlbumService;
import com.play.music.artist.ArtistService;
import com.play.music.genre.GenreService;
import com.play.music.models.*;
import com.play.music.playlist.PlaylistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SongController {

    private final SongService songService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;

    private final PlaylistService playlistService;

    public SongController(SongService songService, AlbumService albumService, ArtistService artistService, GenreService genreService, PlaylistService playlistService) {
        this.songService = songService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.genreService = genreService;
        this.playlistService = playlistService;
    }

    @RequestMapping("/")
    public String getHomepage(Model model){
        List<Song> TempListSong = songService.getAllSongs();
        List<Album> TempListAlbum = albumService.findAll();
        List<Artist> TempListArtist = artistService.findAll();
        List<Genre> TempListGenre = genreService.findAll();


        List<Song> ListNewSong = new ArrayList<>();
        List<Song> ListHotSong = new ArrayList<>();
        List<Album> ListAlbum = new ArrayList<>();
        List<Album> ListHotAlbum = new ArrayList<>();
        List<Artist> ListArtist = new ArrayList<>();
        List<Genre> ListGenre = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ListNewSong.add(TempListSong.get(i));
        }

        for (int i = 0; i < 3; i++) {
            ListAlbum.add(TempListAlbum.get(i));
        }

        for (int i = 0; i < 6; i++) {
            ListArtist.add(TempListArtist.get(i));
        }

        for (int i = 0; i < 3; i++) {
            ListGenre.add(TempListGenre.get(i));
        }
        Collections.shuffle(TempListSong);
        Collections.shuffle(TempListAlbum);
        for (int i = 0; i < 5; i++) {
            ListHotAlbum.add(TempListAlbum.get(i));
            ListHotSong.add(TempListSong.get(i));
        }
        model.addAttribute("ListNewSong", ListNewSong);
        model.addAttribute("ListAlbum", ListAlbum);
        model.addAttribute("ListArtist", ListArtist);
        model.addAttribute("ListGenre", ListGenre);
        model.addAttribute("ListHotAlbum", ListHotAlbum);
        model.addAttribute("ListHotSong", ListHotSong);

        return "index";
    }
//    @GetMapping("/playSong/{id}")
//    @ResponseBody
//    public Song getPlaySong(@PathVariable(name = "id") Integer id) {
//        return songService.getSong(id);
//    }
}
