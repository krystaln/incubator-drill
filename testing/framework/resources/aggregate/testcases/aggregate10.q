alter session set `planner.enable_hashagg` = false;
select min(distinct(c_int)), min(distinct(c_bigint)), min(distinct(c_float4)), min(distinct(c_float8)) from data;
alter session set `planner.enable_hashagg` = true;
