alter session set `planner.enable_hashagg` = false;
select max(c_int), max(c_bigint), max(c_float4), max(c_float8) from data;
alter session set `planner.enable_hashagg` = true;
