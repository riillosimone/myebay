package it.prova.myebay.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.exception.AccessDeniedException;
import it.prova.myebay.exception.AnnuncioChiusoException;
import it.prova.myebay.exception.UtenteNotFoundException;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.annuncio.AnnuncioRepository;

@Service
public class AnnuncioServiceImpl implements AnnuncioService {

	@Autowired
	private AnnuncioRepository annuncioRepository;

	@Autowired
	private UtenteService utenteService;

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> listAll() {
		return annuncioRepository.findAllByApertoTrue();
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Annuncio annuncioFromDb = this.caricaSingoloElemento(annuncioInstance.getId());
		if (auth != null) {

			String username = auth.getName();
			Utente utenteFromDb = utenteService.findByUsername(username);
			if (utenteFromDb == null) {
				throw new UtenteNotFoundException();
			}

			Utente proprietario = utenteService.findByUsername(annuncioFromDb.getUtente().getUsername());
			if (proprietario != utenteFromDb) {
				throw new AccessDeniedException();
			}
			if (!annuncioInstance.isAperto()) {
				throw new AnnuncioChiusoException();
			}

			annuncioInstance.setAperto(true);
			annuncioInstance.setDataCreazione(annuncioFromDb.getDataCreazione());
			annuncioInstance.setUtente(annuncioFromDb.getUtente());
			annuncioRepository.save(annuncioInstance);
		}
	}

	@Override
	@Transactional
	public void inserisciNuovo(Annuncio annuncioInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteFromDb = utenteService.findByUsername(username);

		if (utenteFromDb == null)
			throw new UtenteNotFoundException();

		annuncioInstance.setUtente(utenteFromDb);
		annuncioInstance.setAperto(true);
		annuncioInstance.setDataCreazione(LocalDate.now());
		annuncioRepository.save(annuncioInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idAnnuncio) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteFromDb = utenteService.findByUsername(username);
		Annuncio annuncioFromDB = this.caricaSingoloElemento(idAnnuncio);
		if (utenteFromDb == null)
			throw new UtenteNotFoundException();
		if (!annuncioFromDB.isAperto()) {
			throw new AnnuncioChiusoException();
		}
		if (annuncioFromDB.getUtente() != utenteFromDb) {
			throw new RuntimeException();
		}
		annuncioRepository.deleteById(idAnnuncio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> findByExampleRicerca(Annuncio example) {
		return annuncioRepository.findByExampleRicerca(example);
	}

	@Override
	public Annuncio caricaElementoConUtente(Long id) {
		return annuncioRepository.findByIdConUtente(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> gestioneAnnunci() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return annuncioRepository.findAllByUtente_Username(username);
	}

}
