package com.play.music.song;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminSongController {
    @RequestMapping("/admin")
    public String getHomepage(){
        return "admin/index";
    }

    @RequestMapping("/add-song")
    public String addSongPage() {return "admin/form";}

    @RequestMapping("/show-songs")
    public String displaySongsPage() {return "admin/songs_table";}
}
