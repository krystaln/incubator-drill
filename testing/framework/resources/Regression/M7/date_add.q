select date_add(cast(fourcf['create_date'] as timestamp), interval '3' hour) from m7voter where row_key=5;
