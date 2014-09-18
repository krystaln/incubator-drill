select cast(twocf['age'] as integer) + cast(threecf['contributions'] as decimal(6,2)) from m7voter where row_key=10;
