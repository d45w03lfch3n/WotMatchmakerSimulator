select tier, sum(useCount) as sumUseCount from TankType as t
	left join tankuse as u
	on t.id = u.tanktype_id
group by tier
order by sumUseCount desc;