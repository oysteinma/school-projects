import { useEffect, useState } from "react";

export const useWindowWidth = () => {
  const [width, setSize] = useState([0]);
  useEffect(() => {
    const updateWidth = () => {
      setSize([window.innerWidth]);
    };
    window.addEventListener("resize", updateWidth);
    updateWidth();
    return () => window.removeEventListener("resize", updateWidth);
  }, []);
  return width;
};
