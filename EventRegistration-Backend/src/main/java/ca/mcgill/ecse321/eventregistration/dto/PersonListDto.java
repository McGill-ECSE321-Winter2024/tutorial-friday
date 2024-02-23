package ca.mcgill.ecse321.eventregistration.dto;

import java.util.List;

public class PersonListDto {
    private List<PersonResponseDto> people;

    public PersonListDto(List<PersonResponseDto> people) {
        this.people = people;
    }

    public List<PersonResponseDto> getPeople() {
        return people;
    }

    public void setPeople(List<PersonResponseDto> people) {
        this.people = people;
    }
}
