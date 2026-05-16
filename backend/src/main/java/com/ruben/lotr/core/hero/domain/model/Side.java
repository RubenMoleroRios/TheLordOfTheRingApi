package com.ruben.lotr.core.hero.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideNameVO;

public class Side {

    private SideIdVO id;
    private SideNameVO name;

    private Side(SideIdVO id, SideNameVO name) {

        this.id = id;
        this.name = name;

    }

    public static @NonNull Side create(@NonNull SideIdVO id, @NonNull SideNameVO name) {
        return new Side(id, name);
    }

    public static @NonNull Side unknown() {
        return create(SideIdVO.unknown(), SideNameVO.unknown());
    }

    public SideIdVO id() {
        return id;
    }

    public SideNameVO name() {
        return name;
    }

}
