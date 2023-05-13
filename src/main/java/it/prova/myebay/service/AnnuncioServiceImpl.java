package it.prova.myebay.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.exception.AnnuncioChiusoException;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.annuncio.AnnuncioRepository;
import it.prova.myebay.repository.utente.UtenteRepository;


@Service
public class AnnuncioServiceImpl implements AnnuncioService{

	
	@Autowired
	private AnnuncioRepository annuncioRepository;
	
	@Autowired
	private UtenteService utenteService;
	
	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> listAll() {
		return (List<Annuncio>) annuncioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio caricaSingoloElemento(Long id) {
		return annuncioRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio caricaSingoloElementoConCategorie(Long id) {
		return annuncioRepository.findByIdConCategorie(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Annuncio annuncioInstance) {
		annuncioRepository.save(annuncioInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Annuncio annuncioInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteFromDb = utenteService.findByUsername(username);
		
		if (utenteFromDb == null)
		throw new RuntimeException("Elemento non trovato.");
		
		annuncioInstance.setUtente(utenteFromDb);
		annuncioInstance.setAperto(true);
		annuncioInstance.setDataCreazione(LocalDate.now());
		annuncioRepository.save(annuncioInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idAnnuncio) {
		Annuncio annuncioFromDB= this.caricaSingoloElemento(idAnnuncio);
		if (!annuncioFromDB.isAperto()) {
			throw new AnnuncioChiusoException();
		}
		annuncioRepository.deleteById(idAnnuncio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> findByExampleRicerca(Annuncio example) {
		return annuncioRepository.findByExampleRicerca(example);
	}

}
