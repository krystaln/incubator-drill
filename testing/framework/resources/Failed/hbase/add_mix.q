select cast(voter.twocf.age as integer) + cast(voter.threecf.contributions as decimal(6,2)) from voter where row_key=10;
