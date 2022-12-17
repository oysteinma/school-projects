import {
  Avatar,
  Box,
  Card,
  Divider,
  FormControl,
  FormControlLabel,
  Grid,
  Radio,
  RadioGroup,
  Stack,
  Typography,
} from "@mui/material";
import { SyntheticEvent, useContext, useEffect, useState } from "react";
import { useApi } from "../api/Api";
import { IUser } from "../model/IUser";
import { AuthContext } from "../App";
import NoData from "./NoData";

/**
 * @returns The User component, showing a grid of all group members.
 * You can see a members role, and also filter by the different roles.
 */
export const User = () => {
  const { getUsers } = useApi();
  const [users, setUsers] = useState<IUser[]>();
  const [userView, setUserView] = useState<IUser[]>();
  const { authenticated } = useContext(AuthContext);

  useEffect(() => {
    if (authenticated) {
      fetchUsers();
    }
  }, [authenticated]);
  // TODO: Toggle ALL fra starten

  function fetchUsers() {
    const token: string = window.sessionStorage.getItem("token")!!;
    const id: string = window.localStorage.getItem("id")!;

    getUsers(id, token).then((res) => {
      setUsers(res.data);
      setUserView(res.data);
    });
  }

  function viewMembers(event: SyntheticEvent<Element, Event>) {
    var toggleValue = (event.target as HTMLInputElement).value;

    if (toggleValue !== "all") {
      var tempUsers = users?.filter(
        (user) => user.access_level === parseInt(toggleValue)
      );
      setUserView(tempUsers);
    } else {
      setUserView(users);
    }
  }

  return (
    <div>
      {!authenticated ? (
        <div>
          <NoData />
        </div>
      ) : (
        <Box>
          <FormControl>
            <RadioGroup
              row
              name="toogle-different-members"
              onChange={(event) => viewMembers(event)}
              defaultValue="all"
            >
              <FormControlLabel value="all" control={<Radio />} label="All" />
              <FormControlLabel value="50" control={<Radio />} label="Owners" />
              <FormControlLabel
                value="40"
                control={<Radio />}
                label="Maintainers"
              />
              <FormControlLabel
                value="30"
                control={<Radio />}
                label="Developers"
              />
            </RadioGroup>
          </FormControl>
          <Box>
            <Grid container rowSpacing={1} spacing={2}>
              <Grid item xs={12}></Grid>
              {userView?.map((user, index) => (
                <Grid item xs={12} md={6} lg={4} key={index}>
                  <Card
                    sx={{ p: 2 }}
                    style={{
                      backgroundColor: "#002C58",
                      color: "white",
                      borderRadius: "10px",
                    }}
                  >
                    <Box>
                      <Avatar
                        variant="rounded"
                        src={user.avatar_url}
                        style={{ cursor: "pointer" }}
                        onClick={() => {
                          window.open(user.web_url, "_blank");
                        }}
                      />
                      <Stack spacing={0.5}>
                        <Typography fontWeight={300}>{user.name}</Typography>
                      </Stack>
                    </Box>
                    <Divider style={{ backgroundColor: "white" }} />
                    <Stack spacing={0.5}>
                      <Typography fontWeight={700}>
                        {user.access_level === 50
                          ? "Owner"
                          : user.access_level === 40
                          ? "Maintainer"
                          : user.access_level === 30
                          ? "Developer"
                          : "Other"}
                      </Typography>
                    </Stack>
                  </Card>
                </Grid>
              ))}
            </Grid>
          </Box>
        </Box>
      )}
    </div>
  );
};
