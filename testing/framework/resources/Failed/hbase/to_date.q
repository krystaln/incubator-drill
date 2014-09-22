select to_date(cast(voter.fourcf.create_date as varchar(25)), 'YYYY-MM-dd HH:mm:ss') from voter where row_key=5;
