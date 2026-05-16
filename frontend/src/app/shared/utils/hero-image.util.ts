import { Hero } from '../../models/hero.models';

const heroImageAliases: Record<string, string> = {
    aragorn: 'aragorn',
    barbol: 'barbol',
    boromir: 'boromir',
    bregalad: 'bregalad',
    dain: 'dain',
    elrond: 'elrond',
    frodo: 'frodo',
    galadriel: 'galadriel',
    gandalf: 'gandalf',
    gimli: 'gimli',
    goin: 'goin',
    gorbag: 'gorbag',
    grishnakh: 'grishnakh',
    legolas: 'legolas',
    lurtz: 'lurtz',
    pippin: 'pipin',
    sam: 'sam',
    samwise: 'sam',
    saruman: 'saruman',
    sauron: 'sauron',
    theoden: 'theoden'
};

function normalizeName(value: string): string {
    return value
        .normalize('NFD')
        .replace(/\p{Diacritic}/gu, '')
        .toLowerCase()
        .trim();
}

export function withHeroImage(hero: Hero): Hero {
    const normalizedName = normalizeName(hero.name);
    const imageSlug = heroImageAliases[normalizedName];

    return {
        ...hero,
        imageUrl: imageSlug ? `/heroes/${imageSlug}.jpg` : undefined
    };
}
