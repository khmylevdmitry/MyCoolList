import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class HorseTest {

    @Test
    void constructorShouldThrowExceptionIfNameIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 3.0);
        });
        assertEquals("Name cannot be null.", exception.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    void constructorShouldThrowExceptionIfNameIsBlank(String input) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(input, 3.0);
        });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionIfSpeedIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Test", -1.0);
        });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructorShouldThrowExceptionIfDistanceIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Test", 3.0, -1.0);
        });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameShouldReturnNamePassedInConstructor() {
        Horse horse = new Horse("TestName", 3.0);
        assertEquals("TestName", horse.getName());
    }

    @Test
    void getSpeedShouldReturnSpeedPassedInConstructor() {
        Horse horse = new Horse("TestName", 3.0);
        assertEquals(3.0, horse.getSpeed());
    }

    @Test
    void getDistanceShouldReturnDistancePassedInConstructor() {
        Horse horse = new Horse("TestName", 3.0, 5.0);
        assertEquals(5.0, horse.getDistance());
    }

    @Test
    void getDistanceShouldReturnZeroForTwoParameterConstructor() {
        Horse horse = new Horse("TestName", 3.0);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    void moveShouldCallGetRandomDouble() {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("TestName", 3.0);
            horse.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void moveShouldCorrectlyCalculateDistance(double randomValue) {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            Horse horse = new Horse("TestName", 3.0, 5.0);
            horse.move();
            double expectedDistance = 5.0 + 3.0 * randomValue;
            assertEquals(expectedDistance, horse.getDistance());
        }
    }


}