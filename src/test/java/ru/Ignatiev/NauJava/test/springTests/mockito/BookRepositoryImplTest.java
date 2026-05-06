package ru.Ignatiev.NauJava.test.springTests.mockito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.impl.BookRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<BookEntity> criteriaQuery;

    @Mock
    private Root<BookEntity> root;

    @Mock
    private TypedQuery<BookEntity> typedQuery;

    @Test
    void findBookByYearRange_ShouldThrowException_WhenMinYearIsGreaterThanMaxYear() {
        int min = 2025;
        int max = 2020;

        assertThrows(IllegalArgumentException.class, () -> {
            bookRepository.findBooksByYearRange(min, max);
        });
    }

    @Test
    void findBooksByYearRange_ShouldReturnCorrectData() {

        int minYear = 2000;
        int maxYear = 2020;
        BookEntity book = new BookEntity();
        List<BookEntity> expectedBooks = List.of(book);

        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        Mockito.when(criteriaBuilder.createQuery(BookEntity.class)).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.from(BookEntity.class)).thenReturn(root);

        Predicate mockPredicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.between(root.get("yearOfCreation"), minYear, maxYear))
                .thenReturn(mockPredicate);

        Mockito.when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.where(mockPredicate)).thenReturn(criteriaQuery);
        Mockito.when(criteriaBuilder.asc(root.get("yearOfCreation"))).thenReturn(Mockito.mock(jakarta.persistence.criteria.Order.class));

        Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        Mockito.when(typedQuery.getResultList()).thenReturn(expectedBooks);

        List<BookEntity> result = bookRepository.findBooksByYearRange(minYear, maxYear);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(expectedBooks, result);

        Mockito.verify(entityManager).getCriteriaBuilder();
        Mockito.verify(criteriaBuilder).between(root.get("yearOfCreation"), minYear, maxYear);
        Mockito.verify(typedQuery).getResultList();
    }
}
