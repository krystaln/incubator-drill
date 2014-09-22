select to_date(cast(m7voter.fourcf.create_date as varchar(25)), 'YYYY-MM-dd HH:mm:ss') from m7voter where row_key=5;
