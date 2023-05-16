package it.prova.myebay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.service.UtenteService;
import it.prova.myebay.validation.ValidationNoPassword;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping("/registrati")
	public String registrati(Model model) {
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(
			@Validated({ ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");

		if (result.hasErrors()) {
			return "utente/insert";
		}
		utenteService.registrati(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("infoMessage",
				"Sei stato registrato! Attendi che un admin abiliti il tuo account.");
		return "redirect:/login";
	}
}
