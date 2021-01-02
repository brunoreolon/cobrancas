package com.algaworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.TituloRepository;

@Service
public class TituloService {

	@Autowired
	private TituloRepository tituloRepository;
	
	public void salvar(Titulo titulo) {
		try {
			this.tituloRepository.save(titulo);
			
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
	
	public List<Titulo> listarTodos(){
		return this.tituloRepository.findAll();
	}

	public Titulo atualizar(Titulo titulo) {
		return this.tituloRepository.save(titulo);
	}

	public void excluir(Long codigo) {
		this.tituloRepository.deleteById(codigo);
	}
	
}
