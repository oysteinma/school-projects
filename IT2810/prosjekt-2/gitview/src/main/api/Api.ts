import axios, { AxiosResponse } from "axios";
import { IUser } from "../model/IUser";
import { ICommit } from "../model/ICommit";
import { IIssue } from "../model/IIssue";
import { IProject } from "../model/IProject";

/** Request for retrieving data from GitLabs API with Axios
 *
 *  The provided info is projectId and accesstoken,
 *  with the exeption og getCommits where filtering
 *  dates are also included.
 *
 */

export const useApi = () => {
  const getClient = (projectId: string, accessToken?: string) => {
    return axios.create({
      baseURL: `https://gitlab.stud.idi.ntnu.no/api/v4/projects/${projectId}/`,
      headers: {
        "content-type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
    });
  };

  const getCommits = async (
    projectId: string,
    fromDate: Date,
    toDate: Date,
    accessToken?: string
  ) => {
    const client = getClient(projectId, accessToken);
    return client.get<never, AxiosResponse<ICommit[]>>(
      `repository/commits?since=${fromDate.toISOString()}&until=${toDate.toISOString()}&per_page=100`
    );
  };
  const getUsers = async (projectId: string, accessToken?: string) => {
    const client = getClient(projectId, accessToken);
    return client.get<never, AxiosResponse<IUser[]>>("members/all");
  };
  const getIssues = async (projectId: string, accessToken?: string) => {
    const client = getClient(projectId, accessToken);
    return client.get<never, AxiosResponse<IIssue[]>>("issues");
  };
  const getRepo = async (projectId: string, accessToken?: string) => {
    const client = getClient(projectId, accessToken);
    return client.get<never, AxiosResponse<IProject>>("");
  };
  return {
    getCommits,
    getUsers,
    getIssues,
    getRepo,
  };
};
