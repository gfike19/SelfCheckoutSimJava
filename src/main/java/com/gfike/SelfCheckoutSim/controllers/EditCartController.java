package com.gfike.SelfCheckoutSim.controllers;
import com.gfike.SelfCheckoutSim.daos.ItemDao;
import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    // TODO use hidden input?
    // old way below
    //    @RequestMapping(value="/editCart", params="add", method = RequestMethod.POST)
    @PostMapping(params = "add")
    public String addItem (ServletRequest request, HttpSession session, Model model) {
        HashMap<Item, Integer> cart= (HashMap<Item, Integer>)session.getAttribute("cart");
        String msg = "";
        if (request.getParameter("shelf").isEmpty()){
             msg = "Select an item to add to the cart!";
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
    // @requestparam required = false is needed in the event the user doesn't select an item
    @PostMapping(params = "update")
    public String updateCart (@RequestParam(required = false) List<String> markedItem,Model model,
  HttpServletRequest request, HttpSession session,
  @RequestParam(required = false) List<Integer> currCount,
  @RequestParam(required = false) List<String> itemId) {

        HashMap<Item, Integer> cart = (HashMap<Item, Integer>)session.getAttribute("cart");
        String msg = "";

        Iterator cartIt = cart.entrySet().iterator();

        if(markedItem != null) {
            while (cartIt.hasNext()) {
                Map.Entry entry = (Map.Entry)cartIt.next();
                Item item = (Item)entry.getKey();
                String id = "" + item.getUid();

                // removes item from cart and from lists
                if (markedItem.contains(id)){
                    cart.remove(item);
                    if (itemId.contains(id)){
                        int pos = itemId.indexOf(id);
                        currCount.remove(pos);
                        itemId.remove(pos);
                    }
                }
            }
        }

        while (cartIt.hasNext()) {
            Map.Entry entry = (Map.Entry) cartIt.next();
            Item item = (Item) entry.getKey();
            int oldVal = cart.get(item);
            int newVal = oldVal + (int) entry.getValue();
            cart.replace(item, oldVal, newVal);
        }


//        might not be needed
//        HashMap<Item, Integer> updated = new HashMap<>();
//
//        for(int i = 0; i < currCount.size(); i++){
//            int id = Integer.parseInt(itemId.get(i));
//            Item item = itemDao.findById(id);
//            updated.put(item, currCount.get(i));
//        }

//        doesn't work
//        while (updateIt.hasNext()) {
//            Map.Entry entry = (Map.Entry)updateIt.next();
//            Item currentItem = (Item)entry.getKey();
//            if(cart.keySet().contains(currentItem)){
//            }
//        }

        model.addAttribute("msg", msg);
        session.setAttribute("msg", msg);

        return "redirect:/editCart";
    }

}