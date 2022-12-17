/**
 *
 * @returns An error message. Shows up if you open one of the pages
 * without entering a proper projectId and token.
 */
const NoData = () => {
  return (
    <div style={{ textAlign: "center" }}>
      <h2>NO DATA TO DISPLAY!</h2>
      <p>
        Please enter a valid project id and project access token at the
        homepage.
      </p>
    </div>
  );
};

export default NoData;
