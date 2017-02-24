package com.programmingskillz.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.programmingskillz.constraint.ValidIsbn;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @author Durim Kryeziu
 */
@JsonPropertyOrder({"id", "title", "author", "description", "isbn", "pages", "publisher", "published"})
@JacksonXmlRootElement(localName = "book")
public class Book {

    private String id;
    @NotNull(message = "{book.title.null}")
    private String title;
    @NotNull(message = "{book.author.null}")
    private String author;
    private String description;
    @ValidIsbn
    private String isbn;
    private String publisher;
    private Instant published;
    @NotNull(message = "{book.pages.null}")
    @Max(value = 32767, message = "{book.pages.max}")
    private Integer pages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Instant getPublished() {
        return published;
    }

    public void setPublished(Instant published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (description != null ? !description.equals(book.description) : book.description != null) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (pages != null ? !pages.equals(book.pages) : book.pages != null) return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        return published != null ? published.equals(book.published) : book.published == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (published != null ? published.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pages=" + pages +
                ", publisher='" + publisher + '\'' +
                ", published=" + published +
                '}';
    }
}