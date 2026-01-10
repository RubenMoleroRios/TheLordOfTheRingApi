package com.ruben.lotr.thelordofthering_api.dto.response;

import java.util.List;

public class LortApiResponseDTO {

    private List<LotrHeroApiDTO> docs;

    public List<LotrHeroApiDTO> getDocs() {
        return docs;
    }

    public void setDocs(List<LotrHeroApiDTO> docs) {
        this.docs = docs;
    }

}