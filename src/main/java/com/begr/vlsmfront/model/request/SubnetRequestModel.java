package com.begr.vlsmfront.model.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@Component
public class SubnetRequestModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7787150724597949335L;
    @NotNull(message = "Le nom du réseau doit être renseigné.")
    @NotEmpty(message = "Le nom du réseau doit être renseigné.")
    @Pattern(regexp = "^[A-Za-z0-9À-ÿ-_]+$", message = "Le nom du réseau ne doit comporter que des lettres et/ou des chiffres ou des tirets.")
    public String name;
    @NotNull(message = "La taille du réseau ne peut pas être vide.")
    @Positive(message= "La taille du réseau doit être positive.")
    public Integer neededSize;


}
