export interface Hero {
    id: string;
    name: string;
    lastName: string;
    breedName: string;
    sideName: string;
    eyesColor: string;
    hairColor: string;
    height: number;
    description: string;
    imageUrl?: string;
}

export interface CatalogItem {
    id: string;
    name: string;
}