package com.books.backend.service.impl

import com.books.backend.models.dto.BookDTO
import com.books.backend.models.dto.errors.NotFoundException
import com.books.backend.models.entity.Book
import com.books.backend.models.entity.BookAuthor
import com.books.backend.repository.BookAuthorRepository
import com.books.backend.repository.BookRepository
import com.books.backend.service.BookService
import com.books.backend.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookServiceImpl : BookService {
    companion object : Log()

    @Autowired
    private lateinit var bookAuthorRepository: BookAuthorRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    override fun getBook(bookId: Long): Mono<BookDTO> {
        return bookRepository.findByBookId(bookId).flatMap {
            Mono.just(
                BookDTO(
                    bookId = it.bookId,
                    publisherId = it.publisherId,
                    genreId = it.genreId,
                    isbn = it.isbn,
                    pageCount = it.pageCount,
                    title = it.title,
                    publicDate = it.publicDate,
                    summary = it.summary,
                )
            )
        }.switchIfEmpty(Mono.error(NotFoundException("Book $bookId Not found!")))
    }

    override fun getBooks(): Flux<BookDTO> {
        return bookRepository.findAll().flatMap {
            Flux.just(
                BookDTO(
                    bookId = it.bookId,
                    publisherId = it.publisherId,
                    genreId = it.genreId,
                    isbn = it.isbn,
                    pageCount = it.pageCount,
                    title = it.title,
                    publicDate = it.publicDate,
                    summary = it.summary,
                )
            )
        }
    }

    override fun createBook(book: BookDTO): Mono<BookDTO> {
        val entityToSave = Book(
            isbn = book.isbn,
            genreId = book.genreId,
            title = book.title,
            publicDate = book.publicDate,
            summary = book.summary,
            pageCount = book.pageCount,
            publisherId = book.publisherId
        )

        return bookRepository.save(entityToSave).flatMap { savedBook ->

            val bookAuthors = book.authorId!!.map { authorId ->
                BookAuthor(authorId = authorId, bookId = savedBook.bookId)
            }

            // Save all BookAuthor entities and then map to the result DTO
            bookAuthorRepository.saveAll(bookAuthors).then(
                Mono.just(
                    BookDTO(
                        bookId = savedBook.bookId,
                        publisherId = savedBook.publisherId,
                        genreId = savedBook.genreId,
                        isbn = savedBook.isbn,
                        pageCount = savedBook.pageCount,
                        title = savedBook.title,
                        publicDate = savedBook.publicDate,
                        summary = savedBook.summary,
                    )
                )
            )
        }.onErrorResume { throwable ->
            logger.error("Error occurred while creating book: ${throwable.message}", throwable)
            Mono.error(Exception("Failed to create book", throwable))
        }
    }


    override fun updateBook(book: BookDTO, bookId: Long): Mono<BookDTO> {
        return bookRepository.findByBookId(bookId).flatMap { existingBook ->
            val entityToUpdate = existingBook.copy(
                publicDate = book.publicDate,
                summary = book.summary,
                pageCount = book.pageCount,
                isbn = book.isbn,
                publisherId = book.publisherId,
                title = book.title,
                genreId = book.genreId
            )

            bookRepository.save(entityToUpdate).flatMap { updatedBook ->
                val bookAuthors = book.authorId!!.map { authorId ->
                    BookAuthor(authorId = authorId, bookId = updatedBook.bookId)
                }

                // Delete existing book authors
                bookAuthorRepository.deleteByBookId(updatedBook.bookId!!).then(
                    // Save new book authors
                    bookAuthorRepository.saveAll(bookAuthors).collectList().map {
                        BookDTO(
                            bookId = updatedBook.bookId,
                            publisherId = updatedBook.publisherId,
                            genreId = updatedBook.genreId,
                            isbn = updatedBook.isbn,
                            pageCount = updatedBook.pageCount,
                            title = updatedBook.title,
                            publicDate = updatedBook.publicDate,
                            summary = updatedBook.summary,
                            authorId = book.authorId
                        )
                    }
                )
            }
        }.onErrorResume { throwable ->
            logger.error("Error occurred while updating book: ${throwable.message}", throwable)
            Mono.error(Exception("Failed to update book", throwable))
        }
    }


    override fun deleteBook(bookId: Long): Mono<Boolean> {
        return bookRepository.deleteByBookId(bookId).flatMap { Mono.just(it) }.onErrorResume { throw Exception(it) }
    }
}