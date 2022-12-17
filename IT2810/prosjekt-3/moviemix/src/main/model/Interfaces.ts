export interface IMovie {
  title: string;
  id: string;
  tagline?: string;
  averageRating?: string;
  poster_url?: string;
  released?: string;
  director?: IDirector[];
  actors?: IActor[];
}

export interface IDirector {
  name: string;
}

export interface IActor {
  name: string;
}

export interface IRating {
  rating: number;
  description: string;

  movie: {
    title: string;
    poster_url?: string;
  };
}
