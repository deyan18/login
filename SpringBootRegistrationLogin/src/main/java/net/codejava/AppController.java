package net.codejava;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sun.security.auth.UserPrincipal;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		userRepo.save(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}


	@RequestMapping("/showPerfil/{email}")
	public ModelAndView showPerfil(@PathVariable(name = "email") String email) {
		User user  = userRepo.findByEmail(email);
		ModelAndView mav = new ModelAndView("perfil");
		mav.addObject("user", user);
		return mav;

	}


	@PostMapping("/processPerfil")
	public String procesarPerfil(User user) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);		
		
// 		userRepo.save(user);
		
		return "user_updated_success";
	}
	
	@RequestMapping("/edit/{email}")
	public ModelAndView showEditUserPage(@PathVariable(name = "email") String email) {
	    ModelAndView mav = new ModelAndView("edit_product");
	    User user  = userRepo.findByEmail(email);
	    userRepo.save(user);
	    
	    return mav;
	}
}