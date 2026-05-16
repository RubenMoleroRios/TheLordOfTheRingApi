package com.ruben.lotr.core.hero.domain.model;

import org.springframework.lang.NonNull;

import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideIdVOMother;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideNameVO;
import com.ruben.lotr.core.hero.domain.valueobject.side.SideNameVOMother;

public final class SideMother {

    private SideMother() {
    }

    public static Builder aSide() {
        return new Builder();
    }

    public static final class Builder {
        private @NonNull SideIdVO id = SideIdVOMother.random();
        private @NonNull SideNameVO name = SideNameVOMother.good();

        public Builder withId(@NonNull SideIdVO id) {
            this.id = id;
            return this;
        }

        public Builder withId(@NonNull String id) {
            return withId(SideIdVO.create(id));
        }

        public Builder withName(@NonNull SideNameVO name) {
            this.name = name;
            return this;
        }

        public Builder withName(@NonNull String name) {
            return withName(SideNameVO.create(name));
        }

        public Side build() {
            return Side.create(id, name);
        }
    }
}
