select div(cast(m7voter.twocf.age as integer),row_key) div from m7voter where row_key < 11 order by div;
