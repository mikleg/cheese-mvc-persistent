package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {
//Added categoryDao !
    @Autowired
    private CheeseDao cheeseDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private MenuDao menuDao; //It added for fixing a bug with 'remove cheese' functionality

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());

        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId,
                                       Model model) {
        Category cat = categoryDao.findOne(categoryId);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());

            return "cheese/add";
        }
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {


        for (Menu menu : menuDao.findAll()){    //It added for fixing a bug with 'remove cheese' functionality
            for (int cheeseId : cheeseIds) {  //It added for fixing a bug with 'remove cheese' functionality
                menu.removeItem(cheeseDao.findOne(cheeseId));  //It added for fixing a bug with 'remove cheese' functionality
            }                                                   //It added for fixing a bug with 'remove cheese' functionality
        }                                                       //It added for fixing a bug with 'remove cheese' functionality

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "category/{id}", method = RequestMethod.GET)
    public String category(Model model, @PathVariable("id") int id) {
        Category cat = categoryDao.findOne(id);
        model.addAttribute("cheeses", cat.getCheeses());
        model.addAttribute("title", "All cheeses with category " + cat.getName());

        return "cheese/index";
    }



    /**
     * It displays an edit form
     *
     * @param model is an Interface. It defines a holder for model attributes and primarily designed for adding attributes to the model
     * @param cheeseId is an ID of cheese (see the field "id" of Cheese class)
     * @return An Edit template
     */
    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId){

        model.addAttribute("title", "Edit Cheese");
        model.addAttribute("cheese", cheeseDao.findOne(cheeseId));
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/edit";
    }

    /**
     * It processes an edit form
     *
     * @param model is an Interface. It defines a holder for model attributes and primarily designed for adding attributes to the model
     * @param cheeseId is the ID of cheese (see the field "id" of Cheese class)
     * @param cheese is the instance of edited cheese
     * @param errors is an Interface. Stores and exposes information about data-binding and validation errors for a specific object.
     * @param categoryId categoryId is the ID of the chosen category
     * @return It redirects to the main page
     */



    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(@PathVariable int cheeseId, @Valid Cheese cheese, Errors errors
            , Model model, @RequestParam int categoryId
     //int cheeseId --> @PathVariable int cheeseId changed for avoiding a bug with submitting a form after correction
    )

    {
        model.addAttribute("categories", categoryDao.findAll());
        if (errors.hasErrors()) {
            return "cheese/edit";
        }

        Cheese aCheese = cheeseDao.findOne(cheeseId);
        aCheese.setName(cheese.getName());
        aCheese.setDescription(cheese.getDescription());
        Category cat = categoryDao.findOne(categoryId);
        aCheese.setCategory(cat);
        cheeseDao.save(aCheese);

        return "redirect:/cheese";
    }

/*    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(int cheeseId, @Valid Cheese cheese, Errors errors
            , Model model, @RequestParam int categoryId

    )

    {
        model.addAttribute("categories", categoryDao.findAll());
        if (errors.hasErrors()) {
            return "cheese/edit";
        }

        Cheese aCheese = cheeseDao.findOne(cheeseId);
        aCheese.copy(cheese);
        Category cat = categoryDao.findOne(categoryId);
        aCheese.setCategory(cat);
        cheeseDao.save(aCheese);

        return "redirect:/cheese";
    }*/



}
