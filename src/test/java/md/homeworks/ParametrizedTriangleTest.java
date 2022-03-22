package md.homeworks;

import md.homeworks.lesson4.Triangle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParametrizedTriangleTest {

    private static Logger logger = LoggerFactory.getLogger(ParametrizedTriangleTest.class);

    @AfterEach
    void tearDown() {
        logger.warn("AfterEach");
    }

//  Данные для позитивных тестов
    public static Stream<Arguments> triangles() {
        return Stream.of(Arguments.of(new Triangle(2, 2,1), 0.9682),
                Arguments.of(new Triangle(25, 25,42), 284.8579),
                Arguments.of(new Triangle(11000, 11000,11000), 52394536.9290),
                Arguments.of(new Triangle(9, 12,15), 54),
                Arguments.of(new Triangle(300, 400,500), 60000),
                Arguments.of(new Triangle(6, 28,24), 57.7495));
    }

//  Позитивные тесты
    @ParameterizedTest(name = "Площадь треугольника: позитивный сценарий, площадь треугольника {0} == {1}")
    @MethodSource("triangles")
    void countAreaTriangleIsNotFailedTest(Triangle triangle, double expectedResult) {
        double areaTriangle = triangle.countAreaTriangle();
        assertEquals(expectedResult, areaTriangle);
    }

//  Данные для негативных тестов
    public static Stream<Arguments> negativeTriangles() {
        return Stream.of(Arguments.of(new Triangle(-2, 2, 1), "The sides of a triangle cannot be negative."),
                Arguments.of(new Triangle(2, -2, 1), "The sides of a triangle cannot be negative."),
                Arguments.of(new Triangle(2, 2, -1), "The sides of a triangle cannot be negative."),
                Arguments.of(new Triangle(0, 2, 1), "The sides of the triangle must be greater than 0."),
                Arguments.of(new Triangle(2, 0, 1), "The sides of the triangle must be greater than 0."),
                Arguments.of(new Triangle(2, 2, 0), "The sides of the triangle must be greater than 0."),
                Arguments.of(new Triangle(25, 75, 13), "The triangle with the specified sides does not exist."),
                Arguments.of(new Triangle(75, 13, 25), "The triangle with the specified sides does not exist."),
                Arguments.of(new Triangle(13, 25, 75), "The triangle with the specified sides does not exist.")
        );
    }

//  Негативные тесты
    @ParameterizedTest(name = "Площадь треугольника: негативный сценарий (треугольника {0}, ошибка:{1})")
    @MethodSource("negativeTriangles")
    public void countAreaTriangleIsNotFailedTest(Triangle triangle, String errorText) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, triangle::countAreaTriangle);
        assertEquals(errorText, illegalArgumentException.getMessage());
    }
}