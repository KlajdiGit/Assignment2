package hoxhalli.klajdi.assignment2.service;

import hoxhalli.klajdi.assignment2.model.PetData;

import java.util.List;

public interface PetDataService {

    void insertPetForm(PetData pet);

    List<PetData> getAllPetForms();

    void deleteAllPetForms();

    void deletePetForm(int id);

    PetData getPetForm(int id);

    void updatePetForm(PetData form);
}
