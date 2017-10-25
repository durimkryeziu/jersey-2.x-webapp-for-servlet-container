package com.programmingskillz.samplejerseywebapp.business.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Durim Kryeziu
 */
public class BookValidationTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validator;
  private Book book;
  private Set<ConstraintViolation<Book>> violations;

  @BeforeClass
  public static void setUpValidator() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Before
  public void setUpBook() {
    book = new Book();
    book.setId(UUID.randomUUID().toString());
    book.setTitle("Effective Java (2nd Edition)");
    book.setAuthor("Joshua Bloch");
    book.setDescription("Effective Java, Second Edition, brings together seventy-eight " +
        "indispensable programmer’s rules of thumb: working, best-practice solutions " +
        "for the programming challenges you encounter every day.");
    book.setIsbn("9780321356680");
    book.setPages(346);
    book.setPublisher("Addison-Wesley");
    book.setPublished(Instant.parse("2008-05-28T00:00:00.00Z"));
  }

  @Test
  public void testAllFieldsNull() {
    Book book = new Book();

    violations = validator.validate(book);
    assertEquals(5, violations.size());
  }

  @Test
  public void testTitleIsNull() {
    book.setTitle(null);

    violations = validator.validate(book);
    assertEquals(1, violations.size());
    assertEquals("Book's title cannot be null.", violations.iterator().next().getMessage());
  }

  @Test
  public void testAuthorIsNull() {
    book.setAuthor(null);

    violations = validator.validate(book);
    assertEquals(1, violations.size());
    assertEquals("Book's author cannot be null.", violations.iterator().next().getMessage());
  }

  @Test
  public void testIsbnIsNullAndInvalid() {
    book.setIsbn(null);

    violations = validator.validate(book);
    List<ConstraintViolation<Book>> violationList = getSortedConstraintViolations();

    assertEquals(2, this.violations.size());
    assertEquals("Book's isbn cannot be null.", violationList.get(0).getMessage());
    assertEquals("ISBN is not valid.", violationList.get(1).getMessage());
  }

  @Test
  public void testIsbnTooShortAndInvalid() {
    book.setIsbn("12345");

    violations = validator.validate(book);
    List<ConstraintViolation<Book>> violationList = getSortedConstraintViolations();

    assertEquals(2, this.violations.size());
    assertEquals("Book's isbn must be between 10 and 17 characters",
        violationList.get(0).getMessage());
    assertEquals("ISBN is not valid.", violationList.get(1).getMessage());
  }

  @Test
  public void testIsbnTooLongAndInvalid() {
    book.setIsbn("1234567890ABCDEFGH");

    violations = validator.validate(book);
    List<ConstraintViolation<Book>> violationList = getSortedConstraintViolations();

    assertEquals(2, this.violations.size());
    assertEquals("Book's isbn must be between 10 and 17 characters",
        violationList.get(0).getMessage());
    assertEquals("ISBN is not valid.", violationList.get(1).getMessage());
  }

  @Test
  public void testPagesIsNull() {
    book.setPages(null);

    violations = validator.validate(book);
    assertEquals(1, violations.size());
    assertEquals("Book's pages cannot be null.", violations.iterator().next().getMessage());
  }

  @Test
  public void testPagesTooLarge() {
    book.setPages(50000);

    violations = validator.validate(book);
    assertEquals(1, violations.size());
    assertEquals("Pages value must be less than or equal to 32767",
        violations.iterator().next().getMessage());
  }

  private List<ConstraintViolation<Book>> getSortedConstraintViolations() {
    List<ConstraintViolation<Book>> violationList = new ArrayList<>();
    violationList.addAll(violations);
    violationList.sort((v1, v2) -> v1.getMessage().compareTo(v2.getMessage()));
    return violationList;
  }

  @After
  public void tearDown() throws Exception {
    validatorFactory.close();
  }
}
