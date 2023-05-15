package it.prova.myebay.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;

public class AnnuncioDTO {

	private Long id;

	@NotBlank(message = "{testoAnnuncio.notblank}")
	private String testoAnnuncio;

	@NotNull(message = "{prezzoAnnuncio.notnull}")
	@Min(0)
	private Double prezzo;

	private LocalDate dataCreazione;

	private boolean aperto;

	private UtenteDTO utenteDTO;

	private Long[] categorieIds;

	public AnnuncioDTO() {
		super();
	}

	public AnnuncioDTO(Long id, @NotBlank(message = "{testoAnnuncio.notblank}") String testoAnnuncio,
			@NotNull(message = "{prezzoaannuncio.notnull}") @Min(0) Double prezzo, LocalDate dataCreazione,
			 UtenteDTO utenteDTO) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.dataCreazione = dataCreazione;
		this.utenteDTO = utenteDTO;
	}

	public AnnuncioDTO(Long id, @NotBlank(message = "{testoAnnuncio.notblank}") String testoAnnuncio,
			@NotNull(message = "{prezzoaannuncio.notnull}") @Min(0) Double prezzo, LocalDate dataCreazione,
			boolean aperto,UtenteDTO utenteDTO, Long[] categorieIds) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.dataCreazione = dataCreazione;
		this.aperto = aperto;
		this.utenteDTO = utenteDTO;
		this.categorieIds = categorieIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestoAnnuncio() {
		return testoAnnuncio;
	}

	public void setTestoAnnuncio(String testoAnnuncio) {
		this.testoAnnuncio = testoAnnuncio;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public boolean isAperto() {
		return aperto;
	}

	public void setAperto(boolean aperto) {
		this.aperto = aperto;
	}

	public UtenteDTO getUtenteDTO() {
		return utenteDTO;
	}

	public void setUtenteDTO(UtenteDTO utenteDTO) {
		this.utenteDTO = utenteDTO;
	}

	public Long[] getCategorieIds() {
		return categorieIds;
	}

	public void setCategorieIds(Long[] categorieIds) {
		this.categorieIds = categorieIds;
	}

	public Annuncio buildAnnuncioModel(boolean aperto, boolean includesCategories) {
		Annuncio result = new Annuncio(this.id, this.testoAnnuncio, this.prezzo, this.dataCreazione, this.utenteDTO.buildUtenteModel(false));
		if (includesCategories && categorieIds != null) {
			result.setCategorie(
					Arrays.asList(categorieIds).stream().map(id -> new Categoria(id)).collect(Collectors.toSet()));
		}
		if (aperto) {
			result.setAperto(true);
		}
		return result;
	}

	public static AnnuncioDTO buildAnnuncioDTOFromModel(Annuncio annuncioModel, boolean includesCategorie) {

		AnnuncioDTO result = new AnnuncioDTO(annuncioModel.getId(), annuncioModel.getTestoAnnuncio(),
				annuncioModel.getPrezzo(), annuncioModel.getDataCreazione(),UtenteDTO.buildUtenteDTOFromModel(annuncioModel.getUtente(), false));

		if (includesCategorie && !annuncioModel.getCategorie().isEmpty()) {
			result.categorieIds = annuncioModel.getCategorie().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});
		}
		if (annuncioModel.isAperto()) {
			result.setAperto(true);
		} else {
			result.setAperto(false);
		}
		return result;
	}

	public static List<AnnuncioDTO> createAnnuncioDTOListFromModelList(List<Annuncio> modelListInput,
			boolean includesCategorie) {
		return modelListInput.stream().map(annuncioEntity -> {
			return AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioEntity, includesCategorie);
		}).collect(Collectors.toList());
	}

	public static Set<AnnuncioDTO> createAnnuncioDTOSetFromModelList(Set<Annuncio> modelListInput,
			boolean includesCategorie) {
		return modelListInput.stream().map(annuncioEntity -> {
			return AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioEntity, includesCategorie);
		}).collect(Collectors.toSet());
	}
	
	public static Set<Annuncio> createAnnuncioModelSetFromDTOSet(Set<AnnuncioDTO> dtoSetInput,boolean includesCategorie) {
		return dtoSetInput.stream().map(dtoEntity -> {
			return dtoEntity.buildAnnuncioModel(true, includesCategorie);
		}).collect(Collectors.toSet());
	}
		
	
}
