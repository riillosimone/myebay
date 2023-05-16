package it.prova.myebay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.exception.UtenteNotFoundException;
import it.prova.myebay.service.UtenteService;

@Controller
@RequestMapping(value = "/utente")
public class RicaricaController {
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping("/ricarica")
	public String ricarica(Model model) {
		model.addAttribute("credito_utente_attr",
				UtenteDTO.buildUtenteDTOFromModel(utenteService.findByUsernameInPagina(),false));
		return "utente/ricarica";
	}

	@PostMapping("/ricarica")
	public String ricaricaCredito(Double creditoDaRicaricare, RedirectAttributes redirectAttrs) {

		try {
			utenteService.ricarica(creditoDaRicaricare);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (UtenteNotFoundException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Utente non loggato.");
			return "redirect:/secured/home";
		} catch (RuntimeException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Non puoi inserire un valore negativo");
			return "redirect:/utente/ricarica";
		}
		

		
		return "redirect:/secured/home";
	}

}
