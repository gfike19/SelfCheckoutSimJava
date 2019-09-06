package com.gfike.SelfCheckoutSim.controllers;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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

        // doesn't work
//        String [] checkboxes = request.getParameterValues("markedItem");
//        model.addAttribute("msg", checkboxes.length);

        String msg = markedItem.toString();
        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);
        return "redirect:/editCart";
    }
}

