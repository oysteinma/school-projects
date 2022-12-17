SELECT Brenneri.Navn AS Brennerinavn, Kaffe.Navn AS Kaffenavn
FROM 
    Brenneri JOIN Kaffe USING (BrenneriID)
    LEFT OUTER JOIN Kaffesmaking USING (KaffeID)
    WHERE Kaffe.Beskrivelse LIKE '%floral%' 
    OR Kaffesmaking.Notater LIKE '%floral%';