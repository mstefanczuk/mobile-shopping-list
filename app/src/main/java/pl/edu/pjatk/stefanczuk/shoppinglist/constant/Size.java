package pl.edu.pjatk.stefanczuk.shoppinglist.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Size {

    SMALL("Mały"),
    BIG("Duży");

    private String value;
}
