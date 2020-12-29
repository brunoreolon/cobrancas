package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		view.addObject(new Titulo());
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Valid Titulo titulo, Errors e, RedirectAttributes attributes) {
		if (e.hasErrors()) {
			return "CadastroTitulo";
		}
		
		this.tituloService.salvar(titulo);
		attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
		
		return "redirect:/titulos/novo";
	}
	
	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> titulos = tituloService.listarTodos();
		
		ModelAndView view = new ModelAndView("PesquisaTitulos");
		view.addObject("titulos", titulos);
		
		return view;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView atualizar(@PathVariable("codigo") Titulo titulo) {
		ModelAndView view = new ModelAndView("CadastroTitulo");
		view.addObject(titulo);
		
		return view;
	}
	
	@ModelAttribute("statusTitulo")
	public List<StatusTitulo> statusEnum(){
		return Arrays.asList(StatusTitulo.values());
	}
}
