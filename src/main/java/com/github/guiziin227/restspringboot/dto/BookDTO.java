package com.github.guiziin227.restspringboot.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String author;
    private String launchDate;
    private String title;
    private Double price;

    public BookDTO() {}

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(id, bookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
