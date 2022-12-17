import { Typography, Box, Button, Chip, Grid } from "@mui/material";
import { useState, useContext, useEffect } from "react";
import { useApi } from "../api/Api";
import { IProject } from "../model/IProject";
import { AuthContext } from "../App";

/**
 * @returns the Repository component, showing simple
 * information about the repository which you are currently
 * connected to. Requires correct projectId and api-key.
 */
export const Repository = () => {
  const { getRepo } = useApi();
  const token: string = window.sessionStorage.getItem("token")!!;
  const id: string = window.localStorage.getItem("id")!;
  const [project, setProject] = useState<IProject>();
  const { authenticated, setAuthenticated } = useContext(AuthContext);

  useEffect(() => {
    getRepo(id, token)
      .then((res) => {
        setProject(res.data);
        setAuthenticated(true);
      })
      .catch(() => setAuthenticated(false));
  }, [authenticated]);

  return (
    <Box>
      {authenticated && project && (
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            gap: 1,
          }}
        >
          {project?.name}
          <Grid sx={{ pt: 0.5, gap: 1, justifyContent: "center" }} container>
            {project?.topics.map((topic) => (
              <Chip
                label={topic}
                size="small"
                key={project.topics.indexOf(topic)}
                sx={{ fontSize: 11, color: "white", background: "gray" }}
              />
            ))}
          </Grid>
          <Typography paragraph>{project?.description}</Typography>
          <Button
            size="small"
            color="primary"
            onClick={() => window.open(project?.web_url, "_blank")}
          >
            Link to project
          </Button>
        </Box>
      )}
    </Box>
  );
};
