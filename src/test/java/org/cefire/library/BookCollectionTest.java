package org.cefire.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookCollectionTest {
    @Test
    public void shouldFindABookByISBN() {
        final String isbnToLocate = "isbn2";
        BookCollection books = new BookCollection(new Book[]{
                new Book("isbn1", "titulo1", "autor1"),
                new Book("isbn2", "titulo2", "autor2"),
                new Book("isbn2", "titulo2", "autor2"),
                new Book("isbn3", "titulo3", "autor3"),
        });

        List<Book> foundBooks = books.find(isbnToLocate);
        Assertions.assertFalse(foundBooks::isEmpty);
        foundBooks.forEach((Book book)->Assertions.assertEquals(isbnToLocate, book.getISBN()));

    }

    @Test
    public void shouldGetAnEmptyListIfNoMatchesAreFound(){
        final String isbnToLocate = "isbn que no existe";
        BookCollection books = new BookCollection(new Book[]{
                new Book("isbn1", "titulo1", "autor1"),
                new Book("isbn2", "titulo2", "autor2"),
                new Book("isbn3", "titulo3", "autor3"),
        });

        Assertions.assertTrue(books.find(isbnToLocate).isEmpty());
    }
}
