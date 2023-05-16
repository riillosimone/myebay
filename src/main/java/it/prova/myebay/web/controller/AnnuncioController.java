package it.prova.myebay.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.exception.AnnuncioChiusoException;
import it.prova.myebay.exception.UtenteNotFoundException;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.AnnuncioService;
import it.prova.myebay.service.CategoriaService;

@Controller
@RequestMapping("/annuncio")
public class AnnuncioController {

	@Autowired
	private AnnuncioService annuncioService;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		model.addAttribute("insert_annuncio_attr", new AnnuncioDTO());
		return "utente/annuncio/insert";
	}

	@PostMapping("/save")
	public String save(@Validated @ModelAttribute("insert_annuncio_attr") AnnuncioDTO annuncioDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			model.addAttribute("categorie_totali_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
			return "utente/annuncio/insert";
		}
		try {
			annuncioService.inserisciNuovo(annuncioDTO.buildAnnuncioModel(true, true));
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (UtenteNotFoundException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Utente non loggato.");
			return "redirect:/public/annuncio";
		}
		return "redirect:/public/annuncio";

	}

	@GetMapping("delete/{idAnnuncio}")
	public String deleteAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		Annuncio annuncioModel = annuncioService.caricaSingoloElementoConCategorie(idAnnuncio);
		AnnuncioDTO annuncioDTO = AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel, true);
		model.addAttribute("delete_annuncio_attr", annuncioDTO);
		model.addAttribute("categorie_totali_attr", annuncioModel.getCategorie());
		return "utente/annuncio/delete";
	}

	@PostMapping("/delete")
	public String delete(Long idAnnuncio, RedirectAttributes redirectAttrs) {
		try {
			annuncioService.rimuovi(idAnnuncio);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (AnnuncioChiusoException e) {
			redirectAttrs.addFlashAttribute("errorMessage",
					"Attenzione! L'annuncio che stai cercando di eliminare è già chiuso.");
			return "redirect:/public/annuncio";
		} catch (UtenteNotFoundException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Utente non loggato.");
			return "redirect:/public/annuncio";

		} catch (RuntimeException er) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Non puoi eliminare un annuncio non tuo.");
			return "redirect:/public/annuncio";
		}
		return "redirect:/public/annuncio";

	}

	@GetMapping("edit/{idAnnuncio}")
	public String editAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		Annuncio annuncioModel = annuncioService.caricaSingoloElementoConCategorie(idAnnuncio);
		AnnuncioDTO annuncioDTO = AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel, true);
		model.addAttribute("edit_annuncio_attr", annuncioDTO);
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		return "utente/annuncio/edit";
	}

	@PostMapping("/edit")
	public String edit(@Valid @ModelAttribute("edit_annuncio_attr") AnnuncioDTO annuncioDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			model.addAttribute("categorie_totali_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
			return "utente/annuncio/edit";
		}
		try {
			annuncioService.aggiorna(annuncioDTO.buildAnnuncioModel(true, true));
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (AnnuncioChiusoException e) {
			redirectAttrs.addFlashAttribute("errorMessage",
					"Attenzione! L'annuncio che stai cercando di modificare è già chiuso.");
			return "redirect:/public/annuncio";
		} catch (UtenteNotFoundException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Utente non loggato.");
			return "redirect:/public/annuncio";
		}

		return "redirect:/public/annuncio";
	}

	@GetMapping("/listaannunci/{utenteInPagina}")
	public String gestioneAnnunci(@PathVariable(required = true) String utenteInPagina, Model model) {
		model.addAttribute("annuncio_list_attr",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.gestioneAnnunci(utenteInPagina), false));
		return "public/annuncio/list";
	}

}
