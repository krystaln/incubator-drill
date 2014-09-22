select stddev(cast(voter.twocf.age as integer)) from voter where voter.twocf.age > 30;
