package md.homeworks.lesson5;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Word {

    private String word;
    private String transcription;
    private String translation;
}
