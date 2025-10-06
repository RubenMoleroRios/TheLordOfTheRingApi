package com.ruben.lotr.thelordofthering_api.dto;

import java.util.List;

public class ExternalCharacterDTO {
    private Long id;
    private String name;
    private String realm;
    private String height;
    private String hair_color;
    private String eye_color;
    private String date_of_birth;
    private String date_of_death;
    private String gender;
    private String species;
    private String race;
    private String group;
    private List<String> weapons;
    private List<String> languages;
    private List<String> films;
    private List<String> books;
    private String url;

    public ExternalCharacterDTO(Long id, String name, String realm, String height, String hair_color, String eye_color,
            String date_of_birth, String date_of_death, String gender, String species, String race, String group,
            List<String> weapons, List<String> languages, List<String> films, List<String> books, String url) {
        this.id = id;
        this.name = name;
        this.realm = realm;
        this.height = height;
        this.hair_color = hair_color;
        this.eye_color = eye_color;
        this.date_of_birth = date_of_birth;
        this.date_of_death = date_of_death;
        this.gender = gender;
        this.species = species;
        this.race = race;
        this.group = group;
        this.weapons = weapons;
        this.languages = languages;
        this.films = films;
        this.books = books;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDate_of_death() {
        return date_of_death;
    }

    public void setDate_of_death(String date_of_death) {
        this.date_of_death = date_of_death;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
