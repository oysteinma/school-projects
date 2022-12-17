# Backend

## Kjøring av backend

Kjør kommandoene under i terminalen for å starte backenden.

```bash
$ cd backend
$ npm install
$ npm start
```

## Eksempel-queries

Kjører man lokalt vil man få opp en side for å kjøre queries på [localhost:4000](localhost:4000)

```
Eksempel på å opprette en film:

mutation {
  createMovies(
    input: [
      {
        title: "Forrest Gump",
        releaseDate: "1994-09-30"
        posterUrl: "https://www.themoviedb.org/t/p/w1280/saHP97rTPS5eLmrLQEcANmKrsFl.jpg"
        tagline: "A man with a low IQ has accomplished great things in his life and been present during significant historic events—in each case, far exceeding what anyone imagined he could do. But despite all he has achieved, his one true love eludes him."
      }
    ]
  ) {
    movies {
      title
      releaseDate
      posterUrl
      tagline
    }
  }
}
```

Eksempel på å hente filmer

```
query {
  movies {
    title
    releaseDate
    posterUrl
    tagline
    genres {
      name
    }

  }
}
```
