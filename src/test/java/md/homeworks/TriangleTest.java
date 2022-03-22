package md.homeworks;

import md.homeworks.lesson4.Triangle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@Disabled("Перенесены в параметризованные тесты")

public class TriangleTest {

    @Test
    @DisplayName("Площадь треугольника 2, 2, 1 должна быть равна 0.9682")  // Равнобедренный остроугольный
    public void countAreaIsoscelesAcuteTriangleSuccessfulTest() {
        Triangle triangle = new Triangle(2, 2, 1); // Arrange
        double areaTriangle = triangle.countAreaTriangle();  // Act
        assertEquals(0.9682, areaTriangle);
    }

    @Test
    @DisplayName("Площадь треугольника 25, 25, 42 должна быть равна 284.8579")  // Равнобедренный тупоугольный
    public void countAreaIsoscelesObtuseTriangleSuccessfulTest() {
        Triangle triangle = new Triangle(25, 25, 42);
        double areaTriangle = triangle.countAreaTriangle();
        assertEquals(284.8579, areaTriangle);
    }

    @Test
    @DisplayName("Площадь треугольника 11000, 11000, 11000 должна быть равна 52394536.9290")  // Равносторонний остроугольный
    public void countAreaEquilateralAcuteTriangleSuccessfulTest() {
        Triangle triangle = new Triangle(11000, 11000, 11000);
        double areaTriangle = triangle.countAreaTriangle();
        assertEquals(52394536.9290, areaTriangle);
    }

    @Test
    @DisplayName("Площадь треугольника 9, 12, 15 должна быть равна 54")  // Разносторонний прямоугольный
    public void countAreaRightTriangleSuccessfulTest() {
        Triangle triangle = new Triangle(9, 12, 15);
        double areaTriangle = triangle.countAreaTriangle();
        assertEquals(54, areaTriangle);
    }

    @Test
    @DisplayName("Площадь треугольника 300, 400, 500 должна быть равна 60000")  // Разносторонний остроугольный
    public void countAreaAcuteTriangleSuccessfulTest() {
        Triangle triangle = new Triangle(300, 400, 500);
        double areaTriangle = triangle.countAreaTriangle();
        assertEquals(60000, areaTriangle);
    }

    @Test
    @DisplayName("Площадь треугольника 6, 28,  24 должна быть равна 57.7495")  //  Разносторонний тупоугольный
    public void countAreaObtuseTriangleSuccessfulTest() {
        Triangle triangle = new Triangle(6, 28, 24);
        double areaTriangle = triangle.countAreaTriangle();
        assertEquals(57.7495, areaTriangle);
    }

    @Test
    @DisplayName("Если первая сторона треугольника отрицательная - ошибка 'The sides of a triangle cannot be negative.'")
    public void countAreaTriangle1SideIsNegativeFailedTest() {
        Triangle triangle = new Triangle(-2, 2, 1);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The sides of a triangle cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Если вторая сторона треугольника отрицательная - ошибка 'The sides of a triangle cannot be negative.'")
    public void countAreaTriangle2SideIsNegativeFailedTest() {
        Triangle triangle = new Triangle(2, -2, 1);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The sides of a triangle cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Если третья сторона треугольника отрицательная - ошибка 'The sides of a triangle cannot be negative.'")
    public void countAreaTriangle3SideIsNegativeFailedTest() {
        Triangle triangle = new Triangle(2, 2, -1);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The sides of a triangle cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Если первая сторона треугольника равна 0 - ошибка 'The sides of the triangle must be greater than 0.'")
    public void countAreaTriangle1SideIsZeroFailedTest() {
        Triangle triangle = new Triangle(0, 2, 1);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The sides of the triangle must be greater than 0.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Если вторая сторона треугольника равна 0 - ошибка 'The sides of the triangle must be greater than 0.'")
    public void countAreaTriangle2SideIsZeroFailedTest() {
        Triangle triangle = new Triangle(2, 0, 1);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The sides of the triangle must be greater than 0.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Если третья сторона треугольника равна 0 - ошибка 'The sides of the triangle must be greater than 0.'")
    public void countAreaTriangle3SideIsZeroFailedTest() {
        Triangle triangle = new Triangle(2, 2, 0);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The sides of the triangle must be greater than 0.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Треугольник со сторонами 25, 75, 13 не существует - ошибка 'The triangle with the specified sides does not exist.'")
    public void countAreaTriangleIsNotSumCALessBFailedTest() {
        Triangle triangle = new Triangle(25, 75, 13);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The triangle with the specified sides does not exist.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Треугольник со сторонами 75, 13, 25 не существует - ошибка 'The triangle with the specified sides does not exist.'")
    public void countAreaTriangleIsNotSumBCLessAFailedTest() {
        Triangle triangle = new Triangle(75, 13, 25);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The triangle with the specified sides does not exist.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("Треугольник со сторонами 13, 25, 75 не существует - ошибка 'The triangle with the specified sides does not exist.'")
    public void countAreaTriangleIsNotSummABLessCFailedTest() {
        Triangle triangle = new Triangle(13, 25, 75);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals("The triangle with the specified sides does not exist.", illegalArgumentException.getMessage());
    }
}