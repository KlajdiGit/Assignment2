package hoxhalli.klajdi.assignment2.repository;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "pet")
public class PetEntityJpa {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( name = "pet_name")
    private String name = "";

    @Column( name = "pet_kind")
    private String petKind = "";

    @Column(name = "pet_sex")
    private String petSex = "";

    @Column(name = "pet_vaccinated")
    private Boolean petVaccinated = false;



}
