package hoxhalli.klajdi.assignment2.service;

import hoxhalli.klajdi.assignment2.model.PetData;
import hoxhalli.klajdi.assignment2.repository.PetDataRepositoryJpa;
import hoxhalli.klajdi.assignment2.repository.PetEntityJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PetDataServiceImpl  implements PetDataService {

    private final PetDataRepositoryJpa petDataRepositoryJpa;


      PetDataServiceImpl(PetDataRepositoryJpa petDataRepositoryJpa){
        log.trace("constructor() is called");
        this.petDataRepositoryJpa = petDataRepositoryJpa;
    }

    private static void copyFormToEntity(PetData form, PetEntityJpa pet){
        log.trace("copyFormToEntity() is called");
        pet.setName(form.getName());
        pet.setPetKind(form.getPetKind());
        pet.setPetSex(form.getPetSex());
        pet.setPetVaccinated(form.isVaccinated());
    }

    private  static void copyEntityToForm(PetEntityJpa pet, PetData form){
        log.trace("copyEntityToForm() is called");
        form.setId(pet.getId());
        form.setName((pet.getName()));
        form.setPetKind(pet.getPetKind());
        form.setPetSex(pet.getPetSex());
        form.setVaccinated((pet.getPetVaccinated()));
    }

    public void insertPetForm(PetData form){
          log.trace("insertPetForm() is called");
          log.debug("insert pet form" + form);
          PetEntityJpa pet = new PetEntityJpa();
          copyFormToEntity(form, pet);
          pet = petDataRepositoryJpa.save(pet);
          form.setId(pet.getId());
    }

    public List<PetData> getAllPetForms(){
          log.trace("getAllPetForms()");
          List<PetData> formList = new ArrayList<>();
          List <PetEntityJpa> petList = petDataRepositoryJpa.findAll();
          for(PetEntityJpa pet: petList){
              PetData form = new PetData();
              copyEntityToForm(pet, form );
              formList.add(form);
          }
          log.trace("retrieved {} form objects", formList.size());
          return formList;
    }

    public void deleteAllPetForms(){
          log.trace("deleteAllPetForms() is called");
          log.debug("deleting all student forms");
          petDataRepositoryJpa.deleteAll();
    }

    public void deletePetForm(int id) {
        log.trace("deletePetForm() is called");
        log.debug("deleting pet form for id=" + id);
        petDataRepositoryJpa.deleteById(id);
    }

    public PetData getPetForm(int id){
          log.trace("getPetForm() is called");
          log.debug("getting pet form for id=" + id);
          Optional <PetEntityJpa> result = petDataRepositoryJpa.findById(id);
          if(result.isPresent()){
              PetData form = new PetData();
              PetEntityJpa pet = result.get();
              copyEntityToForm(pet,form);
              log.debug("the form for id={} is retrieved", id);
              return form;
          }
          log.debug("the form for id={} is not found", id);
          return null;
    }

    public void updatePetForm(PetData form){
        log.trace("updatePetForm() is called");
        log.debug("form=" + form);

        Optional <PetEntityJpa> result = petDataRepositoryJpa.findById(form.getId());
        if(result.isPresent()){
            PetEntityJpa pet = result.get();
            copyFormToEntity(form, pet);
            petDataRepositoryJpa.save(pet);
        }
    }


}