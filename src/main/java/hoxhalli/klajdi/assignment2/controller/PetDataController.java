package hoxhalli.klajdi.assignment2.controller;


import hoxhalli.klajdi.assignment2.model.PetData;
import hoxhalli.klajdi.assignment2.service.PetDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class PetDataController {


    private static final String[] kinds = {
            "---Select Kind---",
            "Cat", "Dog", "Rabbit" };


    private final PetDataService petDataService;

    public PetDataController(PetDataService petDataService){
        this.petDataService = petDataService;
    }

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "Index";
    }


    @GetMapping("/add-pet")
    public ModelAndView addPet(){
        log.trace("addPet() is called");
        ModelAndView modelAndView = new ModelAndView(
                "AddPet","form", new PetData());
        modelAndView.addObject("kinds",kinds);
        return modelAndView;
    }

    @PostMapping("/insert-pet")
    public String insertPet(
        @Validated @ModelAttribute("form") PetData form,
                BindingResult bindingResult,
                        Model model){
            log.trace("insertPet() is called");
            log.debug("form = " + form);

                log.trace("the user inputs are correct");
                petDataService.insertPetForm(form);
                log.debug("id = " + form.getId());
                return "redirect:confirm-insert/" + form.getId();
        }

    @GetMapping("/confirm-insert/{id}")
    public String confirmInsert(@PathVariable(name = "id") String strId, Model model){
        log.trace("confirmInsert() is called");
        log.debug("id = " + strId);
            int id = Integer.parseInt(strId);
            log.trace("looking for the data in the database");
            PetData form = petDataService.getPetForm(id);
                if(form == null)
                    log.trace("no data for this id=" + id);
                log.trace("showing the data");
                model.addAttribute("form", form);
                return "ConfirmInsert";

    }

    @GetMapping("/list-pets")
    public ModelAndView listpets() {
        log.trace("listPets() is called");
        List<PetData> list = petDataService.getAllPetForms();
        log.debug("list size = " + list.size());
        return new ModelAndView("ListPets",
                "pets", list);
    }

    @GetMapping("/delete-all")
    public String deleteAll(){
        log.trace("deleteAll() is called");
        petDataService.deleteAllPetForms();
        return "redirect:list-pets";
    }

    @GetMapping("pet-details/{id}")
    public String petDetails(@PathVariable String id, Model model){
        log.trace("petDetails() is called");
        log.debug("id = " + id);
            PetData form = petDataService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("pet", form);
                return "PetDetails"; // show the student data in the form to edit
            } else {
                log.trace("no data for this id=" + id);
                return "Error";
            }

    }

    @GetMapping("/delete-pet")
    public String deletePet(@RequestParam String id, Model model) {
        log.trace("deletePet() is called");
        try {
            PetData form = petDataService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("pet", form);
                return "DeletePet";
            } else {
                return "redirect:list-pets";
            }
        } catch (NumberFormatException e) {
            return "redirect:list-pets";
        }
    }

    @PostMapping("/remove-pet")
    public String removePett(@RequestParam String id) {
        log.trace("removePet() is called");
        log.debug("id = " + id);
        petDataService.deletePetForm(Integer.parseInt(id));

        return "redirect:list-pets";
    }

    @GetMapping("/edit-pet")
    public String editPet(@RequestParam String id, Model model) {
        log.trace("editPet() is called");
        log.debug("id = " + id);
            PetData form = petDataService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("form", form);
                model.addAttribute("kinds", kinds);
                return "EditPet";
            } else {
                log.trace("no data for this id=" + id);
                return "redirect:list-pets";
            }

    }


    @PostMapping("/update-pet")
    public String updatePet(
            @Validated @ModelAttribute("form") PetData form,
            BindingResult bindingResult,
            Model model) {
        log.trace("updatePet() is called");
        log.debug("form = " + form);
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("kinds", kinds);
            return "EditPet";
        } else {
            log.trace("the user inputs are correct");
            petDataService.updatePetForm(form);
            log.debug("id = " + form.getId());
            return "redirect:pet-details/" + form.getId();
        }
    }

}
