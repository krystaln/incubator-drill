#!/bin/bash
/opt/mapr/hbase/hbase-0.94.17/bin/hbase shell << EOF
disable '/test/tables/m7student'
drop '/test/tables/m7student'
disable '/test/tables/m7voter'
drop '/test/tables/m7voter'
create '/test/tables/m7student','onecf','twocf','threecf','fourcf','fivecf'
create '/test/tables/m7voter', 'onecf', 'twocf', 'threecf', 'fourcf', {SPLITS => ['1','2','3','4','5','6','7','8','9']}
exit
EOF
