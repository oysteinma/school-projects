
SELECT Brenneri.Navn AS Brennerinavn, Kaffe.Navn AS Kaffenavn, Kaffe.KiloPris, avg(Kaffesmaking.Poeng) AS Gjennomsnittscore
FROM 
  Brenneri JOIN Kaffe USING (BrenneriID)
  JOIN Kaffesmaking USING (KaffeID) 
  GROUP BY KaffeID
  ORDER BY (Gjennomsnittscore / KiloPris) DESC
     