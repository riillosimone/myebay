package it.prova.myebay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.service.UtenteService;

@Controller
@RequestMapping(value = "/utente")
public class RicaricaController {
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping("/ricarica/{utenteInPagina}")
	public String ricarica(@PathVariable(required = true) String utenteInPagina, Model model) {
		model.addAttribute("credito_utente_attr",
				UtenteDTO.buildUtenteDTOFromModel(utenteService.findByUsername(utenteInPagina),false));
		return "utente/ricarica";
	}

	@PostMapping("/ricarica")
	public String ricaricaCredito(Double creditoDaRicaricare, RedirectAttributes redirectAttrs) {

		utenteService.ricarica(creditoDaRicaricare);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/secured/home";
	}

}
