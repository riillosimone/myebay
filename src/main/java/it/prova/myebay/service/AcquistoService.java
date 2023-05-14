package it.prova.myebay.service;

import java.util.List;

import it.prova.myebay.model.Acquisto;

public interface AcquistoService {

	public List<Acquisto> listAll();
	
	public List<Acquisto> gestioneAcquisti(String username);
	
	public Acquisto caricaSingoloElemento(Long id);
	
	public void registraAcquisto (Long idAnnuncio);
	
}
