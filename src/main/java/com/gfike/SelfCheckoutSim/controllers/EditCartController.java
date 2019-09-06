package com.gfike.SelfCheckoutSim.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditCartController {

    @Autowired
    public ItemDao itemDao;
// doesn't work
//    @RequestMapping(value ="/editCart",params={"","add"}, method = RequestMethod.GET)
@RequestMapping(value ="/editCart", method = RequestMethod.GET)
    public String editCartGet(Model model, HttpSession session){

        if (session.getAttribute("cart") != null) {
            ArrayList<Item> cart = (ArrayList<Item>)session.getAttribute("cart");
            model.addAttribute("cart", cart);
        }

        List<Item> items = itemDao.findAll();
        model.addAttribute("items", items);

        String msg = (String)session.getAttribute("msg");
        model.addAttribute("msg", msg);
        return "editCart";
    }
    // doesn't work
//    @RequestMapping(value ="/editCart", params="remove", method = RequestMethod.GET)
//    public String removeItemGet(Model model, HttpSession session){
//
//        if (session.getAttribute("cart") != null) {
//            ArrayList<Item> cart = (ArrayList<Item>)session.getAttribute("cart");
//            model.addAttribute("cart", cart);
//            session.removeAttribute("cart");
//        }
//
//        List<Item> items = itemDao.findAll();
//        model.addAttribute("items", items);
//
//        String msg = (String)session.getAttribute("msg");
//        model.addAttribute("msg", msg);
//        return "editCart";
//    }
    //TODO work on noting if an item has duplicates
    //TODO add validation
    @RequestMapping(value="/editCart", params="add", method = RequestMethod.POST)
    public String addItem (Model model, ServletRequest request, HttpSession session) {
        Item i;
        ArrayList<Item> cart;
        int id = Integer.parseInt(request.getParameter("shelf"));
        i = itemDao.findById(id);

        if (session.getAttribute("cart") != null) {
            cart = (ArrayList<Item>)session.getAttribute("cart");
        }
        else {
            cart = new ArrayList<>();
        }

        cart.add(i);
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);
        String msg = i.getName() + " has been added to the cart!";

        session.setAttribute("msg", msg);
        model.addAttribute("msg", msg);
        return "redirect:/editCart";
    }

    @RequestMapping(value="/editCart", params="remove", method = RequestMethod.POST)
    public String removeItems (Model model, HttpServletRequest request, HttpSession session,
    @RequestParam List<String> markedItem) {
        ArrayList<Item> cart;
        // turns list of strings to list of Integers
        // doesn't really work here though :(
//        List<Integer> itemIds = markedItem.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        //List<String> itemNames = null;
        /* TODO create error msg for when they are trying to remove when cart is empty */
        if (session.getAttribute("cart") != null) {
            cart = (ArrayList<Item>)session.getAttribute("cart");
            // doesn't work
//            session.removeAttribute("cart");
        }
        else {
            cart = new ArrayList<>();
        }

        String msg = "";
        // tried remapping cart in session to a new cart, didn't work
        try {
            for(String s : markedItem){
                int i = Integer.parseInt(s);
                Item itemToRemove = itemDao.findById(i);

                if(cart.contains(itemToRemove)){
                    cart.remove(itemToRemove);
                }
            }

            msg = "Current cart size: " + cart.size();

        } catch(Exception e) {
            msg = e.toString();
        }
        // doesn't work
//        session.removeAttribute("cart");
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);

        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);
        return "redirect:/editCart";
    }
}

