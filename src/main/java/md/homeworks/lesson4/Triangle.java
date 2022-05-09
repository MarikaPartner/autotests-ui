package md.homeworks.lesson4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data  // Заменяет геттеры и сеттеры
@AllArgsConstructor  //  Создает конструктор со всеми параметрами
public class Triangle {

    private int a;
    private int b;
    private int c;

    public double countAreaTriangle() {
        checkSidesArePositive();    // Проверка, что стороны треугольника не отрицательные
        checkSidesAreNotZero();     // Проверка, что стороны треугольника не равны нулю
        checkIsTriangle();          // Проверка, что треугольник с указанными сторонами существует
        double semiperimeter = (a + b + c) / 2.0; // Вычисление полупериметра
        double areaTriangle = Math.sqrt(semiperimeter * (semiperimeter - a) * (semiperimeter - b) * (semiperimeter - c));  // Вычисление площади по формуле Герона
        double roundedAreaTriangle = new BigDecimal(areaTriangle).setScale(4, RoundingMode.HALF_EVEN).doubleValue();  // Округление полученного значения
        return roundedAreaTriangle;
    }

    private void checkSidesArePositive () {
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("The sides of a triangle cannot be negative.");
        }
    }

    private void checkSidesAreNotZero () {
        if (a == 0 || b == 0 || c == 0) {
            throw new IllegalArgumentException("The sides of the triangle must be greater than 0.");
        }
    }

    private void checkIsTriangle () {
        if (a + b < c || b + c < a || a + c < b) {
            throw new IllegalArgumentException("The triangle with the specified sides does not exist.");
        }
    }
}