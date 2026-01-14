package com.ruben.lotr.core.character.infrastructure.external.lotr.client;

import java.util.List;

public class LotrApiResponseDTO {

    private List<LotrHeroResponseApiDTO> docs;

    public List<LotrHeroResponseApiDTO> getDocs() {
        return docs;
    }

    public void setDocs(List<LotrHeroResponseApiDTO> docs) {
        this.docs = docs;
    }

}