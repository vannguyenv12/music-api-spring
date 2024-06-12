package com.play.music.song;
import java.util.Collections;
import com.play.music.album.AlbumService;
import com.play.music.artist.ArtistService;
import com.play.music.genre.GenreService;
import com.play.music.models.*;
import com.play.music.playlist.PlaylistService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SongController {
    @PersistenceContext
    private EntityManager entityManager;

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
    public String getHomepage(Model model, HttpSession session){
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

        System.out.println("check session: " + session.getAttribute("username"));

        return "index";
    }

    @RequestMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @PostMapping("/loginAction")
    @Transactional
    public ModelAndView loginAction(@RequestParam String email, @RequestParam String password, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        User user = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (user != null) {
            String hashedInputPassword = HashPassword.hash(password);
            if (hashedInputPassword.equals(user.getPassword())) {
                String username = user.getUsername();
                session.setAttribute("username", username);

                session.setAttribute("role", user.getRole());


                if("admin".equals(user.getRole())) {
                    modelAndView.setViewName("redirect:/admin"); // Redirect to admin page if role is "admin"
                } else {
                    modelAndView.setViewName("redirect:/"); // Redirect to home page for other roles
                }
                return modelAndView;
            }
        }
        modelAndView.addObject("session", session);
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    @PostMapping("/signupAction")
    @Transactional
    public ModelAndView signUpAction(@RequestParam String email, @RequestParam String password, @RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView();

        // Kiểm tra người dùng đã tồn tại chưa
        User existingUser = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (existingUser != null) {
            // Người dùng đã tồn tại, trả về trang đăng ký với thông báo
            modelAndView.addObject("message", "Email này đã được sử dụng!");
            modelAndView.setViewName("signup");
            return modelAndView;
        }

        // Mã hóa mật khẩu
        String hashedPassword = HashPassword.hash(password);
        // Tạo người dùng mới
        User newUser = new User();
        newUser.setUsername(name);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);

        // Lưu người dùng mới vào cơ sở dữ liệu
        entityManager.persist(newUser);
        entityManager.flush();
        // Đăng ký thành công, chuyển hướng người dùng đến trang đăng nhập
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    };
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
//    @GetMapping("/playSong/{id}")
//    @ResponseBody
//    public Song getPlaySong(@PathVariable(name = "id") Integer id) {
//        return songService.getSong(id);
//    }
}
