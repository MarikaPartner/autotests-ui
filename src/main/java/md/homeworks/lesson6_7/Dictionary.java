package md.homeworks.lesson6_7;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dictionary {

    private String nameDictionary;
    private String translationDirection;

    public Dictionary(String nameDictionary) {
        this.nameDictionary = nameDictionary;
        this.translationDirection = "Английский-Русский";
    }
}
