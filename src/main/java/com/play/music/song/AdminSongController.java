package com.play.music.song;

import com.play.music.models.Album;
import com.play.music.models.Artist;
import com.play.music.models.Genre;
import com.play.music.models.Song;
import com.play.music.repository.AlbumRepository;
import com.play.music.repository.ArtistRepository;
import com.play.music.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminSongController {
    private final SongService songService;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;

    @RequestMapping("/admin")
    public String getHomepage(){
        return "admin/index";
    }

    @GetMapping("/add-song")
    public String addSongPage(Model model) {
        List<Album> albums = albumRepository.findAll();
        List<Artist> artists = artistRepository.findAll();
        List<Genre> genres = genreRepository.findAll();


        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("genres", genres);
        model.addAttribute("song", new Song());

        return "admin/form";
    }

    @GetMapping("/update-song/{id}")
    public String updateSongPage(@PathVariable(name = "id") Integer id, Model model) {
        List<Album> albums = albumRepository.findAll();
        List<Artist> artists = artistRepository.findAll();
        List<Genre> genres = genreRepository.findAll();

        Song song = songService.getSong(id);

        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("genres", genres);
        model.addAttribute("song", song);

        return "admin/form";
    }

    @RequestMapping("/show-songs")
    public String displaySongsPage(Model model) {

        List<Song> songs = songService.getAllSongs();

        model.addAttribute("songs", songs);

        return "admin/songs_table" ;
    }

    @PostMapping("/save-song")
    public String saveSong(Song song, @RequestParam("file") MultipartFile file, RedirectAttributes ra) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                ra.addFlashAttribute("message", "Please select a file to upload.");
                return "redirect:/add-song";
            }

            // Validate file type
            if (!file.getContentType().equals("audio/mpeg")) {
                ra.addFlashAttribute("message", "Invalid file type. Please upload an MP3 file.");
                return "redirect:/add-song";
            }

            String uploadDir = "uploads";
            // Create the directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filename = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);
            Files.write(filePath, file.getBytes());

            song.setSongUrl(filePath.toString());

            songService.saveSong(song);

            ra.addFlashAttribute("message", "The song was save successfully!");
        } catch (IOException e) {
            ra.addFlashAttribute("message", "An error occurred while uploading the song!");
            e.printStackTrace();
        }
        return "redirect:/show-songs";
    }

    @GetMapping("/delete-song/{id}")
    public String deleteSong(@PathVariable(name = "id") Integer id, RedirectAttributes ra) {
        songService.deleteSong(id);
        ra.addFlashAttribute("message", "The song was removed");
        return "redirect:/show-songs";
    }
}
