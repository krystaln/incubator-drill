select substr(convert_from(voter.onecf.name, 'UTF8'), 5, 8) from voter where voter.twocf.age < 20;
