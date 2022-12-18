import Typography from "@mui/material/Typography";

/**
 * @returns the MovieMixHeader component, which displays the title on each page.
 * @param title : the title of the page.
 */
export default function MovieMixHeader({ title }: { title: string }) {
  return (
    <Typography
      variant="h1"
      fontWeight={700}
      sx={{
        background:
          "linear-gradient(to right top, #d16ba5, #d76ea9, #dc70ae, #e273b2, #e876b7, #e184d2, #d493ea, #c3a2ff, #88c0ff, #3cd9ff, #07ecff, #5ffbf1)",
        WebkitBackgroundClip: "text",
        WebkitTextFillColor: "transparent",
        fontSize: {
          xs: 30,
          sm: 40,
          lg: 50,
        },
      }}
    >
      {title}
    </Typography>
  );
}
