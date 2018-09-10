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
		float price = Float.parseFloat(request.getParameter("price"));
		String plu = request.getParameter("plu");
		boolean fs;
		boolean pLb;
		
		if (request.getParameter("pLb").equals("true")) {
			pLb = true;
		}
		else {
			pLb = false;
		}
		
		if (request.getParameter("fs").equals("true")) {
			fs = true;
		}
		else {
			fs = false;
		}
		
		Item i = new Item(name,price, pLb, plu, fs);
		
		
		itemDao.save(i);
		return "redirect:addtodb";
	}
	
	
	@RequestMapping(value="/error", method = RequestMethod.GET)
	public String error () {
		return "error";
	}
}
