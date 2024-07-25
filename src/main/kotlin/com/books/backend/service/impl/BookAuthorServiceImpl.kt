package com.books.backend.service.impl

import com.books.backend.models.dto.*
import com.books.backend.repository.*
import com.books.backend.service.BookAuthorService
import com.books.backend.util.Log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookAuthorServiceImpl : BookAuthorService {
    companion object : Log()

    @Autowired
    lateinit var bookAuthorRepository: BookAuthorRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var genreRepository: GenreRepository


    override fun getBookAuthor(
        authorId: Long
    ): Mono<BookAuthorDTO> {
        val author = authorRepository.findByAuthorId(authorId).flatMap {
            Mono.just(
                AuthorDTO(
                    authorId = it.authorId,
                    firstName = it.firstName,
                    bio = it.bio,
                    lastName = it.lastName,
                    birthDay = it.birthDay,
                )
            )
        }

        val books = bookAuthorRepository.findByAuthorId(authorId).flatMap { bookRepository.findByBookId(it.bookId!!) }

        val bookPublisher = books.flatMap { book ->
            publisherRepository.findByPublisherId(book.publisherId!!.toLong()).flatMap { publisher ->
                genreRepository.findByGenreId(book.genreId!!.toLong()).flatMap { genre ->
                    Mono.just(
                        BookPublisherDTO(
                            book = BookDTO(
                                bookId = book.bookId,
                                genreId = book.genreId,
                                publicDate = book.publicDate,
                                publisherId = book.publisherId,
                                title = book.title,
                                isbn = book.isbn,
                                pageCount = book.pageCount,
                                summary = book.summary
                            ), publisher = PublisherDTO(
                                publisherId = publisher.publisherId,
                                name = publisher.name,
                                address = publisher.address,
                                website = publisher.website,
                            ), genre = GenreDTO(
                                name = genre.name,
                                genreId = genre.genreId,
                            )
                        )
                    )
                }
            }
        }

        return Mono.zip(author, bookPublisher.collectList()).map {
            BookAuthorDTO(
                author = it.t1, books = it.t2
            )
        }
    }

    override fun getBookAuthors(): Flux<BookAuthorDTO> {
        return bookAuthorRepository.findAll().mapNotNull { it.authorId }.distinct().flatMap {
            getBookAuthor(it!!)
        }
    }
}