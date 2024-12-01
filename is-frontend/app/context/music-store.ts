

export interface MusicBand {
    id: number
    name: string
    coordinates: {
        x: number
        y: number
    }
    creationDate: Date | undefined
    genre: MusicGenre | null
    numberOfParticipants: number | null
    singlesCount: number
    description: string | null
    bestAlbum: {
        name: string
        sales: number
    }
    albumsCount: number
    establishmentDate: Date | null
    studio: {
        address: string
    },
    ownerLogin: string
}

export enum MusicGenre {
    ROCK = "ROCK",
    PSYCHEDELIC_CLOUD_RAP = "PSYCHEDELIC_CLOUD_RAP",
    JAZZ = "JAZZ",
    BRIT_POP = "BRIT_POP"
}