package com.ruben.lotr.core.character.domain.model;

import com.ruben.lotr.core.character.domain.valueobject.SideIdVO;
import com.ruben.lotr.core.character.domain.valueobject.SideNameVO;

public class Side {

    private SideIdVO id;
    private SideNameVO name;

    public Side(SideIdVO id, SideNameVO name) {

        this.id = id;
        this.name = name;

    }

    public static Side create(SideIdVO id, SideNameVO name) {
        return new Side(id, name);
    }

    public static Side unknown() {
        return create(SideIdVO.unknown(), SideNameVO.unknown());
    }

    public SideIdVO id() {
        return id;
    }

    public SideNameVO name() {
        return name;
    }

}
