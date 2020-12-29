package com.algaworks.cobranca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.TituloRepository;

@Service
public class TituloService {

	@Autowired
	private TituloRepository tituloRepository;
	
	public Titulo salvar(Titulo titulo) {
		return this.tituloRepository.save(titulo);
	}
	
	public List<Titulo> listarTodos(){
		return this.tituloRepository.findAll();
	}

	public Optional<Titulo> pesquisarId(Long codigo) {
		return this.tituloRepository.findById(codigo);
	}

	public Titulo atualizar(Titulo titulo) {
		return this.tituloRepository.save(titulo);
	}
	
}
