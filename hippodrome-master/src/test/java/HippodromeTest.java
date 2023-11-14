import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class HippodromeTest {

    @Test
    void constructorShouldThrowExceptionIfHorsesIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }


    @Test
    void constructorShouldThrowExceptionIfHorsesListIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(Collections.emptyList());
        });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesShouldReturnTheSameListPassedInConstructor() {
        List<Horse> inputList = IntStream.range(0, 30)
                .mapToObj(i -> new Horse("Horse " + i, i))
                .collect(Collectors.toList());
        Hippodrome hippodrome = new Hippodrome(inputList);
        List<Horse> horses = hippodrome.getHorses();

        assertEquals(inputList, horses);
    }

    @Test
    void moveShouldCallMoveOnAllHorses() {
        List<Horse> mockHorses = IntStream.range(0, 50)
                .mapToObj(i -> mock(Horse.class))
                .collect(Collectors.toList());
        Hippodrome hippodrome = new Hippodrome(mockHorses);
        hippodrome.move();

        mockHorses.forEach(horse -> verify(horse).move());
    }

    @Test
    void getWinnerShouldReturnHorseWithGreatestDistance() {
        Horse expectedWinner = new Horse("Winner", 3.0, 10.0);
        List<Horse> horses = Arrays.asList(
                new Horse("Horse1", 3.0, 5.0),
                expectedWinner,
                new Horse("Horse2", 3.0, 8.0)
        );

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();

        assertEquals(expectedWinner, winner);
    }



}