package pl.edu.pjatk.stefanczuk.shoppinglist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Size {

    SMALL("Mały"),
    BIG("Duży");

    private String value;
}
