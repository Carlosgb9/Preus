package com.example.preus;

public class Model {
    private String name;
    private Integer preu;
    private String image;

    public Model(String name, Integer preu, String image) {
        this.name = name;
        this.preu = preu;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPreu() {
        return preu;
    }
    public void setPreu(Integer preu) {
        this.preu = preu;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

}

