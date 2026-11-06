package br.com.erudio.restwithspringbooterudio.modules.person.data.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse extends RepresentationModel<PersonResponse> {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonResponse that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
