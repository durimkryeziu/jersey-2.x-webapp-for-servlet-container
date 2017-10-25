package com.programmingskillz.samplejerseywebapp.business.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.programmingskillz.samplejerseywebapp.business.constraint.ValidIsbn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Durim Kryeziu
 */
@ApiModel(value = "Book", description = "Sample object that represents a book")
@JsonPropertyOrder(
    {"id", "title", "author", "description", "isbn", "pages", "publisher", "published"}
)
@JacksonXmlRootElement(localName = "book")
public class Book {

  @Size(min = 36, max = 36)
  private String id;
  @NotNull(message = "{book.title.null}")
  @Size(max = 500)
  private String title;
  @NotNull(message = "{book.author.null}")
  @Size(max = 255)
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

  @ApiModelProperty(value = "Book's ID", example = "767a463c-4cc3-48c1-b93e-25c0d216032b")
  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  @ApiModelProperty(value = "Book's Title", required = true, example = "Effective Java (2nd Edition)")
  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  @ApiModelProperty(value = "Book's Author", required = true, example = "Joshua Bloch")
  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  @ApiModelProperty("Book's Description")
  public void setDescription(String description) {
    this.description = description;
  }

  public String getIsbn() {
    return isbn;
  }

  @ApiModelProperty(value = "Book's ISBN number", required = true, example = "9780321356680")
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Integer getPages() {
    return pages;
  }

  @ApiModelProperty(value = "Book's Length", required = true, example = "364")
  public void setPages(Integer pages) {
    this.pages = pages;
  }

  public String getPublisher() {
    return publisher;
  }

  @ApiModelProperty("Book's Publisher")
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public Instant getPublished() {
    return published;
  }

  @ApiModelProperty(value = "Book's Published date", example = "2008-05-28T00:00:00Z")
  public void setPublished(Instant published) {
    this.published = published;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Book book = (Book) o;

    if (id != null ? !id.equals(book.id) : book.id != null) {
      return false;
    }
    if (title != null ? !title.equals(book.title) : book.title != null) {
      return false;
    }
    if (author != null ? !author.equals(book.author) : book.author != null) {
      return false;
    }
    if (description != null ? !description.equals(book.description) : book.description != null) {
      return false;
    }
    if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) {
      return false;
    }
    if (pages != null ? !pages.equals(book.pages) : book.pages != null) {
      return false;
    }
    if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) {
      return false;
    }
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
