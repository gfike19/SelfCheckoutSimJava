package com.gfike.SelfCheckoutSim.controllers;

import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("editCart")
public class EditCartController {

    /*
    TODO remove items from all items list that have already been put in cart
     */
    @Autowired
    public ItemDao itemDao;

// old way below
//@RequestMapping(value ={"/editCart"}, method = RequestMethod.GET)
    @GetMapping
    public String editCartGet(Model model,  HttpSession session){

        HashMap<Item, Integer> cart;

        if (session.getAttribute("cart") != null){
            cart = (HashMap<Item, Integer>)session.getAttribute("cart");
        }
        else{
            cart = new HashMap<Item, Integer>();
        }

        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        List<Item> items = itemDao.findAll();
        model.addAttribute("items", items);

        String msg = (String)session.getAttribute("msg");
        model.addAttribute("msg", msg);
        return "editCart";
    }
    // old way below
    //    @RequestMapping(value="/editCart", params="add", method = RequestMethod.POST)
    @PostMapping(params = "add")
    public String addItem (ServletRequest request, HttpSession session, Model model) {
        HashMap<Item, Integer> cart= (HashMap<Item, Integer>)session.getAttribute("cart");

        if (request.getParameter("shelf").isEmpty()){
            String msg = "Select an item to add to the cart!";
            model.addAttribute("msg", msg);
            session.setAttribute("msg", msg);
            return "redirect:/editCart";
        }

        int id = Integer.parseInt(request.getParameter("shelf"));
        Item i = itemDao.findById(id);

        cart.put(i, 1);
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        return "redirect:/editCart";
    }
    // @requestparam required = false is needed in the even the user doesn't select an item
    @PostMapping(params = "remove")
    public String removeItems (@RequestParam(required = false) List<String> markedItem,Model model, HttpServletRequest request, HttpSession session,
     SessionStatus sessionStatus) {

        HashMap<Item, Integer> cart = (HashMap<Item, Integer>)session.getAttribute("cart");
        String msg = "";

        if(markedItem == null){
            msg += "Cart is empty!";
            model.addAttribute("msg", msg);
            session.setAttribute("msg", msg);
            return "redirect:/editCart";
        }


            for(String s : markedItem){
                int id = Integer.parseInt(s);
                for(Item i : cart.keySet()){
                    if(id == i.getUid()){
                        cart.remove(i);
                    }
                }
            }

        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);

        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);

        return "redirect:/editCart";
    }

    @PostMapping(params = "inc")
    public String inc(Model model, HttpServletRequest request, HttpSession session,
                      SessionStatus sessionStatus){

        Object o = request.getParameter("plus");
        String msg = o.toString();
        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);

        return "redirect:/editCart";
    }

}

