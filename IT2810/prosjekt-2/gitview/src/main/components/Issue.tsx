import { Box, Slider } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { useApi } from "../api/Api";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";
import { AuthContext } from "../App";
import NoData from "./NoData";

/** Issue.tsx: A functional component for showing the issues
 *
 *  Here, Chart.js is used to draw the graph. The data-const defines
 *  how the graph is presented - "scales" for example defines the font color.
 *
 *  Two handleChange-functions ensures that the presented data is
 *  continuously updated when the user adjusts the slider.
 *  The interface IData is locally used when updating the issues.
 */

interface IData {
  labels: string[];
  datasets: {
    label: string;
    data: number[];
    backgroundColor: string;
  }[];
}

export const Issue = () => {
  const { getIssues } = useApi();
  const [value, setValue] = useState(20);

  ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
  );

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        labels: {
          color: "white",
        },
      },
      title: {
        display: true,
        text: "Issues created in September 2022",
        color: "white",
      },
    },
    scales: {
      y: {
        ticks: { color: "white" },
      },
      x: {
        ticks: { color: "white" },
      },
    },
  };

  const [data, setData] = useState<IData>({
    labels: ["Before September 20", "At September 20", "After September 20"],
    datasets: [
      {
        label: "Issues",
        data: [0, 0, 0],
        backgroundColor: "#0077EE",
      },
    ],
  });

  const { authenticated } = useContext(AuthContext);
  useEffect(() => {
    if (authenticated) {
      fetchIssues(20);
    }
  }, [authenticated]);

  const fetchIssues = (newValue: number) => {
    const token: string = window.sessionStorage.getItem("token")!!;
    const id: string = window.localStorage.getItem("id")!;
    var issuesCreated: number[] = [0, 0, 0];

    getIssues(id, token).then((results) => {
      results.data.filter((issue) => {
        if (
          issue.created_at.slice(5, 7) === "09" &&
          issue.created_at.slice(2, 4) === "22"
        ) {
          if (parseInt(issue.created_at.slice(8, 10)) < newValue) {
            issuesCreated[0] = issuesCreated[0] + 1;
          } else if (parseInt(issue.created_at.slice(8, 10)) === newValue) {
            issuesCreated[1] = issuesCreated[1] + 1;
          } else {
            issuesCreated[2] = issuesCreated[2] + 1;
          }
        }
      });

      setData({
        labels: [
          `Before September ${newValue}`,
          `At September ${newValue}`,
          `After September ${newValue}`,
        ],
        datasets: [
          {
            label: "Issues",
            data: [issuesCreated[0], issuesCreated[1], issuesCreated[2]],
            backgroundColor: "#0077EE",
          },
        ],
      });
    });
  };

  const handleChangeCommitted = (
    event: Event | React.SyntheticEvent<Element, Event>,
    newValue: number | number[]
  ) => {
    fetchIssues(newValue as number);
    setValue(newValue as number);
  };

  const handleChange = (e: Event, newValue: number | number[]) => {
    setValue(newValue as number);
  };

  return (
    <Box sx={{ pb: 5 }}>
      {!authenticated ? (
        <div>
          <NoData />
        </div>
      ) : (
        <div style={{ maxHeight: "450px", width: "100%", height: "60%" }}>
          <Bar options={options} data={data} />
          <Slider
            value={value}
            onChange={handleChange}
            onChangeCommitted={handleChangeCommitted}
            valueLabelDisplay="auto"
            min={1}
            max={30}
          />
        </div>
      )}
    </Box>
  );
};
