package com.gfike.SelfCheckoutSim;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
public class SelfCheckoutController {
	@Autowired
	ItemDao itemDao;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index(){
		
		return "index";
	}
	
	
	@RequestMapping(value= {"/addtodb", "redirect:addtodb"}, method = RequestMethod.GET)
	public String CreateNewItemGet () {
//		String msg = " ";
//		model.addAttribute("msg", msg);
		return "addtodb";
	}
	
	@RequestMapping(value="/addtodb", method = RequestMethod.POST)
	public String CreateNewItemPost (HttpServletRequest request) {
		
		//get values from form
		String name = request.getParameter("name");
		float price = Integer.parseInt(request.getParameter("price"));
		boolean pLb = Boolean.parseBoolean(request.getParameter("plb"));
		int plu = Integer.parseInt(request.getParameter("plu"));
		boolean fs = Boolean.parseBoolean(request.getParameter("fs"));
		
		Item i = new Item(name, price, pLb, plu, fs);
		
		itemDao.save(i);
//		Item j = itemDao.findById(i.getUid());
//		String msg = " ";
//		
//		if (j != null) {
//			msg = "Item " + i.getName() + " was succesfully added!";
//		}
//		else
//			msg = "Item " + i.getName() + " was not added.";
//		HttpSession session = request.getSession();
//		session.setAttribute("item", i);
		return "redirect:addtodb";
	}
	
	
	@RequestMapping(value="/error", method = RequestMethod.GET)
	public String error () {
		return "error";
	}
}
