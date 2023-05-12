package it.prova.myebay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.myebay.service.UtenteService;

@Controller
public class HomeController {

	@Autowired
	private UtenteService utenteService;

	@RequestMapping(value = { "/home", "" })
	public String home() {

		if (utenteService.isAutenticato()) {
			return "utente/index";
		} else

			return "public/index";
	}
}
