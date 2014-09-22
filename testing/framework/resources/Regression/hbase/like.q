select cast(row_key as integer) voter_id, cast(voter.onecf.name as varchar(30)) name from voter where voter.onecf.name like '%young' order by voter_id;
