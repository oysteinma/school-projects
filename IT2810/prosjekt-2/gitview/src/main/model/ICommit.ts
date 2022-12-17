/**
 * Model for Commit. Based on Gitlab Api fields
 */
export interface ICommit {
  title: string;
  committer_name: string;
  authored_date: Date;
  web_url: string;
}
