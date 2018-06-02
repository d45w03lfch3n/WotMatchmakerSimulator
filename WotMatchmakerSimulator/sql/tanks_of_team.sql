select * from team as t 
	left join team_tanktype as ttt
	on t.id = ttt.team_id
	left join tanktype as tt
	on ttt.tanktypes_id = tt.id
where t.id = 71; 