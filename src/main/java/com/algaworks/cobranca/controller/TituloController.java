package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.TituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private final String CADASTRO_VIEW = "CadastroTitulo"; 
	
	@Autowired
	private TituloService tituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(new Titulo());
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		String mensagem = "Título salvo com sucesso!";
		if (!(titulo.getCodigo() == null)) {
			mensagem = "Título editado com sucesso!";
		}
		
		try {
			this.tituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", mensagem);
			return "redirect:/titulos/novo";
		}catch (DataIntegrityViolationException e) {
			result.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		List<Titulo> titulos = tituloService.filtrar(filtro);
		
		ModelAndView view = new ModelAndView("PesquisaTitulos");
		view.addObject("titulos", titulos);
		
		return view;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(titulo);
		
		return view;
	}
	
	@RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		this.tituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluido com sucesso!");
		
		return "redirect:/titulos";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return this.tituloService.receber(codigo);
	}
	
	@ModelAttribute("statusTitulo")
	public List<StatusTitulo> statusEnum(){
		return Arrays.asList(StatusTitulo.values());
	}
}
