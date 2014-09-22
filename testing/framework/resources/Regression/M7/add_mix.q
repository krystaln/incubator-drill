select cast(m7voter.twocf.age as integer) + cast(m7voter.threecf.contributions as decimal(6,2)) from m7voter where row_key=10;
