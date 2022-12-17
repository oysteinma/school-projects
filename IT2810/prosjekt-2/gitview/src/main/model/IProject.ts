/**
 * Model for project. Based on Gitlab Api fields
 */
export interface IProject {
    name: string;
    web_url: string;
    avatar_url: string;
    topics: string[];
    description: string;
}