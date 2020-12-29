package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.service.TituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private TituloService tituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView view = new ModelAndView("CadastroTitulo");
		
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo) {
		this.tituloService.salvar(titulo);
		
		ModelAndView view = new ModelAndView("CadastroTitulo");
		view.addObject("mensagem", "Título salvo com sucesso!");
		
		return view;
	}
	
	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> titulos = tituloService.listarTodos();
		
		ModelAndView view = new ModelAndView("PesquisaTitulos");
		view.addObject("titulos", titulos);
		
		return view;
	}
	
	@ModelAttribute("statusTitulo")
	public List<StatusTitulo> statusEnum(){
		return Arrays.asList(StatusTitulo.values());
	}
}
