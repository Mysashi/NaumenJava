package ru.Ignatiev.NauJava.test.springTests.mockito;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Ignatiev.NauJava.domain.impl.BookRepositoryImpl;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryImplTest {


    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    @Test
    void findBookByYearRange_ShouldThrowException_WhenMinYearIsGreaterThanMaxYear() {
        int min = 2025;
        int max = 2020;

        assertThrows(IllegalArgumentException.class, () -> {
            bookRepository.findBooksByYearRange(min, max);
        });
    }
}
