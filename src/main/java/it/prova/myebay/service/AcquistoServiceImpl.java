package it.prova.myebay.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.exception.AnnuncioChiusoException;
import it.prova.myebay.exception.CreditoInsufficienteException;
import it.prova.myebay.exception.UtenteNotFoundException;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.acquisto.AcquistoRepository;

@Service
public class AcquistoServiceImpl implements AcquistoService {

	
	@Autowired
	AcquistoRepository acquistoRepository;
	
	@Autowired
	AnnuncioService annuncioService;
	
	@Autowired
	UtenteService utenteService;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Acquisto> gestioneAcquisti(String username) {
		
		return acquistoRepository.findAllByUtente_Username(username);
	}

	@Override
	@Transactional(readOnly = true)
	public Acquisto caricaSingoloElemento(Long id) {
		
		return acquistoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void registraAcquisto(Long idAnnuncio) {
		Acquisto acquistoInstance = new Acquisto();
		
		Annuncio annuncioFromDb = annuncioService.caricaSingoloElemento(idAnnuncio);
		if (!annuncioFromDb.isAperto()) {
			throw new AnnuncioChiusoException();
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);
		if (utenteLoggato == null) {
			throw new UtenteNotFoundException();
		}
		
		if (utenteLoggato.getCreditoResiduo() == null ||utenteLoggato.getCreditoResiduo() < annuncioFromDb.getPrezzo()) {
			throw new CreditoInsufficienteException();
		}
		
		Utente venditore = annuncioFromDb.getUtente();
		if (venditore.getCreditoResiduo() == null) {
			venditore.setCreditoResiduo(0D);
		}
		
		Double nuovoCreditoUtenteAcquirente = utenteLoggato.getCreditoResiduo() - annuncioFromDb.getPrezzo();
		Double nuovoCreditoUtenteVenditore = venditore.getCreditoResiduo() + annuncioFromDb.getPrezzo();
		
		utenteLoggato.setCreditoResiduo(nuovoCreditoUtenteAcquirente);
		venditore.setCreditoResiduo(nuovoCreditoUtenteVenditore);
		annuncioFromDb.setAperto(false);
		
		acquistoInstance.setDataAcquisto(LocalDate.now());
		acquistoInstance.setDescrizione(annuncioFromDb.getTestoAnnuncio());
		acquistoInstance.setPrezzo(annuncioFromDb.getPrezzo());
		acquistoInstance.setUtente(utenteLoggato);
		
		acquistoRepository.save(acquistoInstance);
	}

	@Override
	public List<Acquisto> listAll() {
		
		return (List<Acquisto>) acquistoRepository.findAll();
	}

}
