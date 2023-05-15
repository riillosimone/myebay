package it.prova.myebay.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.myebay.model.Acquisto;

public class AcquistoDTO {


	private Long id;
	
	private String descrizione;
	
	private LocalDate dataAcquisto;
	
	private Double prezzo;
	
	private UtenteDTO utenteDTO;

	public AcquistoDTO(Long id, String descrizione, LocalDate dataAcquisto, Double prezzo, UtenteDTO utenteDTO) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataAcquisto = dataAcquisto;
		this.prezzo = prezzo;
		this.utenteDTO = utenteDTO;
	}

	public AcquistoDTO(Long id, String descrizione, LocalDate dataAcquisto, Double prezzo) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataAcquisto = dataAcquisto;
		this.prezzo = prezzo;
	}

	public AcquistoDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDate dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public UtenteDTO getUtenteDTO() {
		return utenteDTO;
	}

	public void setUtenteDTO(UtenteDTO utenteDTO) {
		this.utenteDTO = utenteDTO;
	}
	
	public Acquisto buildAcquistoModel () {
		Acquisto result = new Acquisto(this.id, this.descrizione, this.dataAcquisto, this.prezzo, this.utenteDTO.buildUtenteModel(false));
		return result;
	}
	
	
	public static AcquistoDTO buildAcquistoDTOFromModel (Acquisto model) {
		AcquistoDTO result = new AcquistoDTO(model.getId(), model.getDescrizione(), model.getDataAcquisto(), model.getPrezzo(), UtenteDTO.buildUtenteDTOFromModel(model.getUtente(), false));
		return result;
		
	}
	
	public static List<AcquistoDTO> createAcquistoDTOListFromModelList(List<Acquisto> modelListInput) {
		return modelListInput.stream().map(acquistoEntity -> {
			return AcquistoDTO.buildAcquistoDTOFromModel(acquistoEntity);
		}).collect(Collectors.toList());
	}
	
	public static Set<AcquistoDTO> createAcquistoDTOSetFromModelSet(Set<Acquisto> modelListInput) {
		return modelListInput.stream().map(acquistoEntity -> {
			return AcquistoDTO.buildAcquistoDTOFromModel(acquistoEntity);
		}).collect(Collectors.toSet());
	}
	
	
	public static Set<Acquisto> createAcquistoModelSetFromDTOSet(Set<AcquistoDTO> dtoSetInput) {
		return dtoSetInput.stream().map(dtoEntity -> {
			return dtoEntity.buildAcquistoModel();
		}).collect(Collectors.toSet());
	}
	
}
