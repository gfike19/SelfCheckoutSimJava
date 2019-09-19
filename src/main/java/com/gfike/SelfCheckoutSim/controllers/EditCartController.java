package com.gfike.SelfCheckoutSim.controllers;

import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EditCartController {

    @Autowired
    public ItemDao itemDao;

@RequestMapping(value ="/editCart", method = RequestMethod.GET)
    public String editCartGet(Model model,  HttpSession session){

        ArrayList<Item> cart;

        if (session.getAttribute("cart") != null){
            cart = (ArrayList<Item>)session.getAttribute("cart");
        }
        else{
            cart = new ArrayList<Item>();
        }

        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        List<Item> items = itemDao.findAll();
        model.addAttribute("items", items);

        String msg = (String)session.getAttribute("msg");
        model.addAttribute("msg", msg);
        return "editCart";
    }

    //TODO work on noting if an item has duplicates
    //TODO add validation,
    @RequestMapping(value="/editCart", params="add", method = RequestMethod.POST)
    public String addItem (ServletRequest request, HttpSession session, Model model,
    SessionStatus sessionStatus) {
        ArrayList<Item> cart;
        int id = Integer.parseInt(request.getParameter("shelf"));
        Item i = itemDao.findById(id);

        if (session.getAttribute("cart") != null){
            cart = (ArrayList<Item>)session.getAttribute("cart");
        }
        else{
            cart = new ArrayList<Item>();
        }

        cart.add(i);
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        String msg = i.getName() + " has been added to the cart!";

        session.setAttribute("msg", msg);
        model.addAttribute("msg", msg);
        return "redirect:/editCart";
    }

    @RequestMapping(value="/editCart", params="remove", method = RequestMethod.POST)
    public String removeItems (Model model, HttpServletRequest request, HttpSession session,
    @RequestParam List<String> markedItem, SessionStatus sessionStatus) {
        ArrayList<Item> cart;
        String msg = "";
        // turns list of strings to list of Integers
        // doesn't really work here though :(
//        List<Integer> itemIds = markedItem.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        /* TODO create error msg for when they are trying to remove when cart is empty */
        if (session.getAttribute("cart") != null) {
            cart = (ArrayList<Item>)session.getAttribute("cart");
        }
        else {
            cart = new ArrayList<>();
            msg += "Cart is empty! Please add an item before proceeding.";
//            return "redirect:/editCart";
        }

        try {

            for(String s : markedItem){
                int id = Integer.parseInt(s);
                for(int i = 0; i < cart.size(); i ++) {
                    if(cart.get(i).getUid() == id) {
                        cart.remove(i);
                    }
                }
            }
        } catch(Exception e) {
            msg += e.getMessage().toString();
        }


        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);

        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);

        return "redirect:/editCart";
    }
}

