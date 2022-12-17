Select Bruker.FulltNavn, Count(Epost)
FROM (
  SELECT Epost
  FROM Kaffe
  JOIN Kaffesmaking USING (KaffeID)
  WHERE strftime('%Y', SmaksDato) IS strftime('%Y', 'now')
  GROUP BY KaffeID, Epost
)
JOIN Bruker USING (Epost)
GROUP BY Epost
ORDER BY Count(Epost) DESC