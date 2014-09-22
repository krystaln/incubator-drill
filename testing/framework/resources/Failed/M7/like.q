select cast(row_key as integer) voter_id, cast(m7voter.onecf.name as varchar(30)) name from m7voter where m7voter.onecf.name like '%young' order by voter_id;
