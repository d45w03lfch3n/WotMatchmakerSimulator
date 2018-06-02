select avg(m.tier), tt.name from Team_TankType as ttt
	left join Match as m
	on ttt.team_id = m.greenteam_id or ttt.team_id = m.redteam_id
	left join tanktype as tt
	on tt.id = ttt.tanktypes_id
where m.session_id = 1
group by tt.id;