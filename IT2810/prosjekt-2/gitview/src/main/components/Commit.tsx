import {
  Button,
  Link,
  Typography,
  styled,
  FormControl,
  Select,
  MenuItem,
  Input,
  Card,
  Divider,
  InputBase,
} from "@mui/material";
import { Box } from "@mui/system";
import { useContext, useEffect, useState } from "react";
import { useApi } from "../api/Api";
import { ICommit } from "../model/ICommit";
import { AuthContext } from "../App";
import NoData from "./NoData";

/**
 * @returns The commit component - which essentially is the
 * commit page for out project. it uses localstorage and
 * sessionstorage to receive information about the project to get.
 * Commit will initially show up to a 100 commits from the last
 * year - sorted by time. you can also choose a timeframe
 * yourself, or choose which person to filter by.
 */
export const Commit = () => {
  const { getCommits } = useApi();
  const [commits, setCommits] = useState<ICommit[]>();
  const [authors, setAuthors] = useState<string[]>();
  const [filteredCommits, setFilteredCommits] = useState<ICommit[]>();
  const { authenticated } = useContext(AuthContext);
  const [fromDate, setFromDate] = useState<Date>(
    new Date(new Date().setFullYear(new Date().getFullYear() - 1))
  );
  const [toDate, setToDate] = useState<Date>(new Date(new Date().getTime()));

  useEffect(() => {
    if (authenticated) {
      fetchCommits(
        new Date(new Date().setFullYear(new Date().getFullYear() - 1)),
        new Date(new Date().getTime())
      );
    }
  }, [authenticated]);

  const fetchCommits = (fromDate: Date, toDate: Date) => {
    const token: string = window.sessionStorage.getItem("token")!!;
    const id: string = window.localStorage.getItem("id")!;

    getCommits(id, fromDate, toDate, token).then((res) => {
      setCommits(res.data);
      setFilteredCommits(res.data);
      const a: string[] = [];
      res.data.map((item) =>
        !a.includes(item.committer_name) ? a.push(item.committer_name) : ""
      );
      setAuthors(a);
    });
  };

  const [selectedIndex, setSelectedIndex] = useState(1);

  const handleListItemClick = (index: number, author: string) => {
    setSelectedIndex(index);
    if (index === 0) {
      setFilteredCommits(commits);
    } else {
      setFilteredCommits(
        commits?.filter((commit) => commit.committer_name === author)
      );
    }
  };

  const handleFromDate = (date: string) => {
    if (date !== "" && date.length <= 10) {
      setFromDate(new Date(date));
    }
  };

  const handleToDate = (date: string) => {
    if (date !== "" && date.length <= 10) {
      setToDate(new Date(date));
    }
  };

  const [filterValue, setFilterValue] = useState("All commits");
  const handleFilterValue = (newValue: string) => {
    setFilterValue(newValue);
  };

  const Styled = styled(InputBase)(({ theme }) => ({
    "label + &": {
      marginTop: theme.spacing(3),
    },
    "& .MuiInputBase-input": {
      borderRadius: 10,
      position: "relative",
      backgroundColor: "#002C58",
      color: "white",
      borderColor: "white",
      fontSize: 16,
      padding: "10px 26px 10px 12px",
      transition: theme.transitions.create(["border-color", "box-shadow"]),
    },
  }));

  return (
    <Box
      sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}
    >
      {!authenticated ? (
        <div>
          <NoData />
        </div>
      ) : (
        <Box>
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              gap: 1,
              justifyContent: "center",
              mt: 1,
            }}
          >
            <Box>
              <Typography>From:</Typography>
              <Input
                type="date"
                inputProps={{ min: "2000-01-01", max: "2023-01-01" }}
                onChange={(e) => handleFromDate(e.target.value)}
                sx={{
                  color: "white",
                  borderRadius: "10px",
                  backgroundColor: "#0059B2",
                  padding: 1,
                }}
                value={new Date(fromDate).toISOString().substring(0, 10)}
              />
            </Box>
            <Box>
              <Typography>To:</Typography>
              <Input
                type="date"
                inputProps={{ min: "2000-01-01", max: "2023-01-01" }}
                onChange={(e) => handleToDate(e.target.value)}
                sx={{
                  color: "white",
                  borderRadius: "10px",
                  backgroundColor: "#0059B2",
                  padding: 1,
                }}
                value={new Date(toDate).toISOString().substring(0, 10)}
              />
            </Box>
          </Box>
          <Box sx={{ display: "flex", justifyContent: "center", gap: 1 }}>
            <Button
              onClick={() => fetchCommits(fromDate, toDate)}
              sx={{ mt: 0.5, padding: "4px 6px", alignSelf: "center", top: 1 }}
              variant="outlined"
            >
              Get Commits
            </Button>
            <FormControl
              sx={{
                color: "white",
                mt: 0.5,
                mb: 2,
              }}
            >
              <Typography sx={{ textAlign: "center" }}>Filter</Typography>
              <Select
                labelId="select-label"
                id="select-label"
                label="Yo"
                value={filterValue}
                onChange={(e) => handleFilterValue(e.target.value)}
                input={<Styled />}
              >
                <MenuItem
                  selected={selectedIndex === 0}
                  onClick={() => handleListItemClick(0, "")}
                  key={0}
                  value="All commits"
                >
                  All
                </MenuItem>
                {authors?.map((author) => (
                  <MenuItem
                    selected={selectedIndex === authors.indexOf(author) + 1}
                    onClick={() =>
                      handleListItemClick(authors.indexOf(author) + 1, author)
                    }
                    key={authors.indexOf(author) + 1}
                    value={author}
                  >
                    {author}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Box>
          <Box sx={{ justifyContent: "center" }}>
            <Typography variant="h4" sx={{ mb: 2, textAlign: "center" }}>
              Commits
            </Typography>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                gap: 1,
                alignItems: "center",
              }}
            >
              {filteredCommits?.map((commit, key) => (
                <Card
                  key={key}
                  sx={{
                    backgroundColor: "#002C58",
                    color: "white",
                    borderRadius: "10px",
                    p: 2,
                    maxWidth: 650,
                    width: "90%",
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "space-between",
                  }}
                >
                  <Box sx={{ display: "flex", flexDirection: "column" }}>
                    <Typography variant="h6">
                      {commit.committer_name}
                    </Typography>
                    <Divider sx={{ backgroundColor: "white" }} />
                    <Typography
                      paragraph
                      sx={{ wordWrap: "break-word", mt: 2 }}
                    >
                      {commit.title}
                    </Typography>
                  </Box>
                  <Box
                    sx={{
                      display: "flex",
                      justifyContent: "space-between",
                      mt: "auto",
                    }}
                  >
                    <Link href={commit.web_url} target="_blank">
                      Link to commit
                    </Link>
                    <Typography variant="caption">
                      {new Date(commit.authored_date).toLocaleString("nb-NO")}
                    </Typography>
                  </Box>
                </Card>
              ))}
            </Box>
          </Box>
        </Box>
      )}
    </Box>
  );
};
