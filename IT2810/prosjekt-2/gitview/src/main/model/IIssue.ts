/**
 * Model for Issue. Based on Gitlab Api fields
 */
export interface IIssue {
  title: string;
  labels: string[];
  web_url: string;
  author: IAuthor;
  created_at: string;
}
/**
 * Model for Author. Author is a subfield in Issue
 */
interface IAuthor {
  name: string;
}
