package com.play.music.song;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SongController {
    @RequestMapping("/")
    public String getHomepage(){
        return "index";
    }
}
