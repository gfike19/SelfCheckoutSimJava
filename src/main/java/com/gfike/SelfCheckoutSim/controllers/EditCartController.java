package com.gfike.SelfCheckoutSim.controllers;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;

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
        return "editCart";
    }

    @RequestMapping(value="/editCart", method = RequestMethod.POST)
    public String addItem (Model model, ServletRequest request, HttpSession session) {

        if (session.getAttribute("cart") != null) {
            ArrayList<Item> cart = (ArrayList<Item>)session.getAttribute("cart");
            int id = Integer.parseInt(request.getParameter("shelf"));
            Item i = itemDao.findById(id);
            cart.add(i);
        }
        else {
            int id = Integer.parseInt(request.getParameter("shelf"));
            Item i = itemDao.findById(id);
            ArrayList<Item> cart = new ArrayList<>();
            cart.add(i);
            session.setAttribute("cart", cart);
            model.addAttribute("cart", cart);
        }

        return "redirect:/editCart";
    }
}
