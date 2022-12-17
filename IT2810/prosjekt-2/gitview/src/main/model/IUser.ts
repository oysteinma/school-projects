/**
 * Model for User. Based on Gitlab Api fields
 */
export interface IUser {
  name: string;
  avatar_url: string;
  web_url: string;
  access_level: number;
}
