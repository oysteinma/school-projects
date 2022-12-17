SELECT Brenneri.Navn AS Brennerinavn, Kaffe.Navn AS Kaffenavn
FROM 
    Gaard JOIN Parti USING (GaardsID)
    JOIN ForedlingsMetode USING (MetodeNavn)
    JOIN Kaffe USING (PartiID)
    JOIN Brenneri USING (BrenneriID)
    WHERE (Gaard.Land LIKE '%Rwanda%'
    OR Gaard.Land LIKE '%Colombia%')
    AND Foredlingsmetode.MetodeNavn <> 'Vasket';
