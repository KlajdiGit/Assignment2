package hoxhalli.klajdi.assignment2.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class PetData implements Serializable {

    private int id = 0;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "[A-Za-z]*")
    private String name = "";

    @NotBlank
    @Pattern(regexp = "(Cat|Dog|Rabbit)?")
    private String petKind = "";

    @NotNull

    private String petSex = "Female";

    private boolean vaccinated = false;


}
