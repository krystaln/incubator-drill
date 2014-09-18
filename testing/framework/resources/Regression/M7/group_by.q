select cast(m7voter.onecf.name as varchar(30)) name, count(*) unique_name from m7voter group by m7voter.onecf.name having count(*) > 3 order by m7voter.onecf.name;
