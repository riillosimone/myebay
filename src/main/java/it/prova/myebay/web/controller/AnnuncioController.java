package it.prova.myebay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.service.AnnuncioService;
import it.prova.myebay.service.CategoriaService;

@Controller
@RequestMapping("/annuncio")
public class AnnuncioController {

	@Autowired
	private AnnuncioService annuncioService;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ModelAndView listAllAnnunci() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("annuncio_list_attr",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.listAll(), false));
		mv.setViewName("annuncio/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchAnnuncio(Model model) {
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		model.addAttribute("search_annuncio_attr", new AnnuncioDTO());
		return "public/annuncio/search";
	}

	@PostMapping("/list")
	public String listAnnunci (AnnuncioDTO annuncioExample, ModelMap model) {
		model.addAttribute("annuncio_list_attr",AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.findByExampleRicerca(annuncioExample.buildAnnuncioModel(true, true)), true));
		return "public/annuncio/list";
	}
}
