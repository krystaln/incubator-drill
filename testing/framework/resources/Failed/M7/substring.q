select substr(convert_from(m7voter.onecf.name, 'UTF8'), 5, 8) from m7voter where m7voter.twocf.age < 20;
