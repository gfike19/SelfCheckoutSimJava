package com.gfike.SelfCheckoutSim.controllers;

import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    ItemDao itemDao;

    @GetMapping
    public String checkoutGet(Model model, HttpSession session){
        String msg;
        if(session.getAttribute("cart") == null) {
            msg = "Cart is empty!";
            model.addAttribute("msg", msg);
            session.setAttribute("msg", msg);
            return "redirect:/editCart";
        }

        ArrayList<Item> cart = (ArrayList<Item>)session.getAttribute("cart");

        if(cart.size() == 0){
            msg = "Cart is empty!";
            model.addAttribute("msg", msg);
            session.setAttribute("msg", msg);
            return "redirect:/editCart";
        }

        model.addAttribute("cart", cart);
        msg = (String) session.getAttribute("msg");
        model.addAttribute("msg", msg);

        if (session.getAttribute("scanned") != null){
            ArrayList<String> scanned = (ArrayList<String>)session.getAttribute("scanned");
            model.addAttribute("scanned", scanned);
        }

        return "checkout";
    }

    @PostMapping(params = "scan")
    public String scan (ServletRequest request, HttpSession session, Model model){

        int id = Integer.parseInt(request.getParameter("scanner"));
        Item i = itemDao.findById(id);

        ArrayList<String> scanned;

        if(session.getAttribute("scanned") != null){
            scanned = (ArrayList<String>) session.getAttribute("scanned");
        }
        else{
            scanned = new ArrayList<String>();
        }

        scanned.add(i.getName());
        session.setAttribute("scanned", scanned);
        model.addAttribute("scanned", scanned);

//        model.addAttribute("msg", msg);
//        session.setAttribute("msg", msg);

        return "redirect:/checkout";
    }

}
