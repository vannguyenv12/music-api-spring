package com.play.music.song;

import com.play.music.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SongController {
    @PersistenceContext
    private EntityManager entityManager;


    @RequestMapping("/")
    public ModelAndView getHomepage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            modelAndView.addObject("username", username);
        }
        modelAndView.setViewName("index");
        return modelAndView;
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
}
