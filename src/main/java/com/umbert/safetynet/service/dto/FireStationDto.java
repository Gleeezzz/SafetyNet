package com.umbert.safetynet.service.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data // Gère les getters/setters/equals/hashCode
public class FireStationDto {

    // 1. CHAMPS DE COMPTAGE : Les noms doivent correspondre aux setters utilisés dans le Service
    private Integer adultsCount;
    private Integer childrenCount; // Utilisation d'un nom de champ cohérent (childrenCount)

    // 2. CHAMPS DE LA LISTE DE PERSONNES
    // Cette liste est utilisée pour stocker les FireStationPersonDto.
    // Elle est initialisée ici pour garantir qu'elle ne soit JAMAIS null, empêchant la NPE.
    private List<FireStationPersonDto> people = new ArrayList<>();

    // -----------------------------------------------------------

    // 3. CONSTRUCTEURS
    // Ce constructeur par défaut est essentiel pour new FireStationDto() dans le Service
    public FireStationDto() {
    }

    // 🛑 CONSTRUCTEUR INUTILE SUPPRIMÉ : Le constructeur avec (String children, String adults)
    // est supprimé car il provoquait l'erreur "Expected 2 arguments but found 0" et utilisait des champs non standard.

    // -----------------------------------------------------------

    // 4. GETTERS ET SETTERS (Manuels pour contourner l'incohérence des noms historiques)

    // Les setters sont renommés pour correspondre aux noms standards des champs (AdultsCount et ChildrenCount)
    // Assurez-vous que votre service appelle setAdultsCount et setChildrenCount
    // ou adaptez les méthodes du service pour correspondre à ces méthodes.

    public void setChildrenCount(Integer childrenCount) {
        this.childrenCount = childrenCount;
    }

    public void setAdultsCount(Integer adultsCount) {
        this.adultsCount = adultsCount;
    }

    // Méthodes pour la liste 'people' :

    // 🛑 CORRECTION CRITIQUE (NPE) : Retourne le champ 'people' initialisé, JAMAIS null.
    public List<FireStationPersonDto> getPeople() {
        return people;
    }

    // Setter standard pour 'people'
    public void setPeople(List<FireStationPersonDto> people) {
        this.people = people;
    }
}