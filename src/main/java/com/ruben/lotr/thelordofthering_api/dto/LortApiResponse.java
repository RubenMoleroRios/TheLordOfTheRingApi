package com.ruben.lotr.thelordofthering_api.dto;

import java.util.List;

public class LortApiResponse {

    private List<LotrHero> docs;

    public List<LotrHero> getDocs() {
        return docs;
    }

    public void setDocs(List<LotrHero> docs) {
        this.docs = docs;
    }

}
