package it.prova.myebay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/secured")
public class SecuredHomeController {

	@RequestMapping(value = { "/home", "" })
	public String home() {
		return "utente/index";
	}
}
