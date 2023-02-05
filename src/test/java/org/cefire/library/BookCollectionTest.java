package org.cefire.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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
//        assertFalse(foundBooks::isEmpty);
//        foundBooks.forEach((Book book)->assertEquals(isbnToLocate, book.getISBN()));

        assertThat(foundBooks.isEmpty(), is(false));
        foundBooks.forEach((Book book)->assertThat(isbnToLocate, is(equalTo(book.getISBN()))));
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

//    1. Comprobar el correcto funcionamiento de la búsqueda por título.
    @Test
    public void shouldFindABookByTitle() {
        final String titleToLocate = "titulo2";
        BookCollection books = new BookCollection(new Book[]{
                new Book("isbn1", "titulo1", "autor1"),
                new Book("isbn2", "titulo2", "autor2"),
                new Book("isbn2", "titulo2", "autor2"),
                new Book("isbn3", "titulo3", "autor3"),
        });

        List<Book> foundBooks = books.find(titleToLocate);
        assertFalse(foundBooks::isEmpty);
        foundBooks.forEach((Book book)-> assertEquals(titleToLocate, book.getTitle()));

    }

    //2. Comprobar que no se encuentran coincidencias cuando la BookCollection está vacía.
    @Test
    public void shouldGetNoMatchesIfBookCollectionIsEmpty(){

        //Si es null, sin problemas
//        BookCollection books=null; //<--in case of null
//        Assertions.assertTrue(books==null);

    //Pero si declaro books como una Lista, me dice que hay al menos un elemento. Y no me funciona ninguno de los Asserts siguientes:
        BookCollection books = new BookCollection(new Book[0]);
//        Assertions.assertTrue(List.of(books).isEmpty());
//        Assertions.assertArrayEquals(List.of(books).toArray(),List.of().toArray(new Book[0]));
//        Assertions.assertEquals(List.of(books).size(),0);

        //Solución facilitada por Marcos
       List foundBooks = books.find("dddd");
        assertTrue(foundBooks::isEmpty);
        assertEquals(foundBooks.size(),0);


    }

    @Test
    public void shouldFindCopiesOfABook() {
        final Book bookToBeFound = new Book("isbnFound","Found title","Found author");
        BookCollection books = new BookCollection(new Book[]{
                new Book("isbnFound","Found title","Found author"),
                new Book("isbnFound","Found title","Found author"),
                new Book("isbn2", "titulo2", "autor2"),
                new Book("isbn3", "titulo3", "autor3"),
        });

        List<Book> foundBooks = books.findCopies(bookToBeFound);
        assertFalse(foundBooks::isEmpty);
        foundBooks.forEach((Book book)-> assertEquals(book, bookToBeFound));

    }

    @Test
    public void shouldGetExceptionWhenUsingFindOrFailWithANonExistentEntry() {
        final String isbnToLocate = "este texto no existe ni como isbn ni como autor";
        BookCollection books = new BookCollection(new Book[]{
                new Book("isbn1", "titulo1", "autor1"),
                new Book("isbn2", "titulo2", "autor2"),
                new Book("isbn3", "titulo3", "autor3"),
        });

//        try {
//            books.findOrFail(isbnToLocate);
//            Assertions.fail("expected exception");
//        } catch (Exception e){
//            Assertions.assertInstanceOf(BookCollection.ExpectedToFindAtLeastABook.class, e);
//        }

        assertThrows(BookCollection.ExpectedToFindAtLeastABook.class,()->books.findOrFail(isbnToLocate));
    }
}


